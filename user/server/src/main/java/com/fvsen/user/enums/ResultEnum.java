package com.fvsen.user.enums;

import lombok.Getter;

/**
 * TODO
 *
 * @author Fvsen
 * @date 2020/4/27/027 8:23
 */
@Getter
public enum ResultEnum {
    LOGIN_FAIL(1, "登录失败"),
    LOGIN_ERROR(2, "角色权限有误"),
    ;

    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
