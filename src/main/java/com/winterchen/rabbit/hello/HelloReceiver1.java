package com.winterchen.rabbit.hello;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "hello")
public class HelloReceiver1 {
    @RabbitHandler
    public void process(String hello) {
        int temp=HelloReceiver.count;
        System.out.println("Receiver1  : " + hello);
        HelloReceiver.count=temp+1;
    }

}