package com.chengliuxiang.xiaochengshu.user.biz.service.impl;

import com.chengliuxiang.framework.biz.context.holder.LoginUserContextHolder;
import com.chengliuxiang.framework.common.enums.DeleteEnum;
import com.chengliuxiang.framework.common.enums.StatusEnum;
import com.chengliuxiang.framework.common.exception.BizException;
import com.chengliuxiang.framework.common.response.Response;
import com.chengliuxiang.framework.common.utils.JsonUtil;
import com.chengliuxiang.framework.common.utils.ParamUtil;
import com.chengliuxiang.xiaochengshu.user.biz.constant.RedisKeyConstants;
import com.chengliuxiang.xiaochengshu.user.biz.constant.RoleConstants;
import com.chengliuxiang.xiaochengshu.user.biz.domain.dataobject.RoleDO;
import com.chengliuxiang.xiaochengshu.user.biz.domain.dataobject.UserDO;
import com.chengliuxiang.xiaochengshu.user.biz.domain.dataobject.UserRoleDO;
import com.chengliuxiang.xiaochengshu.user.biz.domain.mapper.RoleDOMapper;
import com.chengliuxiang.xiaochengshu.user.biz.domain.mapper.UserDOMapper;
import com.chengliuxiang.xiaochengshu.user.biz.domain.mapper.UserRoleDOMapper;
import com.chengliuxiang.xiaochengshu.user.biz.enums.ResponseCodeEnum;
import com.chengliuxiang.xiaochengshu.user.biz.enums.SexEnum;
import com.chengliuxiang.xiaochengshu.user.biz.model.vo.UpdateUserInfoReqVO;
import com.chengliuxiang.xiaochengshu.user.biz.rpc.DistributedIdGeneratorRpcService;
import com.chengliuxiang.xiaochengshu.user.biz.rpc.OssRpcService;
import com.chengliuxiang.xiaochengshu.user.biz.service.UserService;
import com.chengliuxiang.xiaochengshu.user.dto.req.FindUserByIdReqDTO;
import com.chengliuxiang.xiaochengshu.user.dto.req.FindUserByPhoneReqDTO;
import com.chengliuxiang.xiaochengshu.user.dto.req.RegisterUserReqDTO;
import com.chengliuxiang.xiaochengshu.user.dto.req.UpdateUserPasswordReqDTO;
import com.chengliuxiang.xiaochengshu.user.dto.resp.FindUserByIdRspDTO;
import com.chengliuxiang.xiaochengshu.user.dto.resp.FindUserByPhoneRspDTO;
import com.google.common.base.Preconditions;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    @Resource
    private OssRpcService ossRpcService;
    @Resource
    private DistributedIdGeneratorRpcService distributedIdGeneratorRpcService;

    /**
     * 更新用户信息
     *
     * @param updateUserInfoReqVO
     * @return
     */
    @Override
    public Response<?> updateUserInfo(UpdateUserInfoReqVO updateUserInfoReqVO) {
        UserDO userDO = new UserDO();
        userDO.setId(LoginUserContextHolder.getUserId());
        Boolean needUpdate = false;
        // 头像
        MultipartFile avatarFile = updateUserInfoReqVO.getAvatar();
        if (Objects.nonNull(avatarFile) && avatarFile.getSize() > 0) {
            String avatarUrl = ossRpcService.uploadFile(avatarFile);
            log.info("===> 调用 oss 服务成功，上传头像，url:{}", avatarUrl);
            if (StringUtils.isBlank(avatarUrl)) {
                throw new BizException(ResponseCodeEnum.UPLOAD_AVATAR_FAIL);
            }
            userDO.setAvatar(avatarUrl);
            needUpdate = true;
        }
        // 昵称
        String nickname = updateUserInfoReqVO.getNickname();
        if (StringUtils.isNotBlank(nickname)) {
            Preconditions.checkArgument(ParamUtil.checkNickname(nickname), ResponseCodeEnum.NICK_NAME_VALID_FAIL.getErrorMessage());
            userDO.setNickname(nickname);
            needUpdate = true;
        }
        // 小橙书 ID
        String xiaochengshuId = updateUserInfoReqVO.getXiaochengshuId();
        if (StringUtils.isNotBlank(xiaochengshuId)) {
            Preconditions.checkArgument(ParamUtil.checkXiaochengshuId(xiaochengshuId), ResponseCodeEnum.XIAOCHENGSHU_ID_VALID_FAIL.getErrorMessage());
            userDO.setXiaochengshuId(xiaochengshuId);
            needUpdate = true;
        }
        // 性别
        Integer sex = updateUserInfoReqVO.getSex();
        if (Objects.nonNull(sex)) {
            Preconditions.checkArgument(SexEnum.isValid(sex), ResponseCodeEnum.SEX_VALID_FAIL.getErrorMessage());
            userDO.setSex(sex);
            needUpdate = true;
        }
        // 个人简介
        String introduction = updateUserInfoReqVO.getIntroduction();
        if (StringUtils.isNotBlank(introduction)) {
            Preconditions.checkArgument(ParamUtil.checkLength(introduction, 100), ResponseCodeEnum.INTRODUCTION_VALID_FAIL.getErrorMessage());
            userDO.setIntroduction(introduction);
            needUpdate = true;
        }
        // 生日
        LocalDateTime birthday = updateUserInfoReqVO.getBirthday();
        if (Objects.nonNull(birthday)) {
            userDO.setBirthday(birthday);
            needUpdate = true;
        }

        // 背景图
        MultipartFile backgroundImgFile = updateUserInfoReqVO.getBackgroundImg();
        if (Objects.nonNull(backgroundImgFile)) {
            String backgroundImgUrl = ossRpcService.uploadFile(backgroundImgFile);
            log.info("===> 调用 oss 服务成功，上传背景图，url:{}", backgroundImgUrl);
            if(StringUtils.isBlank(backgroundImgUrl)){
                throw new BizException(ResponseCodeEnum.UPLOAD_BACKGROUND_IMG_FAIL);
            }
            userDO.setBackgroundImg(backgroundImgUrl);
            needUpdate = true;
        }
        if (needUpdate) {
            userDO.setUpdateTime(LocalDateTime.now());
            userDOMapper.updateByPrimaryKeySelective(userDO);
        }

        return Response.success();
    }

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
        // 调用分布式 ID 生成服务生成小橙书 ID
        String xiaochengshuId = distributedIdGeneratorRpcService.getXiaochengshuId();

        // 调用分布式 ID 生成服务生成用户 ID
        String userIdStr = distributedIdGeneratorRpcService.getUserId();

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

    /**
     * 根据用户 ID 查询用户信息
     * @param findUserByIdReqDTO
     * @return
     */
    @Override
    public Response<FindUserByIdRspDTO> findById(FindUserByIdReqDTO findUserByIdReqDTO) {
        Long id = findUserByIdReqDTO.getId();
        UserDO userDO = userDOMapper.selectByPrimaryKey(id);

        if(Objects.isNull(userDO)){
            throw new BizException(ResponseCodeEnum.USER_NOT_FOUND);
        }

        FindUserByIdRspDTO findUserByIdRspDTO = FindUserByIdRspDTO.builder()
                .id(userDO.getId())
                .nickName(userDO.getNickname())
                .avatar(userDO.getAvatar())
                .build();
        return Response.success(findUserByIdRspDTO);
    }
}
