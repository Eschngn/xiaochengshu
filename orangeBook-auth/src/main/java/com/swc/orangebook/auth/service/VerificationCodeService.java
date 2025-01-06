package com.swc.orangebook.auth.service;

import com.swc.framework.common.response.Response;
import com.swc.orangebook.auth.model.vo.verificationcode.SendVerificationCodeReqVO;

/**
 * @author swc
 * @Description:
 * @date 2024/9/8 11:33
 */
public interface VerificationCodeService {
    /**
     * 发送短信验证码
     * @param sendVerificationCodeReqVO
     * @return
     */
    Response<?> send(SendVerificationCodeReqVO sendVerificationCodeReqVO);
}
