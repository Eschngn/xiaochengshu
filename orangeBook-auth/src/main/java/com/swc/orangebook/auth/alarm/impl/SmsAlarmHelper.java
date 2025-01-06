package com.swc.orangebook.auth.alarm.impl;

import com.swc.orangebook.auth.alarm.AlarmInterface;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Wilson
 * @Description: TODO
 * @date 2024/9/15 0:06
 */
@Slf4j
public class SmsAlarmHelper implements AlarmInterface {
    /**
     * 发送警告信息
     * @param message
     * @return
     */
    @Override
    public boolean send(String message) {
        log.info("==> 【短信告警】：{}", message);

        // 业务逻辑...

        return true;
    }
}
