package com.winterchen;

import com.winterchen.rabbit.OrderRabbitConfig;
import com.winterchen.rabbit.hello.HelloSender;
import com.winterchen.rabbit.order.OrderTtlProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot2MybatisDemoApplicationTests {

	@Autowired
	OrderTtlProducer orderTtlProducer;

	@Autowired
	private AmqpTemplate rabbitTemplate;

	@Test
	public void contextLoads() {
	}

	@Test
	public void helloSend() throws InterruptedException {
		for(int i=0;i<3;++i)
			orderTtlProducer.send();

		Thread.sleep(400000);

	}

	@Test
	public void sendOrderDelay() throws InterruptedException {
		rabbitTemplate.convertAndSend(OrderRabbitConfig.orderDelayRoute,"fuck");
		java.lang.Thread.sleep(300000);
	}
}
