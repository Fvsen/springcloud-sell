package com.fvsen.product.server.service;


import com.fvsen.product.common.DecreaseStockInput;
import com.fvsen.product.server.dataobject.ProductInfo;

import java.util.List;

public interface ProductInfoService {

    List<ProductInfo> findAll();


    List<ProductInfo> findInfo(List<String> productIdList);

    void decreaseStock(List<DecreaseStockInput> decreaseStockInputList);
}
