package com.swc.orangeBook.kv.biz.service;

import com.swc.framework.common.response.Response;
import com.swc.orangeBook.kv.dto.req.AddNoteContentReqDTO;
import com.swc.orangeBook.kv.dto.req.DeleteNoteContentReqDTO;
import com.swc.orangeBook.kv.dto.req.FindNoteContentReqDTO;
import com.swc.orangeBook.kv.dto.rsp.FindNoteContentRspDTO;

/**
 * @author Wilson
 * @Description: TODO
 * @date 2024/9/17 12:26
 */
public interface NoteContentService {
    /**
     * 添加笔记内容
     *
     * @param addNoteContentReqDTO
     * @return
     */
    Response<?> addNoteContent(AddNoteContentReqDTO addNoteContentReqDTO);

    /**
     * 查询笔记内容
     *
     * @param findNoteContentReqDTO
     * @return
     */
    Response<FindNoteContentRspDTO> findNoteContent(FindNoteContentReqDTO findNoteContentReqDTO);

    /**
     * 删除笔记内容
     *
     * @param deleteNoteContentReqDTO
     * @return
     */
    Response<?> deleteNoteContent(DeleteNoteContentReqDTO deleteNoteContentReqDTO);
}
