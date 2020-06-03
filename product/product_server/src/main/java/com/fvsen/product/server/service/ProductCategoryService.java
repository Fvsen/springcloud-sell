package com.fvsen.product.server.service;

import com.fvsen.product.server.dataobject.ProductCategory;

import java.util.List;

public interface ProductCategoryService {

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categeryTypeList);
}
