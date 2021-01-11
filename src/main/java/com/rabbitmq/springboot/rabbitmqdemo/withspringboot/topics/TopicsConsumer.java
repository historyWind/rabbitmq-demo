package com.rabbitmq.springboot.rabbitmqdemo.withspringboot.topics;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 第五种类型  topics，动态路由
 */
@Component
public class TopicsConsumer {
    // 第1个消费者
    @RabbitListener(bindings = {
            @QueueBinding( // 绑定队列与交换机
                    value = @Queue, // 声明临时队列
                    exchange = @Exchange(value = "topics", type = "topic"), // 声明交换机的名称与类型
                    key = {"user.save", "user.*"} // 声明动态匹配规则，* 表示匹配一个单词，# 表示匹配多个单词
            )
    })
    public void receive1(String message) {
        System.out.println("消费者1 message： " + message);
    }


    // 第2个消费者
    @RabbitListener(bindings = {
            @QueueBinding( // 绑定队列与交换机
                    value = @Queue, // 声明临时队列
                    exchange = @Exchange(value = "topics", type = "topic"), // 声明交换机的名称与类型
                    key = {"user.*", "order.#","product.#"} // 声明动态匹配规则，* 表示匹配一个单词，# 表示匹配多个单词
            )
    })
    public void receive2(String message) {
        System.out.println("消费者2 message： " + message);
    }
}
