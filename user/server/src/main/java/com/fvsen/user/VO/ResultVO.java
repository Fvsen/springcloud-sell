package com.fvsen.user.VO;

import lombok.Data;

/**
 * TODO
 *
 * @author Fvsen
 * @date 2020/4/27/027 7:44
 */
@Data
public class ResultVO<T> {

    private Integer code;

    private String message;

    private T data;
}
