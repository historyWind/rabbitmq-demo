package com.rabbitmq.springboot.rabbitmqdemo.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitmqUtils {
    private static  ConnectionFactory connectionFactory;

    // 只在类加载时创建一次
    static {
        // 创建连接mq的连接工厂对象
        connectionFactory = new ConnectionFactory();
        // 设置连接rabbitmq的主机
        connectionFactory.setHost("192.168.235.44");
        // 设置端口号
        connectionFactory.setPort(5672);
        // 设置连接哪个虚拟主机
        connectionFactory.setVirtualHost("/ems");
        // 设置虚拟主机对应的用户的账号和密码
        connectionFactory.setUsername("lcb");
        connectionFactory.setPassword("lcb");
    }

    // 定义提供连接的方法
    public static Connection getConnection() {
        try {
            // 获取连接对象
            Connection connection = connectionFactory.newConnection();
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 关闭连接
    public static void close(Channel channel,Connection connection){
        try{
            if(channel != null){
                channel.close();
            }
            if(connection != null){
                connection.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
