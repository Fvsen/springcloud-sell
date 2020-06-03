package com.fvsen.order.message;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class MqReceiver {
    //方法一：只接受指定队列的消息，不会新建队列，如果消息中间件上没有对应的队列则会报错
    // @RabbitListener(Queues = "myQueue")
    //方法二：在方法一的基础上自动创建新的队列
    // @RabbitListener(queuesToDeclare = @Queue("myQueue"))
    //方法三：在方法二的基础上增加了绑定 【Exchange】的功能
    @RabbitListener(bindings = @QueueBinding(
            value= @Queue("myQueue"),
            exchange = @Exchange("myExchange")
    ))
    public void myQueue(String message) {
        log.info("MqReceiver: {}", message);
    }

    /**
     * 在实际生产中，有时会需要将同一类型的队列进行分组，
     * 如在订单服务中，各类订单的消息队列都binding在同一个【Exchange】中，
     * 将电商的订单和水果的订单区分开来
     *
     *数码供应商服务，接收消息
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange("myOrder"),
            key = "computer",
            value = @Queue("computerOrder")
    ))
    public void processComputer(String message) {
        log.info("computer MqReceiver: {}", message);
    }


    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange("myOrder"),
            key = "fruit",
            value = @Queue("fruitOrder")
    ))
    public void processFruit(String message){
        log.info("fruit MqReceiver: {}", message);
    }


}
