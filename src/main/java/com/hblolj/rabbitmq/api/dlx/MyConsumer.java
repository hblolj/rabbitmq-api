package com.hblolj.rabbitmq.api.dlx;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

/**
 * @author: hblolj
 * @Date: 2018/8/16 15:15
 * @Description:
 * @Version: 1.0
 **/
public class MyConsumer extends DefaultConsumer {

    /**
     * Constructs a new instance and records its association to the passed-in channel.
     *
     * @param channel the channel to which this consumer is attached
     */
    public MyConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

        System.err.println("-----------consume message----------");
        System.err.println("body: " + new String(body));
//        Integer num = (Integer) properties.getHeaders().get("num");
//        if (num == 0){
//            getChannel().basicNack(envelope.getDeliveryTag(), false, true);
//        }else {
//            getChannel().basicAck(envelope.getDeliveryTag(), false);
//        }
    }
}
