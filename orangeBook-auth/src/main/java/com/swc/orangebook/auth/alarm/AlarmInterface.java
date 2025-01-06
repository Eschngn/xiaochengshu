package com.swc.orangebook.auth.alarm;

/**
 * @author Wilson
 * @Description: TODO
 * @date 2024/9/15 0:05
 */
public interface AlarmInterface {
    /**
     * 发送告警信息
     *
     * @param message
     * @return
     */
    boolean send(String message);
}
