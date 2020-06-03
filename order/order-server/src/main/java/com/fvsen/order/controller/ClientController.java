package com.fvsen.order.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 *      使用RestTemplate调用接口的方法
 *
 */
@RestController
@Slf4j
public class ClientController {

//    方法二
    @Autowired
    private LoadBalancerClient loadBalancerClient;

//    方法三
//    @Autowired
//    private RestTemplate restTemplate;


    @GetMapping("getProductMsg")
    public String getMessage(){

//        第一种方式：直接使用RestTemplate调用固定地址参数的接口
//        RestTemplate restTemplate = new RestTemplate();
//        String response = restTemplate.getForObject("http://127.0.0.1:8080/msg", String.class);

//        第二种方式：使用LoadBalancerClient，可以实现负载均衡，使用时通过serviceId获取服务的地址，然后通过方法一的方式调用
        RestTemplate restTemplate = new RestTemplate();
        ServiceInstance serviceInstance = loadBalancerClient.choose("PRODUCT");
//        String url = serviceInstance.getHost();       获取ip地址
//        String port = serviceInstance.getPort();      获取服务端口信息
        String url = String.format("http://%s:%s", serviceInstance.getHost(), serviceInstance.getPort()) + "/msg";
        String response = restTemplate.getForObject(url, String.class) + "--" + serviceInstance.getPort();


//        第三种方式：使用一个配置类，方法上加上@LoadBalanced注解，以及@Bean注解使其成为一个Bean方便调用，类名加上注解@Component
//        然后使用第一种方式调用，但是参数只需要在ip即host出填写serviceId即可调用
//        String response = restTemplate.getForObject("http://PRODUCT/msg", String.class);

        log.info("response={}", response);
        return response;
    }


}
