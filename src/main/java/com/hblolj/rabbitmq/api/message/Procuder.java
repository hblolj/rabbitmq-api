package com.hblolj.rabbitmq.api.message;

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

        Map<String, Object> headers = new HashMap<>();
        headers.put("value1", "ori");
        headers.put("value2", "zard");

        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .deliveryMode(2)
                .contentEncoding("UTF-8")
                .expiration("5000")
                .headers(headers)
                .build();

        // 4. 通过 Channel 发送数据
        String msg = "rabbitmq-";
        for (int i=0; i<5; i++){
            msg = msg + i;
            channel.basicPublish("", "test001", properties, msg.getBytes());
        }

        // 5. 关闭连接
        channel.close();
        connection.close();
    }
}
