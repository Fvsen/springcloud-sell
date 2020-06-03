package com.fvsen.product.server.service.impl;

import com.fvsen.product.server.dataobject.ProductCategory;
import com.fvsen.product.server.repository.ProductCategoryRepository;
import com.fvsen.product.server.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categeryTypeList) {
        return productCategoryRepository.findByCategoryTypeIn(categeryTypeList);
    }
}
