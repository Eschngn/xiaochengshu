package com.chengliuxiang.kv.biz.service;

import com.chengliuxiang.framework.common.response.Response;
import com.chengliuxiang.xiaochengshu.kv.dto.req.AddNoteContentReqDTO;
import com.chengliuxiang.xiaochengshu.kv.dto.req.DeleteNoteContentReqDTO;
import com.chengliuxiang.xiaochengshu.kv.dto.req.FindNoteContentReqDTO;
import com.chengliuxiang.xiaochengshu.kv.dto.rsp.FindNoteContentRspDTO;

public interface NoteContentService {

    /**
     * 添加笔记内容
     * @param addNoteContentReqDTO
     * @return
     */
    public Response<?> addNoteContent(AddNoteContentReqDTO addNoteContentReqDTO);


    /**
     * 查询笔记内容
     * @param findNoteContentReqDTO
     * @return
     */
    public Response<FindNoteContentRspDTO> findNoteContent(FindNoteContentReqDTO findNoteContentReqDTO);

    /**
     * 删除笔记内容
     * @param deleteNoteContentReqDTO
     * @return
     */
    public Response<?>  deleteNoteContent(DeleteNoteContentReqDTO deleteNoteContentReqDTO);
}
