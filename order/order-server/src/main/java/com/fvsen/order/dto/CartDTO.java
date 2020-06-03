package com.fvsen.order.dto;

import lombok.Data;

@Data
public class CartDTO {

    /**
     * 商品id
     */
    private String productId;

    /**
     * 订单商品个数
     */
    private Integer  productQuantity;

    public CartDTO() {
    }

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
