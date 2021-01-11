package com.rabbitmq.springboot.rabbitmqdemo.rabbitmqnospringboot.workquene;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.springboot.rabbitmqdemo.utils.RabbitmqUtils;

import java.io.IOException;

/**
 * work quenes
 * 这种模式指的是有多个消费者共同消费同一个队列，默认情况下消费者是平均消费该队列（循环），
 * 但是如果有一个队列消费速度比较慢，那么会造成消息堆积，所以我们想按照能者多劳，能力强的消费者多处理消息，能力弱的消费者少处理消息
 * 或者某一个队列拿到了平均分配的消息，但是在处理到一半的时候发生了问题，导致后面的消息都无法处理，导致消息丢失。
 * 那么就需要下面的解决方案
 * 实现逻辑：
 *  1.每次只从队列中拿出1个消息进行处理，防止一次拿走多个，否则有可能拿走的多个，在处理到一半的的时候，发生问题，导致后面的消息丢失，无法处理
 *  channel.basicQos(1);消费端 指定每次只消费队列中的一个 consumer1 line 14
 *  关闭自动确认机制，自动确认机制如果开启在消费者从队列中获取消息后（不一定来得及消费），就会自动确认
 *  2.channel.basicConsume("",false,new DefaultConsumer(channel){})  consumer1 line 18
 *  手动进行确认
 *  做到取1个，消费1个，手动确认1个,那么没有被取走的就可以由另外的消费者进行消费了
 *  3.channel.basicAck(envelope.getDeliveryTag(),false); consumer1 line 33
 *  如果某一个消费者 在消费信息后没有进行手动确认，那么在就会在队列中会存在Unacked的消息，这时当该消费者关闭后，这些unacked的消息会重新被其它消费者进行消费，
 *  这样会造成重复消费
 *
 * 关于手动确认和自动确认两者的介绍：
 * 自动确认： 在自动确认模式下，消息在发送后立即被认为是发送成功。 这种模式可以提高吞吐量（只要消费者能够跟上），
 *          不过会降低投递和消费者处理的安全性。 这种模式通常被称为“发后即忘”。 与手动确认模式不同，如果消费者的TCP连接或信道在成功投递之前关闭，
 *          该消息则会丢失。
 * 手动确认： 使用自动确认模式时需要考虑的另一件事是消费者过载。
 *          手动确认模式通常与有限的信道预取一起使用，限制信道上未完成（“进行中”）传送的数量。
 *          然而，对于自动确认，根据定义没有这样的限制。 因此，消费者可能会被交付速度所压倒，可能积压在内存中，堆积如山，或者被操作系统终止。
 *          某些客户端库将应用TCP反压（直到未处理的交付积压下降超过一定的限制时才停止从套接字读取）。
 *          因此，只建议当消费者可以有效且稳定地处理投递时才使用自动投递方式。
 */
public class Provider {
    public static void main(String[] args) throws IOException {
        // 获取连接
        Connection connection = RabbitmqUtils.getConnection();
        // 创建通道对象
        Channel channel = connection.createChannel();
        // 通过通道声明队列,队列名称，是否持久化，是否独占队列，是否自动删除，额外参数
        channel.queueDeclare("work",true,false,false,null);
        // 循环生产消息
        for (int i = 0; i <10 ; i++) {
            channel.basicPublish("","work",null, (i + " hello work quene").getBytes());
        }
        // 关闭资源
        RabbitmqUtils.close(channel,connection);
    }
}
