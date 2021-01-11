package com.rabbitmq.springboot.rabbitmqdemo.rabbitmqnospringboot.direct;

import com.rabbitmq.client.*;
import com.rabbitmq.springboot.rabbitmqdemo.utils.RabbitmqUtils;

import java.io.IOException;

public class Consumer2 {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitmqUtils.getConnection();
        Channel channel = connection.createChannel();
        // 声明交换机
        channel.exchangeDeclare("log_direct",Provider.EXCHANGE_TYPE_DIRECT);
        // 创建临时队列
        String queueName = channel.queueDeclare().getQueue();
        // 基于route key 绑定交换机和临时队列
        channel.queueBind(queueName,"log_direct","error");
        channel.queueBind(queueName,"log_direct","info");
        channel.queueBind(queueName,"log_direct","warning");

        channel.basicConsume(queueName,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者2 消费信息 ：" + new String(body) );
            }
        });


    }
}
