package com.test.springboot.listener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import java.io.IOException;
import java.util.Properties;


public class ApplicatonEnvironDemoListener  implements
        SpringApplicationRunListener,PriorityOrdered {

    private SpringApplication application;

    private String[] args;
    /**
     * 通过反射创建该实例对象的，构造方法中的参数要加上如下参数
     */
    public ApplicatonEnvironDemoListener(SpringApplication application,String[] args){
        this.application = application;
        this.args = args;
    }

    /**
     * 在准备环境之间调用
     * SpringApplication#run -> listeners.starting();
     */
    @Override
    public void starting() {
        System.out.println("starting-----");
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        Properties properties = new Properties();
        try {
            //demo.properties就是我们自定义的配置文件，extension是自定义目录
            properties.load(this.getClass().getClassLoader().
                    getResourceAsStream("extension/demo.properties"));
            PropertySource propertySource =new
                    PropertiesPropertySource("demo",properties);
            //PropertySource是资源加载的核心
            MutablePropertySources propertySources = environment.getPropertySources();
            //这里添加最后
            propertySources.addLast(propertySource);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext configurableApplicationContext) {

    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext configurableApplicationContext) {

    }

    @Override
    public void finished(ConfigurableApplicationContext configurableApplicationContext, Throwable throwable) {

    }


    /**
     * 这里可以设置该配置文件加载的顺序，在application.yml之前还是之后
     * EventPublishingRunListener#getOrder方法返回 “0”，按照需求这里我们这是比0大，
     * 即在application.yml之后加载，这样在application.yml配置时，可以“覆盖”my.yml
     * 这里用“覆盖”可能不合适，意思到了就好
     */
    @Override
    public int getOrder() {
        return 1;
    }
}
