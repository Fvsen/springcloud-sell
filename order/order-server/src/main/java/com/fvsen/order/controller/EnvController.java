package com.fvsen.order.controller;

import com.fvsen.order.config.GirlConfig;
import com.fvsen.order.dataobject.OrderDetail;
import com.fvsen.order.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/env")
@RefreshScope
public class EnvController {

    @Value("${env}")
    private String env;
//
//    @Value("${eureka}")
//    private String eureka;
//
    @GetMapping("/print")
    public String peint(){
        return env;
    }
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @GetMapping("getMsg")
    public List<OrderDetail> getMsg(){
        return orderDetailRepository.findAll();
    }

    @Autowired
    private GirlConfig girlConfig;

    @GetMapping("girl")
    public String printGirl(){
        return girlConfig.getName() + " is " + girlConfig.getAge() + " years old.";
    }

}
