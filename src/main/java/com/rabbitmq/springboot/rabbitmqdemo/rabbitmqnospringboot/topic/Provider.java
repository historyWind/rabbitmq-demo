package com.rabbitmq.springboot.rabbitmqdemo.rabbitmqnospringboot.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.springboot.rabbitmqdemo.utils.RabbitmqUtils;

import java.io.IOException;

/**
 * topic动态路由模式
 * * 表示匹配一个
 * # 表示匹配多个
 */
public class Provider {
    public static  final  String TOPICS_TYPE = "topic";
    public static  final  String EXCHANGE_NAME = "topics_exchange";
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitmqUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME,TOPICS_TYPE);
        String routeKey = "user.save.xxx";
        channel.basicPublish(EXCHANGE_NAME,routeKey,null,("动态路由 之 topics模式: "+ routeKey).getBytes());
        RabbitmqUtils.close(channel,connection);

    }
}
