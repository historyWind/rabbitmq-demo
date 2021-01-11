package com.rabbitmq.springboot.rabbitmqdemo.rabbitmqnospringboot.topic;

import com.rabbitmq.client.*;
import com.rabbitmq.springboot.rabbitmqdemo.utils.RabbitmqUtils;

import java.io.IOException;

public class Consumer2 {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitmqUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(Provider.EXCHANGE_NAME,Provider.TOPICS_TYPE);
        String queue = channel.queueDeclare().getQueue();
        // * 表示匹配一个单词   # 表示匹配多个单词
        channel.queueBind(queue,Provider.EXCHANGE_NAME,"user.#");
        channel.basicConsume(queue,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者2：" + new String(body));
            }
        });

    }
}
