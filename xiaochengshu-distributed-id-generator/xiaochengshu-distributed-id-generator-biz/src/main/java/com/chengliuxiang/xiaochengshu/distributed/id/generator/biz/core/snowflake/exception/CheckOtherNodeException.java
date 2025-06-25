package com.chengliuxiang.xiaochengshu.distributed.id.generator.biz.core.snowflake.exception;

public class CheckOtherNodeException extends RuntimeException {
    public CheckOtherNodeException(String message) {
        super(message);
    }
}
