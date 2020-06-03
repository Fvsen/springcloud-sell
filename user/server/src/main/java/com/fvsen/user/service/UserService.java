package com.fvsen.user.service;

import com.fvsen.user.dataobject.UserInfo;

/**
 * TODO
 *
 * @author Fvsen
 * @date 2020/4/27/027 7:37
 */
public interface UserService {
    UserInfo findByOpenid(String openId);

}
