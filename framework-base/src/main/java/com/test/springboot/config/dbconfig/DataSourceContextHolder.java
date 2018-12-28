package com.test.springboot.config.dbconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* @Author wwy
* @Description 本地线程，数据源上线文,用于切换数据库
* @Date 16:54 2018/12/5
* @Param
* @return
**/
public class DataSourceContextHolder {


    private static Logger logger = LoggerFactory.getLogger(DataSourceContextHolder.class);

    private static final ThreadLocal<String> local = new ThreadLocal<String>();

    public static ThreadLocal<String> getLocal() {
        return local;
    }

    public static void setRead(){
        logger.info("-----------数据库切换到从库---------------");
        local.set(DataSourceType.read.getType());
    }


    public static void setWrite(){
        logger.info("-----------数据库切换到主库---------------");
        local.set(DataSourceType.write.getType());
    }

    public static String getReadOrWrite(){
       return local.get();

    }

    public static void clear() {
        local.remove();
    }
}
