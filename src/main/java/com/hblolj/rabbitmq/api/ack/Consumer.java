package com.hblolj.rabbitmq.api.ack;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author: hblolj
 * @Date: 2018/8/16 9:33
 * @Description:
 * @Version: 1.0
 **/
public class Consumer {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        // 1. 创建 ConnectionFactory，进行设置
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("39.106.37.46");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("424487386");

        // 2. 通过连接工厂创建连接
        Connection connection = connectionFactory.newConnection();

        // 3. 通过连接创建 Channel
        Channel channel = connection.createChannel();

        // 4. 声明(创建)一个队列
        String exchangeName = "test_ack_exchange";
        String routingKey = "ack.#";
        String queueName = "test_ack_queue";

        channel.exchangeDeclare(exchangeName, "topic", true);
        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, exchangeName, routingKey);

//        channel.basicQos(0, 1, false);

        // 6. 手工签收 必须要设置 autoAck = false
        channel.basicConsume(queueName, false, new MyConsumer(channel));
    }
}
