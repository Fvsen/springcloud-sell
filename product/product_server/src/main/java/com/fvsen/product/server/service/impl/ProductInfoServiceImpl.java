package com.fvsen.product.server.service.impl;


import com.alibaba.fastjson.JSON;
import com.fvsen.product.common.DecreaseStockInput;
import com.fvsen.product.common.ProductInfoOutput;
import com.fvsen.product.server.dataobject.ProductInfo;
import com.fvsen.product.server.enums.ProductStatusEnums;
import com.fvsen.product.server.enums.ResultEnum;
import com.fvsen.product.server.exception.ProductException;
import com.fvsen.product.server.repository.ProductInfoRepository;
import com.fvsen.product.server.service.ProductInfoService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public List<ProductInfo> findAll() {
        return productInfoRepository.findProductInfoByProductStatus(ProductStatusEnums.UP.getCode());
    }

    /**
     * 根据productId获取商品列表
     * @param productIdList
     * @return
     */
    @Override
    public List<ProductInfo> findInfo(List<String> productIdList) {

        return productInfoRepository.findByProductIdIn(productIdList);
    }

    @Override
    @Transactional      //此注解用于开启事务
    public void decreaseStock(List<DecreaseStockInput> decreaseStockInputList) {
        //调用方法完成数据库操作
        List<ProductInfo> productInfoList = decreaseStockProcess(decreaseStockInputList);

        //将数据封装成为ProductInfoOutput
        List<ProductInfoOutput> productInfoOutputList = productInfoList.stream().map(e -> {
            ProductInfoOutput output = new ProductInfoOutput();
            BeanUtils.copyProperties(e, output);
            return output;
        }).collect(Collectors.toList());
        amqpTemplate.convertAndSend("productinfo", JSON.toJSONString(productInfoOutputList));
    }


    @Transactional
    public List<ProductInfo> decreaseStockProcess(List<DecreaseStockInput> decreaseStockInputList) {
        List<ProductInfo> productInfoList = new ArrayList<>();
        for(DecreaseStockInput decreaseStockInput : decreaseStockInputList) {
            Optional<ProductInfo> productInfoOptional = productInfoRepository.findById(decreaseStockInput.getProductId());
            //判断商品是否存在
            if(!productInfoOptional.isPresent()) {
                throw new ProductException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            ProductInfo productInfo = productInfoOptional.get();
            Integer result = productInfo.getProductStock() - decreaseStockInput.getProductQuantity();
            //判断库存是否足够
            if(result < 0){
                throw new ProductException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);
            productInfoRepository.save(productInfo);
            productInfoList.add(productInfo);
        }
        return productInfoList;
    }

}
