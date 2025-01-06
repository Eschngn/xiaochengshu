package com.swc.orangeBook.kv.api;

import com.swc.framework.common.response.Response;
import com.swc.orangeBook.kv.constant.ApiConstants;
import com.swc.orangeBook.kv.dto.req.AddNoteContentReqDTO;
import com.swc.orangeBook.kv.dto.req.DeleteNoteContentReqDTO;
import com.swc.orangeBook.kv.dto.req.FindNoteContentReqDTO;
import com.swc.orangeBook.kv.dto.rsp.FindNoteContentRspDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Wilson
 * @Description: K-V 键值存储 Feign 接口
 * @date 2024/9/17 12:35
 */
@FeignClient(name = ApiConstants.SERVICE_NAME)
public interface KeyValueFeignApi {
    String PREFIX = "/kv";

    @PostMapping(value = PREFIX + "/note/content/add")
    Response<?> addNoteContent(@RequestBody AddNoteContentReqDTO addNoteContentReqDTO);

    @PostMapping(value = PREFIX + "/note/content/find")
    Response<FindNoteContentRspDTO> findNoteContent(@RequestBody FindNoteContentReqDTO findNoteContentReqDTO);

    @PostMapping(value = PREFIX + "/note/content/delete")
    Response<?> deleteNoteContent(@RequestBody DeleteNoteContentReqDTO deleteNoteContentReqDTO);
}
