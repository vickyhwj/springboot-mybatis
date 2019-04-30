package com.winterchen.rabbit.order;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.winterchen.rabbit.OrderRabbitConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@RabbitListener(queues = OrderRabbitConfig.orderDelayQueue)
public class OrderDelayComsumer {
    @RabbitHandler
    public void recive(String text, Message message, Channel channel, @Headers Map<String,Object> map) throws  Exception {
        System.out.println(System.currentTimeMillis());
        System.out.println(text);
        System.out.print(message);
//        if (map.get("error")!= null){
//            System.out.println("错误的消息");
//            try {
//                channel.basicNack((Long)map.get(AmqpHeaders.DELIVERY_TAG),false,true);      //否认消息并重新放回队列
//                return;
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        try {
//            channel.basicAck((Long)map.get(AmqpHeaders.DELIVERY_TAG),false);            //确认消息
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        channel.basicReject((Long)map.get(AmqpHeaders.DELIVERY_TAG),false);      //否认消息
    }
}
