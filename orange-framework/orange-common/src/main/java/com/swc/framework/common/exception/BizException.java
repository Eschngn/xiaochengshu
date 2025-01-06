package com.swc.framework.common.exception;
/**
 * @Description:
 * @author swc
 * @date 2024/9/6 10:13
 */


import lombok.Getter;
import lombok.Setter;

/**
 * @program: orangeBook
 *
 * @description: 业务异常类
 *
 * @author: Wilson
 *
 * @create: 2024-09-06 10:13
 **/
@Getter
@Setter
public class BizException extends RuntimeException{
    // 异常码
    private String errorCode;
    // 错误信息
    private String errorMessage;

    public BizException(BaseExceptionInterface baseExceptionInterface) {
        this.errorCode = baseExceptionInterface.getErrorCode();
        this.errorMessage = baseExceptionInterface.getErrorMessage();
    }
}
