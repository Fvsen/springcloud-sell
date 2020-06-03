package com.fvsen.user.service.impl;

import com.fvsen.user.dataobject.UserInfo;
import com.fvsen.user.repository.UserInfoRepository;
import com.fvsen.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @author Fvsen
 * @date 2020/4/27/027 7:38
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    /**
     * 根据openId查询用户信息
     * @param openid
     * @return
     */
    @Override
    public UserInfo findByOpenid(String openid) {
        UserInfo userInfo = userInfoRepository.findByOpenid(openid);
        return userInfo;
    }
}
