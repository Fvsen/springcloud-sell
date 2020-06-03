package com.fvsen.order;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;

import java.util.Date;

@Component
public class MqSenderTest extends OrderApplicationTest {

    /*@Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void mqSender(){
        amqpTemplate.convertAndSend("myQueue", "now " + new Date());
    }*/

     @Autowired
     private AmqpTemplate amqpTemplate;
     @Test
     public void mqSender(){
          amqpTemplate.convertAndSend("myQueue", "现在时刻：" + new Date());
     }

     @Test
     public void sendComputer() {
          amqpTemplate.convertAndSend("myOrder", "computer", "现在时刻：" + new Date());
     }

     @Test
     public void sendFruit() {
          amqpTemplate.convertAndSend("myOrder", "fruit", "现在时刻：" + new Date());
     }
}
