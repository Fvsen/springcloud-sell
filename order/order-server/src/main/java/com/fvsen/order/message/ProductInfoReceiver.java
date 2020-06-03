package com.fvsen.order.message;

import com.alibaba.fastjson.JSON;
import com.fvsen.order.dataobject.ProductInfo;
import com.fvsen.product.common.ProductInfoOutput;
import com.netflix.discovery.converters.Auto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * TODO
 *
 * @author Fvsen
 * @date 2020/4/13/013 23:28
 */
@Component
@Slf4j
public class ProductInfoReceiver {

    private static final String PRODUCT_STOCK_TEMPLATE = "product_stock_%s";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RabbitListener(queuesToDeclare = @Queue("productinfo"))
    public void process(String message) {
        List<ProductInfoOutput> productInfoOutputList = JSON.parseArray(message, ProductInfoOutput.class);
        log.info("收到队列: {} 的扣库存消息：{}", "prodcutinfo", productInfoOutputList);
        for(ProductInfoOutput productInfoOutput : productInfoOutputList){
            stringRedisTemplate.opsForValue().set(String.format(PRODUCT_STOCK_TEMPLATE, productInfoOutput.getProductId()),
                    String.valueOf(productInfoOutput.getProductStock()));
        }
    }



}
