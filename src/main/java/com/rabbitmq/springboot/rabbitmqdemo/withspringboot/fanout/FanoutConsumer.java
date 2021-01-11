package com.rabbitmq.springboot.rabbitmqdemo.withspringboot.fanout;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
// 第三种fanout 广播模型
@Component
public class FanoutConsumer {

    // 第三种fanout广播模式
    // 第1个消费者
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue, // 如果不写queue的名称，表明生成临时队列，因为在有交换机的模式下，只生成临时队列就好了
                    exchange = @Exchange(value = "logs",type = "fanout") // 指定交换机的名称与类型
            )
    })
    public void receive1(String message){
        System.out.println("消费者1 message : " + message );
    }

    // 第三种fanout广播模式
    // 第2个消费者
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue, // 如果不写queue的名称，表明生成临时队列，因为在有交换机的模式下，只生成临时队列就好了
                    exchange = @Exchange(value = "logs",type = "fanout") // 指定交换机的名称与类型
            )
    })
    public void receive2(String message){
        System.out.println("消费者2 message : " + message );
    }
}
