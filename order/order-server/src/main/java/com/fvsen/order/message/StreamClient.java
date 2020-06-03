package com.fvsen.order.message;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 *
 * @author Fvsen
 * @date 2020/4/8/008 22:56
 */
public interface StreamClient {

    String INPUT = "myMessage";

    String RECEIVED = "received";


    @Input(StreamClient.INPUT)
    SubscribableChannel input();

    @Output(StreamClient.INPUT)
    MessageChannel output();

    @Input(StreamClient.RECEIVED)
    SubscribableChannel accepterInput();

    @Output(StreamClient.RECEIVED)
    MessageChannel accepterOutput();
}
