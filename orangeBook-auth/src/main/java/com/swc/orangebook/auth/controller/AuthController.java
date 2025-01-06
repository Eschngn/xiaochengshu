package com.swc.orangebook.auth.controller;

import com.swc.framework.biz.operationlog.aspect.ApiOperationLog;
import com.swc.framework.common.response.Response;
import com.swc.orangebook.auth.model.vo.user.UpdatePasswordReqVO;
import com.swc.orangebook.auth.model.vo.user.UserLoginReqVO;
import com.swc.orangebook.auth.service.AuthService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Wilson
 * @Description: TODO
 * @date 2024/9/13 18:02
 */
@RestController
@Slf4j
public class AuthController {
    @Resource
    private AuthService userService;

    @PostMapping("/login")
    @ApiOperationLog(description = "用户登录/注册")
    public Response<String> loginAndRegister(@Validated @RequestBody UserLoginReqVO userLoginReqVO) {
        return userService.loginAndRegister(userLoginReqVO);
    }

    @PostMapping("/logout")
    @ApiOperationLog(description = "账号登出")
    public Response<?> logout() {
        return userService.logout();
    }

    @PostMapping("/password/update")
    @ApiOperationLog(description = "修改密码")
    public Response<?> updatePassword(@Validated @RequestBody UpdatePasswordReqVO updatePasswordReqVO) {
        return userService.updatePassword(updatePasswordReqVO);
    }
}
