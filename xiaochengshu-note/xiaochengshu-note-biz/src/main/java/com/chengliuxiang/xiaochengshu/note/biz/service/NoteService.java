package com.chengliuxiang.xiaochengshu.note.biz.service;

import com.chengliuxiang.framework.common.response.Response;
import com.chengliuxiang.xiaochengshu.note.biz.model.vo.DeleteNoteReqVO;
import com.chengliuxiang.xiaochengshu.note.biz.model.vo.FindNoteDetailReqVO;
import com.chengliuxiang.xiaochengshu.note.biz.model.vo.FindNoteDetailRspVO;
import com.chengliuxiang.xiaochengshu.note.biz.model.vo.PublishNoteReqVO;

public interface NoteService {

    public Response<?> publishNote(PublishNoteReqVO publishNoteReqVO);

    public Response<?> deleteNote(DeleteNoteReqVO deleteNoteReqVO);

    public Response<FindNoteDetailRspVO>  findNoteDetail(FindNoteDetailReqVO findNoteDetailReqVO);
}
