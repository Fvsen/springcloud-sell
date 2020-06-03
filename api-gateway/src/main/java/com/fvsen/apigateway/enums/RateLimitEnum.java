package com.fvsen.apigateway.enums;

/**
 * TODO
 *
 * @author Fvsen
 * @date 2020/4/26/026 22:14
 */
public enum RateLimitEnum {
    RATE_LIMIT_ERROT(0, "获取资源出错");

    private Integer code;
    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    RateLimitEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
