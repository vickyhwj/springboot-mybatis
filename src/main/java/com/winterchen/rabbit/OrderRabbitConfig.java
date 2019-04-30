package com.winterchen.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class OrderRabbitConfig {

    public final  static String orderTtlQueue="order.ttl";
    public final static String orderDelayQueue="order.delay";
    public final static String orderDelayRoute="order.delay";
    public final static String orderDelayExchange="orderDelayExchange";
    public final static String orderFanoutExchange="orderFanoutExchange";


    @Bean
    public Queue orderTtlQueue(){
        Map<String,Object> args=new HashMap<>();
        args.put("x-message-ttl",5000);
        args.put("x-dead-letter-exchange",orderDelayExchange);
        args.put("x-dead-letter-routing-key",orderDelayRoute);
        return new Queue(orderTtlQueue,true,false,false,args);
    }

    @Bean
    public Queue orderDelayQueue(){
        return new Queue(orderDelayRoute);
    }

    @Bean
    public FanoutExchange orderDelayExchange(){
        return new FanoutExchange(orderDelayExchange);
    }

    @Bean
    public FanoutExchange orderFanoutExchange(){
        return new FanoutExchange(orderFanoutExchange);
    }

    @Bean
    public Binding bingOrderTtlQueueToOrderFanoutExchange(Queue orderTtlQueue,FanoutExchange orderFanoutExchange){
        return BindingBuilder.bind(orderTtlQueue).to(orderFanoutExchange);
    }

    @Bean
    public Binding bingOrderDelayQueueToOrderDelayExchange(Queue orderDelayQueue,FanoutExchange orderDelayExchange){
        return BindingBuilder.bind(orderDelayQueue).to(orderDelayExchange);
    }






}