package com.chengliuxiang.xiaochengshu.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoginTypeEnum {
    VERIFICATION_CODE(1),
    PASSWORD(2);

    private final Integer type;

    public static LoginTypeEnum valueOf(Integer type) {
        for (LoginTypeEnum loginTypeEnum : LoginTypeEnum.values()) {
            if (loginTypeEnum.getType().equals(type)) {
                return loginTypeEnum;
            }
        }
        return null;
    }
}
