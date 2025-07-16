package com.chengliuxiang.xiaochengshu.note.biz.rpc;

import com.chengliuxiang.framework.common.response.Response;
import com.chengliuxiang.xiaochengshu.kv.api.KeyValueFeignApi;
import com.chengliuxiang.xiaochengshu.kv.dto.req.AddNoteContentReqDTO;
import com.chengliuxiang.xiaochengshu.kv.dto.req.DeleteNoteContentReqDTO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class KeyValueRpcService {

    @Resource
    private KeyValueFeignApi keyValueFeignApi;

    public boolean saveNoteContent(String uuid, String content) {
        AddNoteContentReqDTO addNoteContentReqDTO = AddNoteContentReqDTO.builder()
                .uuid(uuid)
                .content(content)
                .build();
        Response<?> response = keyValueFeignApi.addNoteContent(addNoteContentReqDTO);
        if (Objects.isNull(response) || !response.isSuccess()) {
            return false;
        }
        return true;
    }

    public boolean deleteNoteContent(String uuid) {
        DeleteNoteContentReqDTO deleteNoteContentReqDTO = DeleteNoteContentReqDTO.builder()
                .uuid(uuid)
                .build();
        Response<?> response = keyValueFeignApi.deleteNoteContent(deleteNoteContentReqDTO);
        if (Objects.isNull(response) || !response.isSuccess()) {
            return false;
        }
        return true;
    }

}
