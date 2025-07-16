package com.chengliuxiang.xiaochengshu.note.biz.service;

import com.chengliuxiang.framework.common.response.Response;
import com.chengliuxiang.xiaochengshu.note.biz.model.vo.PublishNoteReqVO;

public interface NoteService {

    public Response<?> publishNote(PublishNoteReqVO publishNoteReqVO);
}
