package com.fvsen.product.server.repository;


import com.fvsen.product.server.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {
    List<ProductInfo> findProductInfoByProductStatus(Integer productStatus);

    List<ProductInfo> findByProductIdIn(List<String> productIdList);


}
