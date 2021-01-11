package com.rabbitmq.springboot.rabbitmqdemo.rabbitmqnospringboot.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.springboot.rabbitmqdemo.utils.RabbitmqUtils;

import java.io.IOException;

public class Provider {
    public  static final  String EXCHANGE_TYPE_DIRECT = "direct";
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitmqUtils.getConnection();
        Channel channel = connection.createChannel();
        // 声明交换剂 参数1 交换机名称  参数2 交换机类型
        channel.exchangeDeclare("log_direct",EXCHANGE_TYPE_DIRECT);
        // 发送消息
        String routingKey ="error";
        channel.basicPublish("log_direct",routingKey,null,("routing key direct [" + routingKey+" ]").getBytes());
        //关闭连接
        RabbitmqUtils.close(channel,connection);
    }
}
