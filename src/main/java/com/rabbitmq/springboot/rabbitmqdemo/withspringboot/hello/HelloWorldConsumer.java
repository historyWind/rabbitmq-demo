package com.rabbitmq.springboot.rabbitmqdemo.withspringboot.hello;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * hello world 模型的消费者
 */
@Component
// 表示监听名称叫hello的队列  默认是 持久化的、非独占的、非自动删除的队列.
// 该注解即可以放在类上，也可以放在方法上，放在方法上可以省略Rabbithandler注解
@RabbitListener(queuesToDeclare = @Queue(value = "hello",durable = "true"))
public class HelloWorldConsumer {

    @RabbitHandler
    public void receive(String message){
        System.out.println("message :" + message);
    }
}
