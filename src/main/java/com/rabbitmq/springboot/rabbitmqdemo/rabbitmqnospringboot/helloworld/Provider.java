package com.rabbitmq.springboot.rabbitmqdemo.rabbitmqnospringboot.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.springboot.rabbitmqdemo.utils.RabbitmqUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 直连模式
 *  生产者
 */
public class Provider {
        private static  final  String BASIC_QUEUE_NAME = "hello";
    public static void main(String[] args) throws IOException, TimeoutException {
        /*// 创建连接mq的连接工厂对象
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 设置连接rabbitmq的主机
        connectionFactory.setHost("192.168.235.44");
        // 设置端口号
        connectionFactory.setPort(5672);
        // 设置连接哪个虚拟主机
        connectionFactory.setVirtualHost("/ems");
        // 设置虚拟主机对应的用户的账号和密码
        connectionFactory.setUsername("lcb");
        connectionFactory.setPassword("lcb");
        // 获取连接对象
        Connection connection = connectionFactory.newConnection();*/

        // 以上代码改为调用工具类,获取连接
        Connection connection = RabbitmqUtils.getConnection();

        // 获取连接通道
        Channel channel = connection.createChannel();
        //queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments)
        // 通道绑定对应的消息队列
        // 参数1：队列名称，如果队列不存在，会自动创建队列
        // 参数2：用来定义队列特性是否需要持久化 true持久化（在页面上的Features会显示D）、false非持久化，表示在rabbitmq重启后，队列是否还存在.
        //       如果队列中有消息没有被消费，那么在重启后如果未持久化，则消息会丢失，
        //       但是该持久化，只是对队列支持持久化，不对消息支持持久化，即使队列持久化了，重启mq后，队列中未被消费的消息仍然会丢失。
        //       如果要保证队列中未被消费的消息也能持久化，需要在channel.basicPublish方法中的basicProperties的值设置为MessageProperties.PERSISTENT_TEXT_PLAIN
        // 参数3：exclusive ，表示是否独占队列，true表示在该队列只允许当前连接可用，false表示该队列也可以被其他连接使用
        // 参数4：autodelete，是否在消费完成后自动删除队列，true自动删除（在页面上的Features会显示AD），false不自动删除
        //       自动删除，需要消费者与队列断开连接后，才会删除队列，不是没有消息了就删除队列
        // 参数5：额外附加参数
        channel.queueDeclare(BASIC_QUEUE_NAME,true,false,true,null);
        String message = "hello world rabbitmq=======";
        // 发布消息，同一个通道可以同时向多个不同的队列中发送消息
        // 参数1：交换机名称
        // 参数2：队列名称
        // 参数3：传递消息额外设置
        // 参数4：消息的具体内容
        channel.basicPublish("",BASIC_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
        // 如果aa队列存在，则同时会向aa队列发送消息
        //channel.basicPublish("","aa",null,message.getBytes());

        /*channel.close();
        connection.close();*/
        // 调用工具类关闭连接
        RabbitmqUtils.close(channel,connection);
    }
}
