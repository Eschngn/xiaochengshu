package com.chengliuxiang.xiaochengshu.auth.service.impl;

import com.chengliuxiang.framework.common.exception.BizException;
import com.chengliuxiang.framework.common.response.Response;
import com.chengliuxiang.xiaochengshu.auth.constant.RedisKeyConstants;
import com.chengliuxiang.xiaochengshu.auth.enums.LoginTypeEnum;
import com.chengliuxiang.xiaochengshu.auth.enums.ResponseCodeEnum;
import com.chengliuxiang.xiaochengshu.auth.model.vo.user.UserLoginReqVO;
import com.chengliuxiang.xiaochengshu.auth.service.AuthService;
import com.google.common.base.Preconditions;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthServiceImpl implements AuthService {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Response<String> loginAndRegister(UserLoginReqVO userLoginReqVO) {
        String phone = userLoginReqVO.getPhone();
        Integer type = userLoginReqVO.getType();
        LoginTypeEnum loginTypeEnum = LoginTypeEnum.valueOf(type);
        if (Objects.isNull(loginTypeEnum)) {
            throw new BizException(ResponseCodeEnum.LOGIN_TYPE_ERROR);
        }
        Long userId = null;
        switch (loginTypeEnum) {
            case VERIFICATION_CODE:
                String verificationCode = userLoginReqVO.getCode();
                Preconditions.checkArgument(StringUtils.isNotBlank(verificationCode), "验证码不能为空");
                String key = RedisKeyConstants.buildVerificationCodeKey(phone);
                String sentCode = (String) redisTemplate.opsForValue().get(key);
                if(!StringUtils.equals(sentCode,verificationCode)){
                    return Response.fail(ResponseCodeEnum.VERIFICATION_CODE_ERROR);
                }
                // TODO: 调用用户服务查询用户是否已注册
        }

        return null;
    }
}
