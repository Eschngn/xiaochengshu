package com.chengliuxiang.xiaochengshu.auth.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.chengliuxiang.framework.common.exception.BizException;
import com.chengliuxiang.framework.common.response.Response;
import com.chengliuxiang.xiaochengshu.auth.constant.RedisKeyConstants;
import com.chengliuxiang.xiaochengshu.auth.enums.LoginTypeEnum;
import com.chengliuxiang.xiaochengshu.auth.enums.ResponseCodeEnum;
import com.chengliuxiang.xiaochengshu.auth.model.vo.user.UserLoginReqVO;
import com.chengliuxiang.xiaochengshu.auth.rpc.UserRpcService;
import com.chengliuxiang.xiaochengshu.auth.service.AuthService;
import com.chengliuxiang.xiaochengshu.user.dto.resp.FindUserByPhoneRspDTO;
import com.google.common.base.Preconditions;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthServiceImpl implements AuthService {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private UserRpcService userRpcService;

    @Resource
    private PasswordEncoder passwordEncoder;

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
            case VERIFICATION_CODE: // 验证码登陆
                String verificationCode = userLoginReqVO.getCode();
                Preconditions.checkArgument(StringUtils.isNotBlank(verificationCode), "验证码不能为空");
                String key = RedisKeyConstants.buildVerificationCodeKey(phone);
                String sentCode = (String) redisTemplate.opsForValue().get(key);
                if (!StringUtils.equals(sentCode, verificationCode)) {
                    return Response.fail(ResponseCodeEnum.VERIFICATION_CODE_ERROR);
                }
                // 调用用户服务，注册用户
                Long userIdTmp = userRpcService.registerUser(phone);
                if (Objects.isNull(userIdTmp)) {
                    throw new BizException(ResponseCodeEnum.LOGIN_FAIL);
                }
                userId = userIdTmp;
                break;
            case PASSWORD: // 密码登陆
                String password = userLoginReqVO.getPassword();
                FindUserByPhoneRspDTO findUserByPhoneRspDTO = userRpcService.findUserByPhone(phone);
                if(Objects.isNull(findUserByPhoneRspDTO)){
                    throw new BizException(ResponseCodeEnum.USER_NOT_FOUND);
                }
                // 拿到密文密码
                String encodePassword = findUserByPhoneRspDTO.getPassword();
                boolean isPasswordCorrect = passwordEncoder.matches(encodePassword, password);
                if(!isPasswordCorrect){
                    throw new BizException(ResponseCodeEnum.PHONE_OR_PASSWORD_ERROR);
                }
                userId= findUserByPhoneRspDTO.getId();
                break;
            default:
                break;
        }

        // SaToken 登陆用户，入参为用户 ID
        StpUtil.login(userId);

        // 获取 Token 令牌
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();

        // 返回 Token 令牌
        return Response.success(tokenInfo.tokenValue);
    }
}
