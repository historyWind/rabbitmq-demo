package com.rabbitmq.springboot.rabbitmqdemo.withspringboot.route;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RouteConsumer {

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue, // 声明临时队列
                    exchange = @Exchange(value = "directs", type = "direct"),// 指定交换机的名称与类型
                    key = {"info", "error"}
            )
    })
    public void receive1(String message) {
        System.out.println("消费者1 message： " + message);
    }


    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue, // 声明临时队列
                    exchange = @Exchange(value = "directs", type = "direct"),// 指定交换机的名称与类型
                    key = {"error"}
            )
    })
    public void receive2(String message) {
        System.out.println("消费者2 message： " + message);
    }
}
