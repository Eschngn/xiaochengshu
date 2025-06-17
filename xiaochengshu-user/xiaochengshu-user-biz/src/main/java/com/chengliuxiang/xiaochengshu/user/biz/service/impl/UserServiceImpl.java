package com.chengliuxiang.xiaochengshu.user.biz.service.impl;

import com.chengliuxiang.framework.biz.context.holder.LoginUserContextHolder;
import com.chengliuxiang.framework.common.enums.DeleteEnum;
import com.chengliuxiang.framework.common.enums.StatusEnum;
import com.chengliuxiang.framework.common.exception.BizException;
import com.chengliuxiang.framework.common.response.Response;
import com.chengliuxiang.framework.common.utils.JsonUtil;
import com.chengliuxiang.xiaochengshu.user.biz.constant.RedisKeyConstants;
import com.chengliuxiang.xiaochengshu.user.biz.constant.RoleConstants;
import com.chengliuxiang.xiaochengshu.user.biz.domain.dataobject.RoleDO;
import com.chengliuxiang.xiaochengshu.user.biz.domain.dataobject.UserDO;
import com.chengliuxiang.xiaochengshu.user.biz.domain.dataobject.UserRoleDO;
import com.chengliuxiang.xiaochengshu.user.biz.domain.mapper.RoleDOMapper;
import com.chengliuxiang.xiaochengshu.user.biz.domain.mapper.UserDOMapper;
import com.chengliuxiang.xiaochengshu.user.biz.domain.mapper.UserRoleDOMapper;
import com.chengliuxiang.xiaochengshu.user.biz.enums.ResponseCodeEnum;
import com.chengliuxiang.xiaochengshu.user.biz.service.UserService;
import com.chengliuxiang.xiaochengshu.user.dto.req.FindUserByPhoneReqDTO;
import com.chengliuxiang.xiaochengshu.user.dto.req.RegisterUserReqDTO;
import com.chengliuxiang.xiaochengshu.user.dto.req.UpdateUserPasswordReqDTO;
import com.chengliuxiang.xiaochengshu.user.dto.resp.FindUserByPhoneRspDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
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

        UserDO userDO1 = userDOMapper.selectByPhone(phone);
        log.info("===> 用户是否注册，phone:{},userDO:{}", phone, userDO1);

        // 若已注册，则直接返回用户 ID
        if (Objects.nonNull(userDO1)) {
            return Response.success(userDO1.getId());
        }

        // 否则注册新用户
        // TODO 调用分布式 ID 生成服务生成小橙书 ID
        String xiaochengshuId = "12315343";

        // TODO 调用分布式 ID 生成服务生成用户 ID
        String userIdStr = "4231123";

        Long userId = Long.valueOf(userIdStr);
        UserDO userDO = UserDO.builder()
                .id(userId)
                .phone(phone)
                .xiaochengshuId(xiaochengshuId)
                .nickname("小橙薯" + xiaochengshuId)
                .status(StatusEnum.ENABLE.getValue())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .isDeleted(DeleteEnum.NO.getValue())
                .build();
        userDOMapper.insert(userDO);

        UserRoleDO userRoleDO = UserRoleDO.builder()
                .userId(userId)
                .roleId(RoleConstants.COMMON_USER_ROLE_ID)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .isDeleted(DeleteEnum.NO.getValue())
                .build();

        RoleDO roleDO = roleDOMapper.selectByPrimaryKey(RoleConstants.COMMON_USER_ROLE_ID);

        userRoleDOMapper.insert(userRoleDO);

        List<String> roles = new ArrayList<>(1);
        roles.add(roleDO.getRoleKey());
        String userRolesKey = RedisKeyConstants.buildUserRolesKey(userId);
        // 将该用户 ID 和角色数据存入 Redis 中
        redisTemplate.opsForValue().set(userRolesKey, JsonUtil.toJsonString(roles));
        return Response.success(userId);
    }

    /**
     * 根据手机号查询用户信息
     *
     * @param findUserByPhoneReqDTO
     * @return
     */
    @Override
    public Response<FindUserByPhoneRspDTO> findByPhone(FindUserByPhoneReqDTO findUserByPhoneReqDTO) {
        String phone = findUserByPhoneReqDTO.getPhone();
        UserDO userDO = userDOMapper.selectByPhone(phone);
        if (Objects.isNull(userDO)) {
            throw new BizException(ResponseCodeEnum.USER_NOT_FOUND);
        }
        FindUserByPhoneRspDTO findUserByPhoneRspDTO = FindUserByPhoneRspDTO.builder()
                .id(userDO.getId())
                .password(userDO.getPassword())
                .build();
        return Response.success(findUserByPhoneRspDTO);
    }

    /**
     * 更新密码
     *
     * @param updateUserPasswordReqDTO
     * @return
     */
    @Override
    public Response<?> updatePassword(UpdateUserPasswordReqDTO updateUserPasswordReqDTO) {
        Long userId = LoginUserContextHolder.getUserId();
        UserDO userDO = UserDO.builder()
                .id(userId)
                .password(updateUserPasswordReqDTO.getEncodePassword())
                .build();
        userDOMapper.updateByPrimaryKeySelective(userDO);
        return Response.success();
    }
}
