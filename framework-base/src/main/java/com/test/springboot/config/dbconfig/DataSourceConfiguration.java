package com.test.springboot.config.dbconfig;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
* @Author wwy
* @Description 数据源配置，用来初始化读写库
* @Date 16:48 2018/12/5
* @Param
* @return
**/
@Configuration
public class DataSourceConfiguration {

    private static  Logger logger = LoggerFactory.getLogger(DataSourceConfiguration.class);

    @Value("${mysql.datasource.type}")
    private Class<? extends DataSource> dataSourceType;

    //当有多个相同类型的实例需要注入到spring容器中时，需要用到@Primary
    @Primary
    @Bean("writeDataSource")
    @ConfigurationProperties(prefix = "mysql.datasource.write")
    public DataSource writeDataSource(){
        logger.info("------------------------------- writeDataSource init--------------------------------------");
        return DataSourceBuilder.create().type(dataSourceType).build();
    }

    @Bean("readDataSource01")
    @ConfigurationProperties(prefix = "mysql.datasource.read01")
    public DataSource readDataSource01(){
        logger.info("------------------------------- readDataSource01 init--------------------------------------");
        return DataSourceBuilder.create().type(dataSourceType).build();

    }

    @Bean("readDataSource02")
    @ConfigurationProperties(prefix = "mysql.datasource.read02")
    public DataSource readDataSource02(){
        logger.info("------------------------------- readDataSource02 init--------------------------------------");
        return DataSourceBuilder.create().type(dataSourceType).build();

    }
}
