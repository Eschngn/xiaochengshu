package com.chengliuxiang.xiaochengshu.auth.controller;

import com.chengliuxiang.framework.common.response.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/test")
    public Response<String>  test(){
        return Response.success("hello world");
    }
}
