package com.hblolj.rabbitmq.api.dlx;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author: hblolj
 * @Date: 2018/8/16 9:33
 * @Description:
 * @Version: 1.0
 **/
public class Procuder {

    public static void main(String[] args) throws IOException, TimeoutException {

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
        // 指定消息投递模式
        channel.confirmSelect();

        String exchangeName = "test_dlx_exchange";
        String routingKey = "dlx.save";

        // 4. 通过 Channel 发送数据
        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .deliveryMode(2)
                .contentEncoding("UTF-8")
                .expiration("10000")
                .build();

        String msg = "rabbitmq";
        channel.basicPublish(exchangeName, routingKey, properties, msg.getBytes());

        // 5. 关闭连接
        channel.close();
        connection.close();
    }
}
