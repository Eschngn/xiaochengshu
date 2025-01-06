package com.swc.framework.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Wilson
 * @Description: 状态
 * @date 2024/9/13 18:04
 */
@Getter
@AllArgsConstructor
public enum StatusEnum {
    //启用
    ENABLE(0),
    //禁用
    DISABLE(1);

    private final Integer value;
}
