package com.test.springboot.service.base.jms;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.jms.Destination;

/**
* @Author wwy
* @Description 消息提供者
* @Date 16:15 2018/12/7
* @Param
* @return
**/
@Component
public class JmsProducer {
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    public void sendMsg(String destinationName, String message) {
        Destination destination = new ActiveMQQueue(destinationName);
        jmsMessagingTemplate.convertAndSend(destination, message);
    }
}
