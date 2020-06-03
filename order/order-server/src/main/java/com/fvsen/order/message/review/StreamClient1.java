package com.fvsen.order.message.review;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 *
 * @author Fvsen
 * @date 2020/4/9/009 21:33
 */
public interface StreamClient1 {

    String INPUT = "reviewMsg";

    @Input(StreamClient1.INPUT)
    SubscribableChannel input();

    @Output(StreamClient1.INPUT)
    MessageChannel output();
}