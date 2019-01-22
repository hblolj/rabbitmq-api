package com.hblolj.rabbitmq.api.quickstart;

import com.rabbitmq.client.*;

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
        String queueName = "test001";
        channel.queueDeclare(queueName, true, false, false, null);

        // 5. 创建一个消费者
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);

        // 6. 设置 Channel
        channel.basicConsume(queueName, true, queueingConsumer);

        // 7. 获取消息
        while (true){
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String msg = new String(delivery.getBody());
            System.out.println("消费端收到的消息: " + msg);
//            Envelope envelope = delivery.getEnvelope();
        }
    }
}
