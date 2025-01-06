package com.swc.framework.common.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author Wilson
 * @Description: 日期工具类
 * @date 2024/9/19 16:36
 */
public class DateUtil {
    /**
     * LocalDateTime 转时间戳
     *
     * @param localDateTime
     * @return
     */
    public static long localDateTime2Timestamp(LocalDateTime localDateTime) {
        return localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
    }
}
