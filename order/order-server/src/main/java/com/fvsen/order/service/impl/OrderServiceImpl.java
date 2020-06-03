package com.fvsen.order.service.impl;

import com.fvsen.order.dataobject.OrderDetail;
import com.fvsen.order.dataobject.OrderMaster;
import com.fvsen.order.dto.OrderDTO;
import com.fvsen.order.enums.OrderStatusEnum;
import com.fvsen.order.enums.PayStatusEnum;
import com.fvsen.order.enums.ResultEnum;
import com.fvsen.order.exception.OrderException;
import com.fvsen.order.repository.OrderDetailRepository;
import com.fvsen.order.repository.OrderMasterRepository;
import com.fvsen.order.service.OrderService;
import com.fvsen.order.utils.KeyUtil;
import com.fvsen.product.client.ProductClient;
import com.fvsen.product.common.DecreaseStockInput;
import com.fvsen.product.common.ProductInfoOutput;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductClient productClient;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId = KeyUtil.genUniqueKey();
        //      查询商品信息（调用商品服务）
        List<ProductInfoOutput> productInfoByProductId =
                productClient.findProductInfoByProductId(
                        orderDTO.getOrderDetailList().stream().map(OrderDetail::getProductId).collect(Collectors.toList()));
        //     计算总价
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        for(OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            for(ProductInfoOutput productInfo : productInfoByProductId){
                if(orderDetail.getProductId().equals(productInfo.getProductId())){
                    //BigDecimal的运算需要注意：通过方法实现，而不是用运算符，此处add()方法相当于+=
                    orderAmount = productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity()))
                            .add(orderAmount);
                    BeanUtils.copyProperties(productInfo, orderDetail);
                    orderDetail.setOrderId(orderId);
                    orderDetail.setDetailId(KeyUtil.genUniqueKey());
                    orderDetailRepository.save(orderDetail);
                }
            }

        }
    //      扣库存（调用商品服务）
        List<DecreaseStockInput> cartDTOList = orderDTO.getOrderDetailList().stream().
                map(e -> new DecreaseStockInput(e.getProductId(), e.getProductQuantity())).collect(Collectors.toList());
        productClient.decreaseStock(cartDTOList);

    //订单入库
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());

        orderMasterRepository.save(orderMaster);
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(String orderId) {
        Optional<OrderMaster> orderMasterOptional = orderMasterRepository.findById(orderId);
        // isPresent()方法，正确通过的返回值为true，表明值存在
        if(!orderMasterOptional.isPresent()) {
            throw new OrderException(ResultEnum.ORDER_NOT_EXIST);
        }
        //1、判断订单状态
        OrderMaster orderMaster = orderMasterOptional.get();
        if(!OrderStatusEnum.NEW.getCode().equals(orderMaster.getOrderStatus())) {
            throw new OrderException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //2、修改订单状态为完结
        orderMaster.setOrderStatus(OrderStatusEnum.FINISHED.getCode());

        //3、判断订单详情是否为空
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if(CollectionUtils.isEmpty(orderDetailList)) {
            throw new OrderException(ResultEnum.ORDER_NOT_EXIST);
        }
        //4、数据封装
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }
}
