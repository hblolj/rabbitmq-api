package com.hblolj.rabbitmq.api.exchange.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author: hblolj
 * @Date: 2018/8/16 10:40
 * @Description:
 * @Version: 1.0
 **/
public class Procuder4TopicExchange {

    public static void main(String[] args) throws Exception{

        // 1. 创建 ConnectionFactory，进行设置
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("jtik.sunnywit.top");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("424487386");

        // 2. 通过连接工厂创建连接
        Connection connection = connectionFactory.newConnection();

        // 3. 通过连接创建 Channel
        Channel channel = connection.createChannel();

        // 4. 通过 Channel 发送数据
        String exchangeName = "test_topic_exchange";
        String routingKey1 = "deviceFault.add";
        String routingKey2 = "deviceFault.seekHelp";
        String routingKey3 = "deviceFault.allocation.xxxx";
        String msg = "rabbitmq-topic";

        channel.basicPublish(exchangeName, routingKey1, null, msg.getBytes());
        channel.basicPublish(exchangeName, routingKey2, null, msg.getBytes());
        channel.basicPublish(exchangeName, routingKey3, null, msg.getBytes());

        // 5. 关闭连接
        channel.close();
        connection.close();

    }
}
