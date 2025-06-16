package com.chengliuxiang.xiaochengshu.user.biz.controller;

import com.chengliuxiang.framework.biz.operationlog.aspect.ApiOperationLog;
import com.chengliuxiang.framework.common.response.Response;
import com.chengliuxiang.xiaochengshu.user.biz.service.UserService;
import com.chengliuxiang.xiaochengshu.user.dto.req.RegisterUserReqDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    @ApiOperationLog(description = "用户注册")
    public Response<Long> register(@RequestBody @Validated RegisterUserReqDTO registerUserReqDTO) {
        return userService.register(registerUserReqDTO);
    }

}
