package com.fvsen.order.message.review;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

/**
 *
 * @author Fvsen
 * @date 2020/4/9/009 21:35
 */
@Component
@EnableBinding(StreamClient1.class)
@Slf4j
public class StreamReceiver1 {

    @StreamListener(StreamClient1.INPUT)
    public void sendReview(Object message) {
        log.info("reviewReceiver: {}", message);
    }

}
