package com.chengliuxiang.xiaochengshu.auth.service;

import com.chengliuxiang.framework.common.response.Response;
import com.chengliuxiang.xiaochengshu.auth.model.vo.user.UserLoginReqVO;

public interface AuthService {

    Response<String> loginAndRegister(UserLoginReqVO userLoginReqVO);

    Response<?> logout();
}
