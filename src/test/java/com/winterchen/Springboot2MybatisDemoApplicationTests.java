package com.winterchen;

import com.winterchen.rabbit.OrderRabbitConfig;
import com.winterchen.rabbit.hello.HelloReceiver;
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
	HelloSender helloSender;

	@Autowired
	private AmqpTemplate rabbitTemplate;

	@Test
	public void contextLoads() {
	}

	@Test
	public void helloSend() throws InterruptedException {
		for(int i=0;i<3000;++i)
			helloSender.send();


		Thread.sleep(10000);
		System.out.println(HelloReceiver.count);

	}

	@Test
	public void sendOrderDelay() throws InterruptedException {
		rabbitTemplate.convertAndSend(OrderRabbitConfig.orderDelayRoute,"fuck");
		java.lang.Thread.sleep(300000);
	}
}
