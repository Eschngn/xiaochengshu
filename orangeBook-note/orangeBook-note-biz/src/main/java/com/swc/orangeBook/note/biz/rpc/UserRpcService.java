package com.swc.orangeBook.note.biz.rpc;

import com.swc.framework.common.response.Response;
import com.swc.orangeBook.user.api.UserFeignApi;
import com.swc.orangeBook.user.dto.req.FindUserByIdReqDTO;
import com.swc.orangeBook.user.dto.resp.FindUserByIdRspDTO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author Wilson
 * @Description: 用户服务
 * @date 2024/9/18 15:00
 */
@Component
public class UserRpcService {
    @Resource
    private UserFeignApi userFeignApi;
    public FindUserByIdRspDTO findById(Long userId){
        FindUserByIdReqDTO findUserByIdReqDTO = new FindUserByIdReqDTO();
        findUserByIdReqDTO.setId(userId);
        Response<FindUserByIdRspDTO> response = userFeignApi.findById(findUserByIdReqDTO);
        if (Objects.isNull(response) || !response.isSuccess()) {
            return null;
        }

        return response.getData();
    }
}
