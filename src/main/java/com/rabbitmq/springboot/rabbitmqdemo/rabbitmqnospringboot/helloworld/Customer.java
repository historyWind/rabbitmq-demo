package com.rabbitmq.springboot.rabbitmqdemo.rabbitmqnospringboot.helloworld;

import com.rabbitmq.client.*;
import com.rabbitmq.springboot.rabbitmqdemo.utils.RabbitmqUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Customer {
    private static  final  String BASIC_QUEUE_NAME = "hello";
    public static void main(String[] args) throws IOException, TimeoutException {
        /*// 创建连接
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 设置主机
        connectionFactory.setHost("192.168.235.44");
        // 设置端口
        connectionFactory.setPort(5672);
        // 设置虚拟主机
        connectionFactory.setVirtualHost("/ems");
        // 设置虚拟主机对应的用户的账号密码
        connectionFactory.setUsername("lcb");
        connectionFactory.setPassword("lcb");
        // 创建连接对象
        Connection connection = connectionFactory.newConnection();*/

        // 以上代码改为调用工具类,获取连接
        Connection connection = RabbitmqUtils.getConnection();

        // 创建通道
        Channel channel = connection.createChannel();
        // 通道绑定队列。消费者的通道绑定队列需要与生产者的参数完全一致(队列的名称与是否持久化需要一致)，否则会报错channel error; protocol method
        channel.queueDeclare(BASIC_QUEUE_NAME,true,false,true,null);
        // 消费队列的消息.
        // 参数1：指定需要消费的队列的名称
        // 参数2：开启消息的自动确认机制
        // 参数3：消费消息时的回调接口
        channel.basicConsume(BASIC_QUEUE_NAME,true,new DefaultConsumer(channel){
            // String consumerTag,
            // Envelope envelope,
            // AMQP.BasicProperties properties,
            // byte[] body 消息队列中取出的消息
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //super.handleDelivery(consumerTag, envelope, properties, body);
                System.out.println("message is : " + new String(body));
            }
        });

    }
}
