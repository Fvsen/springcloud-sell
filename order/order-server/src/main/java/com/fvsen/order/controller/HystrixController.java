package com.fvsen.order.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.utils.FallbackMethod;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * TODO
 *
 * @author Fvsen
 * @date 2020/4/28/028 7:13
 */
@RestController
@DefaultProperties(defaultFallback = "defaultFallback")
public class HystrixController {


    @HystrixCommand(commandProperties = {
        @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),      //设置开启熔断
        @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),     //在一个bucket时间窗口内，此属性用于
                                                    // 设置使熔断判断逻辑开始工作的最小请求数。（而不是最小失败请求数）
                                                    //例如，如果值是20，那么如果在滚动窗口中只接收到19个请求(比如一个10秒的窗口)，
                                                     // 即便所有19个请求都失败了，熔断判断逻辑也不会被触发。
        @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),       //休眠时间窗
        @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")            //设定错误百分比，默认值50%，
                                        // 例如一段时间（10s）内有100个请求，其中有55个超时或者异常返回了，那么这段时间内的错误百分比是55%，
                                        // 大于了默认值50%，这种情况下触发熔断器-打开。
    })
//    @HystrixCommand(commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
//            //配置超时时间，主要用于访问如支付之类外部接口等耗时较长的场景
//    })
    //如果缺省fallbackMethod属性设置，则使用@DefaultProperties(defaultFallback = "defaultFallback")的配置
//    @HystrixCommand(fallbackMethod = "fallback")
    @GetMapping("/getProductInfoList")
    public String getMsg(@RequestParam("number") Integer number) {
        if(number % 2 == 0) {
            return "success";
        }else {
            throw new RuntimeException("异常");
        }
//        RestTemplate restTemplate = new RestTemplate();
//        return restTemplate.postForObject("http://localhost:8090/product/listForOrder",
//                Arrays.asList("157875196366160022"),
//                String.class);
    }

    public String fallback() {
        return "服务正忙，请稍后再试！";
    }

    public String defaultFallback() {
        return "默认消息：服务正忙，请稍后再试！";
    }


}
