package com.test.springboot.aop;

import com.test.springboot.config.dbconfig.DataSourceContextHolder;
import com.test.springboot.config.dbconfig.DataSourceType;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

/**
* @Author wwy
* @Description
 *            注意本类实现 PriorityOrdered接口，
 *            此接口用来加载时区分先后所用
 *            类似filter有先后顺序
* @Date 11:12 2018/12/6
* @Param
* @return
**/
@Aspect
@EnableAspectJAutoProxy(proxyTargetClass=true,exposeProxy=true)
@Component
public class DataSourceChangeAop implements PriorityOrdered {

    @Before("execution(* com.test.springboot.service..*.*(..)) " +
            "&& @annotation(com.test.springboot.annotation.ReadDataSource)")
    public void setReadDataSoruceType(){
        if(!DataSourceType.write.getType().equals(DataSourceContextHolder.getReadOrWrite())){
            DataSourceContextHolder.setRead();
        }
    }

    @Before("execution(* com.test.springboot.service..*.*(..)) " +
            "&& @annotation(com.test.springboot.annotation.WriteDataSource)")
    public void setWriteDataSoruceType(){
            DataSourceContextHolder.setWrite();
    }

    /**
    * @Description 该类优先级要高于事物，首先要拿到数据库源，然后再开启事物
    **/
    @Override
    public int getOrder() {
        return 1;
    }
}
