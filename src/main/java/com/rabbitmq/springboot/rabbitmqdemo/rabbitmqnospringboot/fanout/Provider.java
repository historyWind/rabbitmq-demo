package com.rabbitmq.springboot.rabbitmqdemo.rabbitmqnospringboot.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.springboot.rabbitmqdemo.utils.RabbitmqUtils;

import java.io.IOException;

public class Provider {
    // 交换机类型
    public static  final String EXCHANGE_TYPE="fanout";// 广播类型
    public static void main(String[] args) throws IOException {
        //获取连接
        Connection connection = RabbitmqUtils.getConnection();
        // 获取通道
        Channel channel = connection.createChannel();
        // 将通道声明指定交换机
        // 参数1 交换机名称   参数2 交换机类型
        channel.exchangeDeclare("logs",EXCHANGE_TYPE);
        // 发布消息
        //basicPublish(String exchange, String routingKey, BasicProperties props, byte[] body)
        // 参数1 交换机名称，参数2 路由key，在该模式下用不到，参数3 额外参数，参数4 消息数据
        channel.basicPublish("logs","",null,"fanout type message".getBytes());
        // 关闭资源
        RabbitmqUtils.close(channel,connection);
    }
}
