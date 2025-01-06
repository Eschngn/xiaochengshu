package com.swc.orangebook.auth.service.impl;
/**
 * @Description:
 * @author swc
 * @date 2024/9/8 11:34
 */

import cn.hutool.core.util.RandomUtil;
import com.swc.framework.common.exception.BizException;
import com.swc.framework.common.response.Response;
import com.swc.orangebook.auth.constant.RedisKeyConstants;
import com.swc.orangebook.auth.enums.ResponseCodeEnum;
import com.swc.orangebook.auth.model.vo.verificationcode.SendVerificationCodeReqVO;
import com.swc.orangebook.auth.service.VerificationCodeService;
import com.swc.orangebook.auth.sms.AliyunSmsHelper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @program: orangeBook
 *
 * @description: 验证码确认业务实现类
 *
 * @author: Wilson
 *
 * @create: 2024-09-08 11:34
 **/
@Service
@Slf4j
public class VerificationCodeServiceImpl implements VerificationCodeService {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource(name = "taskExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Resource
    private AliyunSmsHelper aliyunSmsHelper;

    /**
     * 发送短信验证码
     * @param sendVerificationCodeReqVO
     * @return
     */
    @Override
    public Response<?> send(SendVerificationCodeReqVO sendVerificationCodeReqVO) {
        String phone = sendVerificationCodeReqVO.getPhone();
        String key = RedisKeyConstants.buildVerificationCodeKey(phone);
        Boolean isSent = redisTemplate.hasKey(key);
        if(isSent){
            throw  new BizException(ResponseCodeEnum.VERIFICATION_CODE_SEND_FREQUENTLY);
        }
        String verificationCode = RandomUtil.randomNumbers(6);

        //调用第三方短信发送服务
        threadPoolTaskExecutor.submit(()->{
            String signName="阿里云短信测试";
            String templateCode="SMS_154950909";
            String templateParam=String.format("{\"code\":\"%s\"}",verificationCode);
            aliyunSmsHelper.sendMessage(signName,templateCode,phone,templateParam);
        });
        log.info("==> 手机号: {}, 已发送验证码：【{}】", phone, verificationCode);
        // 存储验证码到 redis, 并设置过期时间为 3 分钟
        redisTemplate.opsForValue().set(key, verificationCode, 3, TimeUnit.MINUTES);
        return Response.success();
    }
}
