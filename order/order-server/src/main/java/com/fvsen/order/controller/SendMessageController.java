package com.fvsen.order.controller;

import com.fvsen.order.dto.OrderDTO;
import com.fvsen.order.message.StreamClient;
import com.fvsen.order.message.review.StreamClient1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TODO
 *
 * @author Fvsen
 * @date 2020/4/8/008 23:08
 */
@RestController
public class SendMessageController {

    @Autowired
    private StreamClient streamClient;

    @Autowired
    private StreamClient1 streamClient1;

    @GetMapping("/myMessage")
    public void process() {
        streamClient.output().send(MessageBuilder.withPayload("现在时刻：" + new Date()).build());
    }


    @GetMapping("/reviewMessage")
    public void review() {
        streamClient1.output().send(MessageBuilder.withPayload(
                new SimpleDateFormat("现在时刻: yyyy-MM-dd hh:mm:ss").format(new Date())).build());
    }

    @GetMapping("/sendPojo")
    public void sendDTO() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId("123456");
        streamClient.output().send(MessageBuilder.withPayload(orderDTO).build());
    }

}
