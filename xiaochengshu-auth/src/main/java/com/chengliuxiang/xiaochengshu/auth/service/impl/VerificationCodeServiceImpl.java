package com.chengliuxiang.xiaochengshu.auth.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.chengliuxiang.framework.common.exception.BizException;
import com.chengliuxiang.framework.common.response.Response;
import com.chengliuxiang.xiaochengshu.auth.constant.RedisKeyConstants;
import com.chengliuxiang.xiaochengshu.auth.enums.ResponseCodeEnum;
import com.chengliuxiang.xiaochengshu.auth.model.vo.verificationcode.SendVerificationCodeReqVO;
import com.chengliuxiang.xiaochengshu.auth.service.VerificationCodeService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class VerificationCodeServiceImpl implements VerificationCodeService {
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 发送短信验证码
     * @param sendVerificationCodeReqVO
     * @return
     */
    @Override
    public Response<?> send(SendVerificationCodeReqVO sendVerificationCodeReqVO) {
        String phone = sendVerificationCodeReqVO.getPhone();
        String key = RedisKeyConstants.buildVerificationCodeKey(phone);
        boolean isSent = redisTemplate.hasKey(key);
        if(isSent){
            throw new BizException(ResponseCodeEnum.VERIFICATION_CODE_SEND_FREQUENTLY);
        }
        String verificationCode = RandomUtil.randomNumbers(6);
        log.info("==> 手机号:{},已发送验证码:【{}】",phone,verificationCode);
        redisTemplate.opsForValue().set(key,verificationCode,3, TimeUnit.MINUTES);
        return Response.success();
    }
}
