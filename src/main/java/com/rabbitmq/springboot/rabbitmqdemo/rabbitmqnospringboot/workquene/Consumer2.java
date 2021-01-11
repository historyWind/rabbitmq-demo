package com.rabbitmq.springboot.rabbitmqdemo.rabbitmqnospringboot.workquene;

import com.rabbitmq.client.*;
import com.rabbitmq.springboot.rabbitmqdemo.utils.RabbitmqUtils;

import java.io.IOException;

public class Consumer2 {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitmqUtils.getConnection();
        // 创建通道
        Channel channel = connection.createChannel();
        // 指定每次只消费队列中的一个,做到取1个，消费1个，手动确认1个
        channel.basicQos(1);
        // 通道声明队列
        channel.queueDeclare("work",true,false,false,null);
        //  消费消息,关闭自动确认，改为手动确认（autoAck:true-->false）
        channel.basicConsume("",false,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者-2：" + new String(body));
                // 手动确认 参数1：手动确认消息标识 参数2：false表示每次只确认1个
                // basicAck(long deliveryTag, boolean multiple)
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        });

    }
}
