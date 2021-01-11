package com.rabbitmq.springboot.rabbitmqdemo;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = RabbitmqDemoApplication.class)
public class MqTest {
    // 注入rabbitmq
    @Autowired
    private RabbitTemplate rabbitTemplate;



    // 如果没有消费者监听，那么调用该方法生产者不会起作用
    // helloWorld 模式的生产者
    @Test
    public void testHello(){
        rabbitTemplate.convertAndSend("hello","hello world");
    }

    // 第二种 workqueue工作队列
    @Test
    public  void testWorkQueue(){
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("workqueue",i + " work queue message");
        }
    }

    // 第三种fanout 广播模型
    @Test
    public  void testFanout(){
        rabbitTemplate.convertAndSend("logs","","fanout 模型消息");
    }


    // 第四种 route 路由模式
    @Test
    public void testRoute(){
        rabbitTemplate.convertAndSend("directs","error","发送error的路由key的信息");
    }
    //第五种 Topics 动态路由
    @Test
    public  void testTopics(){
        rabbitTemplate.convertAndSend("topics","order.save","topics message order 订单");
    }
}
