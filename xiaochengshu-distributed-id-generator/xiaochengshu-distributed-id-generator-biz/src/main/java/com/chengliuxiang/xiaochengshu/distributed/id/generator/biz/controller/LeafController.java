package com.chengliuxiang.xiaochengshu.distributed.id.generator.biz.controller;


import com.chengliuxiang.xiaochengshu.distributed.id.generator.biz.core.common.Result;
import com.chengliuxiang.xiaochengshu.distributed.id.generator.biz.core.common.Status;
import com.chengliuxiang.xiaochengshu.distributed.id.generator.biz.exception.LeafServerException;
import com.chengliuxiang.xiaochengshu.distributed.id.generator.biz.exception.NoKeyException;
import com.chengliuxiang.xiaochengshu.distributed.id.generator.biz.service.SegmentService;
import com.chengliuxiang.xiaochengshu.distributed.id.generator.biz.service.SnowflakeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/id")
@Slf4j
public class LeafController {
    @Autowired
    private SegmentService segmentService;
    @Autowired
    private SnowflakeService snowflakeService;

    @RequestMapping(value = "/segment/get/{key}")
    public String getSegmentId(@PathVariable("key") String key) {
        return get(key, segmentService.getId(key));
    }

    @RequestMapping(value = "/snowflake/get/{key}")
    public String getSnowflakeId(@PathVariable("key") String key) {
        return get(key, snowflakeService.getId(key));
    }

    private String get(@PathVariable("key") String key, Result id) {
        Result result;
        if (key == null || key.isEmpty()) {
            throw new NoKeyException();
        }
        result = id;
        if (result.getStatus().equals(Status.EXCEPTION)) {
            throw new LeafServerException(result.toString());
        }
        return String.valueOf(result.getId());
    }
}
