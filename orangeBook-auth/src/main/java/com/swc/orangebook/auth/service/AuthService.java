package com.swc.orangebook.auth.service;

import com.swc.framework.common.response.Response;
import com.swc.orangebook.auth.model.vo.user.UpdatePasswordReqVO;
import com.swc.orangebook.auth.model.vo.user.UserLoginReqVO;

/**
 * @author Wilson
 * @Description:
 * @date 2024/9/12 23:05
 */
public interface AuthService {
    /**
     * 登录与注册
     * @param userLoginReqVO
     * @return
     */
    Response<String> loginAndRegister(UserLoginReqVO userLoginReqVO);

    /**
     * 退出登录
     * @return
     */
    Response<?> logout();

    /**
     * 修改密码
     * @param updatePasswordReqVO
     * @return
     */
    Response<?> updatePassword(UpdatePasswordReqVO updatePasswordReqVO);
}
