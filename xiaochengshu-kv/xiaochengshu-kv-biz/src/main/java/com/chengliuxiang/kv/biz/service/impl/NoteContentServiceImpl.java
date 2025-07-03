package com.chengliuxiang.kv.biz.service.impl;

import com.chengliuxiang.framework.common.exception.BizException;
import com.chengliuxiang.framework.common.response.Response;
import com.chengliuxiang.kv.biz.domain.dataobject.NoteContentDO;
import com.chengliuxiang.kv.biz.domain.repository.NoteContentRepository;
import com.chengliuxiang.kv.biz.enums.ResponseCodeEnum;
import com.chengliuxiang.kv.biz.service.NoteContentService;
import com.chengliuxiang.xiaochengshu.kv.dto.req.AddNoteContentReqDTO;
import com.chengliuxiang.xiaochengshu.kv.dto.req.DeleteNoteContentReqDTO;
import com.chengliuxiang.xiaochengshu.kv.dto.req.FindNoteContentReqDTO;
import com.chengliuxiang.xiaochengshu.kv.dto.rsp.FindNoteContentRspDTO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class NoteContentServiceImpl implements NoteContentService {
    @Resource
    private NoteContentRepository noteContentRepository;


    @Override
    public Response<?> addNoteContent(AddNoteContentReqDTO addNoteContentReqDTO) {
        // 笔记内容 UUID
        String uuid = addNoteContentReqDTO.getUuid();

        NoteContentDO noteContent = NoteContentDO.builder()
                .id(UUID.fromString(uuid))
                .content(addNoteContentReqDTO.getContent())
                .build();
        // 保存笔记内容到 Cassandra
        noteContentRepository.save(noteContent);
        return Response.success();
    }

    @Override
    public Response<FindNoteContentRspDTO> findNoteContent(FindNoteContentReqDTO findNoteContentReqDTO) {
        // 笔记内容 UUID
        String uuid = findNoteContentReqDTO.getUuid();
        Optional<NoteContentDO> optional = noteContentRepository.findById(UUID.fromString(uuid));

        if(optional.isPresent()){
            throw new BizException(ResponseCodeEnum.NOTE_CONTENT_NOT_FOUND);
        }
        NoteContentDO noteContentDO = optional.get();
        FindNoteContentRspDTO findNoteContentRspDTO = FindNoteContentRspDTO.builder()
                .uuid(noteContentDO.getId())
                .content(noteContentDO.getContent())
                .build();
        return Response.success(findNoteContentRspDTO);
    }

    @Override
    public Response<?> deleteNoteContent(DeleteNoteContentReqDTO deleteNoteContentReqDTO) {
        // 笔记内容 UUID
        String uuid = deleteNoteContentReqDTO.getUuid();

        noteContentRepository.deleteById(UUID.fromString(uuid));
        return Response.success();
    }
}
