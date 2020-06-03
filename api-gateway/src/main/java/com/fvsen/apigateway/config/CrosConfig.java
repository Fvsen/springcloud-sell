package com.fvsen.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

/**
 * TODO
 *
 * @author Fvsen
 * @date 2020/4/27/027 21:52
 */
@Configuration
public class CrosConfig {

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);       //是否允许跨域
        config.setAllowedOrigins(Arrays.asList("*"));   //http://www.a.com
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("*"));       //"GET","POST"等请求方式
        config.setMaxAge(300l);     //缓存时间，在时间段内对相同的跨域请求则不再处理
        source.registerCorsConfiguration("/*", config);
        return new CorsFilter(source);
    }
}
