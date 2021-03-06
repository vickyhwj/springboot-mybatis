package com.winterchen.rabbit.hello;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RabbitListener(queues = "hello")
public class HelloReceiver {
    public static int count=0;
    @RabbitHandler
    public void process(String hello) {
        int temp=count;
        System.out.println("Receiver  : " + hello);
        count=temp+1;
    }

}