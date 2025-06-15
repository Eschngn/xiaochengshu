package com.chengliuxiang.xiaochengshu.user.biz.service.impl;

import com.chengliuxiang.framework.common.response.Response;
import com.chengliuxiang.xiaochengshu.user.biz.domain.mapper.RoleDOMapper;
import com.chengliuxiang.xiaochengshu.user.biz.domain.mapper.UserDOMapper;
import com.chengliuxiang.xiaochengshu.user.biz.domain.mapper.UserRoleDOMapper;
import com.chengliuxiang.xiaochengshu.user.biz.service.UserService;
import com.chengliuxiang.xiaochengshu.user.dto.req.RegisterUserReqDTO;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;

public class UserServiceImpl implements UserService {

    @Resource
    private UserDOMapper userDOMapper;
    @Resource
    private UserRoleDOMapper userRoleDOMapper;
    @Resource
    private RoleDOMapper roleDOMapper;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 用户注册
     *
     * @param registerUserReqDTO
     * @return
     */
    @Override
    public Response<Long> register(RegisterUserReqDTO registerUserReqDTO) {
        String phone = registerUserReqDTO.getPhoneNumber();

        return null;
    }
}
