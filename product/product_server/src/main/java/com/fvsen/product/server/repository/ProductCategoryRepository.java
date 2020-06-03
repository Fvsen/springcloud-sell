package com.fvsen.product.server.repository;

import com.fvsen.product.server.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//JpaRepository<ProductCategory, Integer>  参数2：主键类型
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {

//    ProductCategory findAllByCategoryType(Integer categoryType);

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categeryTypeList);
}
