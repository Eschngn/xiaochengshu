package com.swc.orangebook.auth.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.google.common.base.Preconditions;
import com.swc.framework.biz.context.holder.LoginUserContextHolder;
import com.swc.framework.common.exception.BizException;
import com.swc.framework.common.response.Response;
import com.swc.orangeBook.user.dto.resp.FindUserByPhoneRspDTO;
import com.swc.orangebook.auth.constant.RedisKeyConstants;
import com.swc.orangebook.auth.enums.LoginTypeEnum;
import com.swc.orangebook.auth.enums.ResponseCodeEnum;

import com.swc.orangebook.auth.model.vo.user.UpdatePasswordReqVO;
import com.swc.orangebook.auth.model.vo.user.UserLoginReqVO;
import com.swc.orangebook.auth.rpc.UserRpcService;
import com.swc.orangebook.auth.service.AuthService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Objects;

/**
 * @author Wilson
 * @Description: TODO
 * @date 2024/9/12 23:06
 */
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private UserRpcService userRpcService;


    /**
     * 登陆与注册
     * @param userLoginReqVO
     * @return
     */
    @Override
    public Response<String> loginAndRegister(UserLoginReqVO userLoginReqVO) {
        String phone = userLoginReqVO.getPhone();
        Integer type = userLoginReqVO.getType();
        LoginTypeEnum loginTypeEnum = LoginTypeEnum.valueOf(type);
        // 登录类型错误
        if (Objects.isNull(loginTypeEnum)) {
            throw new BizException(ResponseCodeEnum.LOGIN_TYPE_ERROR);
        }
        Long userId=null;
        //判断登陆类型
        switch (loginTypeEnum){
            case VERIFICATION_CODE://验证码登陆
                String verificationCode = userLoginReqVO.getCode();
                // 校验入参验证码是否为空
                Preconditions.checkArgument(StringUtils.isNotBlank(verificationCode),"验证码不能为空");

                // 构建验证码 Redis Key
                String key = RedisKeyConstants.buildVerificationCodeKey(phone);
                // 查询存储在 Redis 中该用户的登录验证码
                String sentCode = (String) redisTemplate.opsForValue().get(key);
                if(!StringUtils.equals(verificationCode,sentCode)){
                    return Response.fail(ResponseCodeEnum.VERIFICATION_CODE_ERROR);
                }

                // RPC: 调用用户服务，注册用户
                Long userIdTmp = userRpcService.registerUser(phone);

                // 若调用用户服务，返回的用户 ID 为空，则提示登录失败
                if (Objects.isNull(userIdTmp)) {
                    throw new BizException(ResponseCodeEnum.LOGIN_FAIL);
                }

                userId = userIdTmp;
                break;
            case PASSWORD://密码登陆
                String password=userLoginReqVO.getPassword();
                // RPC: 调用用户服务，通过手机号查询用户
                FindUserByPhoneRspDTO findUserByPhoneRspDTO = userRpcService.findUserByPhone(phone);
                // 判断该手机号是否注册
                if (Objects.isNull(findUserByPhoneRspDTO)) {
                    throw new BizException(ResponseCodeEnum.USER_NOT_FOUND);
                }
                // 拿到密文密码
                String encodePassword = findUserByPhoneRspDTO.getPassword();
                // 匹配密码是否一致
                boolean isPasswordCorrect = passwordEncoder.matches(password, encodePassword);
                if(!isPasswordCorrect){
                    throw  new BizException(ResponseCodeEnum.PHONE_OR_PASSWORD_ERROR);
                }
                userId=findUserByPhoneRspDTO.getId();
                break;
            default:
                break;
        }

        // SaToken 登录用户, 入参为用户 ID
        StpUtil.login(userId);

        // 获取 Token 令牌
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();

        // 返回 Token 令牌
        return Response.success(tokenInfo.tokenValue);
    }

    /**
     * 退出登录
     *
     * @param
     * @return
     */
    @Override
    public Response<?> logout() {
        Long userId = LoginUserContextHolder.getUserId();
        // 退出登录 (指定用户 ID)
        StpUtil.logout(userId);

        return Response.success();
    }

    @Override
    public Response<?> updatePassword(UpdatePasswordReqVO updatePasswordReqVO){
        // 新密码
        String newPassword = updatePasswordReqVO.getNewPassword();
        // 密码加密
        String encodePassword = passwordEncoder.encode(newPassword);


        // RPC: 调用用户服务：更新密码
        userRpcService.updatePassword(encodePassword);

        return Response.success();
    }
}
