package com.fvsen.user.controller;

import com.fvsen.user.VO.ResultVO;
import com.fvsen.user.constants.CookieConstant;
import com.fvsen.user.constants.RedisConstant;
import com.fvsen.user.dataobject.UserInfo;
import com.fvsen.user.enums.ResultEnum;
import com.fvsen.user.enums.UserTypeEnum;
import com.fvsen.user.service.UserService;
import com.fvsen.user.utils.CookieUtil;
import com.fvsen.user.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @author Fvsen
 * @date 2020/4/27/027 7:41
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/buyer")
    public ResultVO buyerLogin(@RequestParam("openid") String openid, HttpServletResponse response) {
        ResultVO buyer = new ResultVO();
        UserInfo userInfo = userService.findByOpenid(openid);
        //  openid和数据库内容是否匹配
        if(userInfo == null) {
            return ResultVOUtil.error(ResultEnum.LOGIN_FAIL);
        }
        /*
            登录失败
        */
        if(userInfo.getRole() != UserTypeEnum.BUYER.getCode()){
            return ResultVOUtil.error(ResultEnum.LOGIN_ERROR);
        }
        //设置cookie
        CookieUtil.setCookie(response, CookieConstant.OPENID, openid, CookieConstant.expire);

        return ResultVOUtil.success();
    }


    @GetMapping("/seller")
    public ResultVO seller(@RequestParam("openid") String openid,
                           HttpServletResponse response,
                           HttpServletRequest request) {
        ResultVO buyer = new ResultVO();
        UserInfo userInfo = userService.findByOpenid(openid);
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if(cookie!= null &&
                !StringUtils.isEmpty(stringRedisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_TEMPLATE, cookie.getValue())))) {
            return ResultVOUtil.success();
        }

        //  openid和数据库内容是否匹配
        if(userInfo == null) {
            return ResultVOUtil.error(ResultEnum.LOGIN_FAIL);
        }
        /*
            登录失败
        */
        if(userInfo.getRole() != UserTypeEnum.SELLER.getCode()){
            return ResultVOUtil.error(ResultEnum.LOGIN_ERROR);
        }
        String token = UUID.randomUUID().toString();
        Integer expire = CookieConstant.expire;
        //设置redis值
        stringRedisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_TEMPLATE, token),
                openid,
                expire,
                TimeUnit.SECONDS);
        //设置cookie
        CookieUtil.setCookie(response, CookieConstant.TOKEN, token, CookieConstant.expire);

        return ResultVOUtil.success();
    }
}
