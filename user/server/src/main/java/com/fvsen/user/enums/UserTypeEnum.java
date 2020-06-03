package com.fvsen.user.enums;

import lombok.Data;
import lombok.Getter;

/**
 * TODO
 *
 * @author Fvsen
 * @date 2020/4/27/027 7:58
 */
@Getter
public enum UserTypeEnum {

    BUYER(1, "买家"),
    SELLER(2, "卖家");

    private Integer code;
    private String message;

    UserTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

