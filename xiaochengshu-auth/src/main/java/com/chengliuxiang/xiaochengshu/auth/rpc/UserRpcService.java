package com.chengliuxiang.xiaochengshu.auth.rpc;

import com.chengliuxiang.framework.common.response.Response;
import com.chengliuxiang.xiaochengshu.user.api.UserFeignApi;
import com.chengliuxiang.xiaochengshu.user.dto.req.FindUserByPhoneReqDTO;
import com.chengliuxiang.xiaochengshu.user.dto.req.RegisterUserReqDTO;
import com.chengliuxiang.xiaochengshu.user.dto.req.UpdateUserPasswordReqDTO;
import com.chengliuxiang.xiaochengshu.user.dto.resp.FindUserByPhoneRspDTO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class UserRpcService {

    @Resource
    private UserFeignApi userFeignApi;

    public Long registerUser(String phone) {
        RegisterUserReqDTO registerUserReqDTO = new RegisterUserReqDTO();
        registerUserReqDTO.setPhoneNumber(phone);
        Response<Long> response = userFeignApi.registerUser(registerUserReqDTO);
        if (!response.isSuccess()) {
            return null;
        }
        return response.getData();
    }

    public FindUserByPhoneRspDTO findUserByPhone(String phone) {
        FindUserByPhoneReqDTO findUserByPhoneReqDTO = new FindUserByPhoneReqDTO();
        findUserByPhoneReqDTO.setPhone(phone);
        Response<FindUserByPhoneRspDTO> response = userFeignApi.findByPhone(findUserByPhoneReqDTO);
        if(!response.isSuccess()){
            return null;
        }
        return response.getData();
    }

    public void updatePassword(String encodePassword) {
        UpdateUserPasswordReqDTO updateUserPasswordReqDTO = new UpdateUserPasswordReqDTO();
        updateUserPasswordReqDTO.setEncodePassword(encodePassword);
        userFeignApi.updatePassword(updateUserPasswordReqDTO);
    }
}
