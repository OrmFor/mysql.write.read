package com.test.springboot.config.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.jms.pool.PooledConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class JmsConfig {

    @Value("${spring.activemq.pool.configuration.max-connections}")
    private int connectionMaxCounts;

    @Value("${spring.activemq.pool.configuration.maximum-active-session-per-connection}")
    private int sessionPerConnectionMaxCount;

    @Value("${spring.activemq.pool.configuration.messageCountPerSession2Send}")
    private int messageCountPerSession2Send;
    @Value("${spring.activemq.pool.configuration.time-between-expiration-check-millis}")
    private int sessionCommitInterval;

    @Bean
    @Primary
    @Qualifier("my_activeMQConnectionFactory")
    public ActiveMQConnectionFactory activeMQConnectionFactory(){
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setMaxThreadPoolSize(connectionMaxCounts);
        return activeMQConnectionFactory;

    }

    @Bean
    @Qualifier("my_pooledConnectionFactory")
    public PooledConnectionFactory pooledConnectionFactory(ActiveMQConnectionFactory activeMQConnectionFactory){
        PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
        pooledConnectionFactory.setConnectionFactory(activeMQConnectionFactory);
        return pooledConnectionFactory;
    }

    @Bean
    @Qualifier("activMQPools")
    public ActiveMQPools activeMQPools(PooledConnectionFactory pooledConnectionFactory){
        ActiveMQPools activeMQPools = new ActiveMQPools();
        activeMQPools.setConnectionMaxCounts(connectionMaxCounts);
        activeMQPools.setQueueName("test");
        activeMQPools.setSessionPerConnectionMaxCount(sessionPerConnectionMaxCount);
        activeMQPools.setMessageCountPerSession2Send(messageCountPerSession2Send);
        activeMQPools.setPooledConnectionFactory(pooledConnectionFactory);
        return  activeMQPools;
    }
}
