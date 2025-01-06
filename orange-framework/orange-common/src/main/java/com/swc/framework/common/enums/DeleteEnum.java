package com.swc.framework.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Wilson
 * @Description: 逻辑删除
 * @date 2024/9/13 18:04
 */
@Getter
@AllArgsConstructor
public enum DeleteEnum {
    YES(true),
    No(false);

    private final Boolean value;
}
