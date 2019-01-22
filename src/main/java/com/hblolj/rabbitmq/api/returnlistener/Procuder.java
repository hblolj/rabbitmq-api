package com.hblolj.rabbitmq.api.returnlistener;

import com.rabbitmq.client.*;

import java.io.IOException;
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

        String exchangeName = "test_return_exchange";
        String routingKey = "return.save";
        String routingKeyError = "abc.save";

        channel.addReturnListener((replyCode, replyText, exchange, routingKey1, properties, body) -> {
            System.err.println("---------handle  return----------");
            System.err.println("replyCode: " + replyCode);
            System.err.println("replyText: " + replyText);
            System.err.println("exchange: " + exchange);
            System.err.println("routingKey: " + routingKey1);
            System.err.println("properties: " + properties);
            System.err.println("body: " + new String(body));
        });

        // 4. 通过 Channel 发送数据
        String msg = "rabbitmq";
        channel.basicPublish(exchangeName, routingKeyError, true, null, msg.getBytes());



        // 5. 关闭连接
//        channel.close();
//        connection.close();
    }
}
