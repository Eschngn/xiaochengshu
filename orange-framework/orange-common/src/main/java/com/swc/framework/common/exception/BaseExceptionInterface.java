package com.swc.framework.common.exception;

import lombok.Getter;

/**
 * @author swc
 * @Description:基础异常接口
 * @date 2024/9/6 10:11
 */
public interface BaseExceptionInterface {
    // 获取异常码
    String getErrorCode();

    // 获取异常信息
    String getErrorMessage();
}
