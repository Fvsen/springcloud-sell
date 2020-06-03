package com.fvsen.order.controller;

import com.fvsen.product.client.ProductClient;
import com.fvsen.product.common.DecreaseStockInput;
import com.fvsen.product.common.ProductInfoOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
public class ClientFeignController {

    @Autowired
    private ProductClient productClient;

    @GetMapping("/getMsg")
    public String getProductMsg(){
        String response = productClient.productMsg();
        log.info("response={}", response);
        return response;
    }

    @GetMapping("getInfo")
    public String getProductInfo(){
        List<ProductInfoOutput> productInfoList =
                productClient.findProductInfoByProductId(Arrays.asList("157875196366160022", "164103465734242707"));
        log.info("结果为：" + productInfoList);
        return ("ok");
    }

    @GetMapping("decreaseStock")
    public String decreaseStock(){
        DecreaseStockInput cartDTO = new DecreaseStockInput("157875196366160022", 7);
        productClient.decreaseStock(Arrays.asList(cartDTO));
        return "ok";
    }

}
