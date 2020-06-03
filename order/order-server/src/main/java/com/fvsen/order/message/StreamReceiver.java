package com.fvsen.order.message;

import com.fvsen.order.dto.OrderDTO;
import com.fvsen.order.message.StreamClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @author Fvsen
 * @date 2020/4/8/008 23:04
 */
@Component
@EnableBinding(StreamClient.class)
@Slf4j
public class StreamReceiver {

//    @StreamListener(StreamClient.INPUT)
//    public void process(Object message) {
//        log.info("StreamReceiver: {}", message);
//    }


    @StreamListener(StreamClient.INPUT)
    /**
     * @SendTo 用于收到消息后发送一个消息到指定队列
     */
    @SendTo(StreamClient.RECEIVED)
    public String process(OrderDTO message) {
        log.info("StreamReceiver: {}", message);
        return "received.";
    }

    @StreamListener(StreamClient.RECEIVED)
    public void receiveMessage(String message) {
        log.info("received StreamReceiver: {}", message);
    }
}
