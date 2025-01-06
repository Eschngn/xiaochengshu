package com.swc.framework.biz.context.holder;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.swc.framework.common.constant.GlobalConstants;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Wilson
 * @Description: 登录用户上下文
 * @date 2024/9/15 23:05
 */
public class LoginUserContextHolder {
    private static final ThreadLocal<Map<String,Object>> LOGIN_USER_CONTEXT_THREAD_LOCAL= TransmittableThreadLocal.withInitial(HashMap::new);

    /**
     * 设置用户 ID
     *
     * @param value
     */
    public static void setUserId(Object value){
        LOGIN_USER_CONTEXT_THREAD_LOCAL.get().put(GlobalConstants.USER_ID,value);
    }

    /**
     * 获取用户 ID
     *
     * @return
     */
    public static Long getUserId(){
        Object value = LOGIN_USER_CONTEXT_THREAD_LOCAL.get().get(GlobalConstants.USER_ID);
        if(Objects.isNull(value)){
            return null;
        }
        return Long.valueOf(value.toString());
    }

    /**
     * 删除 ThreadLocal
     */
    public static void remove() {
        LOGIN_USER_CONTEXT_THREAD_LOCAL.remove();
    }
}
