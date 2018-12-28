package com.test.springboot.config.dbconfig;

import com.github.pagehelper.PageHelper;
import com.mysql.jdbc.StringUtils;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author wwy
 * @Description mybatis配置
 * 实现TransactionManagementConfigurer事物控制
 * @Date 17:35 2018/12/5
 * @Param
 * @return
 **/
@Configuration
@AutoConfigureAfter(com.test.springboot.config.dbconfig.DataSourceConfiguration.class)
@MapperScan("com.test.springboot.dao")
public class MybatisConfiguration {

    private static Logger logger = LoggerFactory.getLogger(MybatisConfiguration.class);


    @Value("${mysql.datasource.readSize}")
    private String readDataSourceSize;

    @Value("${mybatis.mapperLocations}")
    private String mapperLocations;

    @Value("${mybatis.configLocation}")
    private String mybatisConfigLocation;

    /**
     * @Description 自动注入写库数据源 用@Qualifier能够避免注入相同类型的bean报错
     **/
    @Autowired
    @Qualifier("writeDataSource")
    private DataSource writeDataSource;


    /**
     * @Description 自动注入读库数据源
     **/
    @Autowired
    @Qualifier("readDataSource01")
    private DataSource readDataSource01;


    /**
     * @Description 自动注入读库数据源
     **/
    @Autowired
    @Qualifier("readDataSource02")
    private DataSource readDataSource02;


    @Bean
    public SqlSessionFactory sqlSessionFactory() {
        try {
            SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
            sqlSessionFactoryBean.setDataSource(routingDataSourceProxy());
            //设置typeAlias
            sqlSessionFactoryBean.setTypeAliasesPackage("com.test.springboot.domain");

            //需要把mapper.xml放入sqlsessionFactoryBean中
            //ResourcePatternResolver resolver = new
            Resource[] resources = new PathMatchingResourcePatternResolver().getResources(mapperLocations);
            sqlSessionFactoryBean.setMapperLocations(resources);

            //注意用defualt可能会遇到坑
            sqlSessionFactoryBean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource(mybatisConfigLocation));
            Interceptor[] plugins = new Interceptor[]{pageHelper(),new SqlPrintInterceptor()};
            sqlSessionFactoryBean.setPlugins(plugins);
            return sqlSessionFactoryBean.getObject();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e1) {
            e1.printStackTrace();
            return null;
        }

    }

    /**
     * 分页插件
     *
     * @return
     */
    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        p.setProperty("offsetAsPageNum", "true");
        p.setProperty("rowBoundsWithCount", "true");
        p.setProperty("reasonable", "true");
        p.setProperty("returnPageInfo", "check");
        p.setProperty("params", "count=countSql");
        pageHelper.setProperties(p);
        return pageHelper;
    }


    @Bean(name = "routingDataSourceProxy")
    public AbstractRoutingDataSource routingDataSourceProxy() {
        Map<Object, Object> targeDataSources = new HashMap<>();
        targeDataSources.put(com.test.springboot.config.dbconfig.DataSourceType.write.getType(), writeDataSource);
        targeDataSources.put(com.test.springboot.config.dbconfig.DataSourceType.read.getType() + "1", readDataSource01);
        targeDataSources.put(com.test.springboot.config.dbconfig.DataSourceType.read.getType() + "2", readDataSource02);

        //获取读库数量
        final int readSize = Integer.parseInt(readDataSourceSize);
        AbstractRoutingDataSource proxy = new AbstractRoutingDataSource() {

            private AtomicInteger count = new AtomicInteger(0);

            @Override
            protected Object determineCurrentLookupKey() {
                String key = com.test.springboot.config.dbconfig.DataSourceContextHolder.getReadOrWrite();
                if (StringUtils.isNullOrEmpty(key)) {
                    throw new NullPointerException("数据库路由时，数据源类型不能为空...");
                }

                if (com.test.springboot.config.dbconfig.DataSourceType.write.getType().equals(key)) {
                    logger.info("----------------使用writeDataSource-----------------------");
                    return com.test.springboot.config.dbconfig.DataSourceType.write.getType();
                }

                int number = count.addAndGet(1);
                int lookUpKey = number % readSize;

                return com.test.springboot.config.dbconfig.DataSourceType.read.getType() + (lookUpKey + 1);
            }
        };
        proxy.setDefaultTargetDataSource(writeDataSource);
        proxy.setTargetDataSources(targeDataSources);
        return proxy;
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }



}
