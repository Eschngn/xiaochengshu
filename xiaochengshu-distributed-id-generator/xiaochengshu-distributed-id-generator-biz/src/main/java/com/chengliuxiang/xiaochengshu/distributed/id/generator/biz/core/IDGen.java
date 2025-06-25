package com.chengliuxiang.xiaochengshu.distributed.id.generator.biz.core;


import com.chengliuxiang.xiaochengshu.distributed.id.generator.biz.core.common.Result;

public interface IDGen {
    Result get(String key);
    boolean init();
}
