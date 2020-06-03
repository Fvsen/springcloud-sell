package com.fvsen.order.dto;

import com.fvsen.order.dataobject.OrderDetail;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDTO {

    /**
     * 主键
     */
    private String orderId;

    /**.
     * 卖家昵称
     */
    private String buyerName;

    /**
     * 买家电话
     */
    private String buyerPhone;

    /**
     * 地址
     */
    private String buyerAddress;

    /**
     * 用户特征id
     */
    private String buyerOpenid;

    /**
     * 订单总价
     */
    private BigDecimal orderAmount;

    /**
     * 订单状态
     */
    private Integer orderStatus;

    /**
     * 支付状态
     */
    private Integer payStatus;

    /**
     * 订单详情
     */
    private List<OrderDetail> orderDetailList;
}
