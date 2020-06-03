package com.fvsen.user.repository;

import com.fvsen.user.dataobject.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * TODO
 *
 * @author Fvsen
 * @date 2020/4/27/027 7:34
 */
public interface UserInfoRepository extends JpaRepository<UserInfo, String> {
    UserInfo findByOpenid(String openid);
}
