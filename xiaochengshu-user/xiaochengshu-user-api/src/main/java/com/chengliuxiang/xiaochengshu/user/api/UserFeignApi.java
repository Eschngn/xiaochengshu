package com.chengliuxiang.xiaochengshu.user.api;

import com.chengliuxiang.framework.common.response.Response;
import com.chengliuxiang.xiaochengshu.user.constant.ApiConstants;
import com.chengliuxiang.xiaochengshu.user.dto.req.FindUserByIdReqDTO;
import com.chengliuxiang.xiaochengshu.user.dto.req.FindUserByPhoneReqDTO;
import com.chengliuxiang.xiaochengshu.user.dto.req.RegisterUserReqDTO;
import com.chengliuxiang.xiaochengshu.user.dto.req.UpdateUserPasswordReqDTO;
import com.chengliuxiang.xiaochengshu.user.dto.resp.FindUserByIdRspDTO;
import com.chengliuxiang.xiaochengshu.user.dto.resp.FindUserByPhoneRspDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = ApiConstants.SERVICE_NAME)
public interface UserFeignApi {
    String PREFIX = "/user";

    @PostMapping(PREFIX + "/register")
    Response<Long> registerUser(@RequestBody RegisterUserReqDTO registerUserReqDTO);

    @PostMapping(PREFIX + "/findByPhone")
    Response<FindUserByPhoneRspDTO> findByPhone(@RequestBody FindUserByPhoneReqDTO findUserByPhoneReqDTO);

    @PostMapping(PREFIX + "/password/update")
    Response<?> updatePassword(@RequestBody UpdateUserPasswordReqDTO updateUserPasswordReqDTO);

    @PostMapping(PREFIX + "/findById")
    Response<FindUserByIdRspDTO> findUserById(@RequestBody FindUserByIdReqDTO findUserByIdReqDTO);
}
