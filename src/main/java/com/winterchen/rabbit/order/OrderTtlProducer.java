package com.winterchen.rabbit.order;
import com.winterchen.rabbit.OrderRabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderTtlProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(){
        rabbitTemplate.convertAndSend(OrderRabbitConfig.orderFanoutExchange,"","order");
    }
}
