package com.fvsen.user.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 *
 * @author Fvsen
 * @date 2020/4/27/027 7:30
 */
@Data
@Entity
public class UserInfo {

    @Id
    private String id;
    private String username;
    private String password;
    private String openid;
    private Integer role;
    private Date createTime;
    private Date updateTime;
}
