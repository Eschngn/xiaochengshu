package com.swc.framework.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @author Wilson
 * @Description:
 * @date 2024/9/11 23:10
 */
public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber,String> {
    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        // 校验逻辑：正则表达式判断手机号是否为 11 位数字
        return phoneNumber != null && phoneNumber.matches("\\d{11}");
    }

    @Override
    public void initialize(PhoneNumber constraintAnnotation) {
        //这里进行一些初始化操作
    }
}
