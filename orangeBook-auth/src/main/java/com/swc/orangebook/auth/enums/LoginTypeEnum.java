package com.swc.orangebook.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @author Wilson
 * @Description:登陆类型枚举类
 * @date 2024/9/12 22:57
 */
@Getter
@AllArgsConstructor
public enum LoginTypeEnum {
    //验证码
    VERIFICATION_CODE(1),
    // 密码
    PASSWORD(2);
    private final Integer value;

    public static LoginTypeEnum valueOf(Integer code) {
        for (LoginTypeEnum loginTypeEnum : LoginTypeEnum.values()) {
            if (Objects.equals(code, loginTypeEnum.getValue())) {
                return loginTypeEnum;
            }
        }
        return null;
    }

}
