package com.chengliuxiang.xiaochengshu.note.biz.rpc;

import com.chengliuxiang.framework.common.response.Response;
import com.chengliuxiang.xiaochengshu.user.api.UserFeignApi;
import com.chengliuxiang.xiaochengshu.user.dto.req.FindUserByIdReqDTO;
import com.chengliuxiang.xiaochengshu.user.dto.resp.FindUserByIdRspDTO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserRpcService {
    @Resource
    private UserFeignApi userFeignApi;

    public FindUserByIdRspDTO findUserById(Long userId) {
        FindUserByIdReqDTO findUserByIdReqDTO = FindUserByIdReqDTO.builder().id(userId).build();
        Response<FindUserByIdRspDTO> response = userFeignApi.findUserById(findUserByIdReqDTO);
        if (Objects.isNull(response) || !response.isSuccess()) {
            return null;
        }
        return response.getData();
    }
}
