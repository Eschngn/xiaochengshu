package com.swc.orangebook.auth.controller;

import com.swc.framework.biz.operationlog.aspect.ApiOperationLog;
import com.swc.framework.common.response.Response;
import com.swc.orangebook.auth.model.vo.verificationcode.SendVerificationCodeReqVO;
import com.swc.orangebook.auth.service.VerificationCodeService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Wilson
 * @Description:
 * @date 2024/9/8 12:27
 */
@RestController
@Slf4j
public class VerificationCodeController {
    @Resource
    private VerificationCodeService verificationCodeService;

    @PostMapping("/verification/code/send")
    @ApiOperationLog(description = "发送短信验证码")
    public Response<?> send(@Validated @RequestBody SendVerificationCodeReqVO sendVerificationCodeReqVO) {
        return verificationCodeService.send(sendVerificationCodeReqVO);
    }
}
