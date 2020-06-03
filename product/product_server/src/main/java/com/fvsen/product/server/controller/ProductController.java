package com.fvsen.product.server.controller;

import com.fvsen.product.common.DecreaseStockInput;
import com.fvsen.product.server.VO.ProductInfoVO;
import com.fvsen.product.server.VO.ProductVO;
import com.fvsen.product.server.VO.ResultVO;
import com.fvsen.product.server.VO.ResultVOUtil;
import com.fvsen.product.server.dataobject.ProductCategory;
import com.fvsen.product.server.dataobject.ProductInfo;
import com.fvsen.product.server.service.ProductCategoryService;
import com.fvsen.product.server.service.ProductInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 1.查询所有在架商品
 * 2.获取类目type列表
 * 3.查询类目
 * 4.构造数据
 */
@RestController
@RequestMapping("/product")
@Slf4j
public class ProductController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("list")
    public ResultVO findProduct(HttpServletRequest request){
        //查询所有在架商品
        List<ProductInfo> productInfoList = productInfoService.findAll();

         //获取类目type列表
        List<Integer> categoryTypeList = productInfoList.stream().map(ProductInfo::getCategoryType).collect(Collectors.toList());

        //查询类目
        List<ProductCategory> categoryList = productCategoryService.findByCategoryTypeIn(categoryTypeList);

        //构造数据
        List<ProductVO> productVOList = new ArrayList<>();
        for(ProductCategory productCategory : categoryList){
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());

            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for(ProductInfo productInfo :productInfoList) {
                if(productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }

            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }

        return ResultVOUtil.success(productVOList);
    }

    @PostMapping("/listForOrder")
    public List<ProductInfo> getProductInfo(@RequestBody List<String> productIdList){
        List<ProductInfo> productInfoList = productInfoService.findInfo(productIdList);
        return productInfoList;
    }

    @PostMapping("decreaseStock")
    public void decreaseStock(@RequestBody List<DecreaseStockInput> decreaseStockInputList){
        productInfoService.decreaseStock(decreaseStockInputList);
    }

}
