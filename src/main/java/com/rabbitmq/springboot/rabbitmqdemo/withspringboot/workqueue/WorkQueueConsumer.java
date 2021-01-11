package com.rabbitmq.springboot.rabbitmqdemo.withspringboot.workqueue;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class WorkQueueConsumer {

    // workqueue模式的第1个消费者
    // RabbitListener可以放在方法上，也可以放在类上。queuesToDeclare表示声明队列
    @RabbitListener(queuesToDeclare = @Queue(value = "workqueue"))
    public  void receive1(String message){
        System.out.println("消费者1 消费的 message : " + message);
    }


    // workqueue模式的第2个消费者
    // RabbitListener可以放在方法上，也可以放在类上。queuesToDeclare表示声明队列
    @RabbitListener(queuesToDeclare = @Queue(value = "workqueue"))
    public  void receive2(String message){
        System.out.println("消费者2 消费的 message : " + message);
    }

}
