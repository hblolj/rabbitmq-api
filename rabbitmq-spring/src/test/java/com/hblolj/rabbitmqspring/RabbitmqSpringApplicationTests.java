package com.hblolj.rabbitmqspring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitmqSpringApplicationTests {

	@Autowired
	private RabbitAdmin rabbitAdmin;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testRabbitMQ() throws Exception {
		rabbitAdmin.declareExchange(new DirectExchange("test.spring.direct", false, false));
		rabbitAdmin.declareExchange(new TopicExchange("test.spring.topic", false, false));
		rabbitAdmin.declareExchange(new FanoutExchange("test.spring.fanout", false, false));

		rabbitAdmin.declareQueue(new Queue("test.spring.direct.queue", false));
		rabbitAdmin.declareQueue(new Queue("test.spring.topic.queue", false));
		rabbitAdmin.declareQueue(new Queue("test.spring.fanout.queue", false));

		rabbitAdmin.declareBinding(new Binding("test.spring.direct.queue", Binding.DestinationType.QUEUE,
				"test.spring.direct", "direct", new HashMap<>()));

		rabbitAdmin.declareBinding(BindingBuilder
				.bind(new Queue("test.spring.topic.queue", false))
				.to(new TopicExchange("test.spring.topic", false, false))
				.with("topic.#"));

		rabbitAdmin.declareBinding(BindingBuilder
				.bind(new Queue("test.spring.fanout.queue", false))
				.to(new FanoutExchange("test.spring.fanout", false, false)));

		rabbitAdmin.purgeQueue("test.spring.topic.queue", false);
	}

	@Test
	public void testSendMessage() throws Exception {
		//1 创建消息
		MessageProperties messageProperties = new MessageProperties();
		messageProperties.getHeaders().put("desc", "信息描述..");
		messageProperties.getHeaders().put("type", "自定义消息类型..");
		Message message = new Message("Hello RabbitMQ".getBytes(), messageProperties);

		rabbitTemplate.convertAndSend("topic001", "spring.amqp", message, new MessagePostProcessor() {
			@Override
			public Message postProcessMessage(Message message) throws AmqpException {
				System.err.println("------添加额外的设置---------");
				message.getMessageProperties().getHeaders().put("desc", "额外修改的信息描述");
				message.getMessageProperties().getHeaders().put("attr", "额外新加的属性");
				return message;
			}
		});
	}

	@Test
	public void testSendMessage2() throws Exception {
		//1 创建消息
		MessageProperties messageProperties = new MessageProperties();
		messageProperties.setContentType("text/plain");
		Message message = new Message("mq 消息1234".getBytes(), messageProperties);

		rabbitTemplate.send("topic001", "spring.abc", message);

		rabbitTemplate.convertAndSend("topic001", "spring.amqp", "hello object message send!");
		rabbitTemplate.convertAndSend("topic002", "rabbit.abc", "hello object message send!");
	}
}
