package com.fvsen.product.client;

import com.fvsen.product.common.DecreaseStockInput;
import com.fvsen.product.common.ProductInfoOutput;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "product", fallback = ProductClient.ProductClientFallback.class)
public interface ProductClient {

    @GetMapping("/msg")
    public String productMsg();


    @PostMapping("/product/listForOrder")
    List<ProductInfoOutput> findProductInfoByProductId(@RequestBody List<String> productIdList);

    @PostMapping("/product/decreaseStock")
    void decreaseStock(@RequestBody List<DecreaseStockInput> cartDTOList);


    /**
     * 当触发降级时，会调用以下类中的对应方法
     */
    @Component
    static class ProductClientFallback implements ProductClient {

        @Override
        public String productMsg() {
            return null;
        }

        @Override
        public List<ProductInfoOutput> findProductInfoByProductId(List<String> productIdList) {
            return null;
        }

        @Override
        public void decreaseStock(List<DecreaseStockInput> cartDTOList) {

        }
    }
}