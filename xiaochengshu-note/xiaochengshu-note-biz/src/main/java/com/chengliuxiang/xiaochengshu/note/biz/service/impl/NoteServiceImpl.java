package com.chengliuxiang.xiaochengshu.note.biz.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.chengliuxiang.framework.biz.context.holder.LoginUserContextHolder;
import com.chengliuxiang.framework.common.exception.BizException;
import com.chengliuxiang.framework.common.response.Response;
import com.chengliuxiang.xiaochengshu.note.biz.domain.dataobject.NoteDO;
import com.chengliuxiang.xiaochengshu.note.biz.domain.mapper.NoteDOMapper;
import com.chengliuxiang.xiaochengshu.note.biz.domain.mapper.TopicDOMapper;
import com.chengliuxiang.xiaochengshu.note.biz.enums.NoteStatusEnum;
import com.chengliuxiang.xiaochengshu.note.biz.enums.NoteTypeEnum;
import com.chengliuxiang.xiaochengshu.note.biz.enums.NoteVisibleEnum;
import com.chengliuxiang.xiaochengshu.note.biz.enums.ResponseCodeEnum;
import com.chengliuxiang.xiaochengshu.note.biz.model.vo.PublishNoteReqVO;
import com.chengliuxiang.xiaochengshu.note.biz.rpc.DistributedIdGeneratorRpcService;
import com.chengliuxiang.xiaochengshu.note.biz.rpc.KeyValueRpcService;
import com.chengliuxiang.xiaochengshu.note.biz.service.NoteService;
import com.google.common.base.Preconditions;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class NoteServiceImpl implements NoteService {

    @Resource
    private NoteDOMapper noteDOMapper;
    @Resource
    private TopicDOMapper topicDOMapper;
    @Resource
    private KeyValueRpcService keyValueRpcService;
    @Resource
    private DistributedIdGeneratorRpcService distributedIdGeneratorRpcService;

    @Override
    public Response<?> publishNote(PublishNoteReqVO publishNoteReqVO) {
        Integer type = publishNoteReqVO.getType();
        NoteTypeEnum noteTypeEnum = NoteTypeEnum.valueOf(type);

        if (Objects.isNull(noteTypeEnum)) {
            throw new BizException(ResponseCodeEnum.NOTE_TYPE_ERROR);
        }
        String imgUris = null;
        String videoUri = null;
        switch (noteTypeEnum) {
            case IMAGE_TEXT:
                List<String> imgUriList = publishNoteReqVO.getImgUris();
                Preconditions.checkArgument(CollUtil.isNotEmpty(imgUriList), "笔记图片不能为空");
                Preconditions.checkArgument(imgUriList.size() <= 8, "笔记图片不能多于 8 张");
                // 将图片链接拼接，以逗号分隔
                imgUris = StringUtils.join(imgUriList, ",");
                break;
            case VIDEO:
                videoUri = publishNoteReqVO.getVideoUri();
                Preconditions.checkArgument(StringUtils.isNotBlank(videoUri), "笔记视频不能为空");
                break;
            default:
                break;
        }
        String snowflakeId = distributedIdGeneratorRpcService.getSnowflakeId();
        String contentUuid = null;
        String content = publishNoteReqVO.getContent();
        boolean isContentEmpty = true; // 笔记内容是否为空，默认值为 true，即空

        // 如果用户填写了笔记内容
        if (StringUtils.isNotBlank(content)) {
            isContentEmpty = false;
            contentUuid = UUID.randomUUID().toString();
            boolean isSavedSuccess = keyValueRpcService.saveNoteContent(contentUuid, content);
            if (!isSavedSuccess) {
                throw new BizException(ResponseCodeEnum.NOTE_PUBLISH_FAIL);
            }
        }
        Long topicId = publishNoteReqVO.getTopicId();
        String topicName = null;
        if(Objects.nonNull(topicId)){
            topicName = topicDOMapper.selectNameByPrimaryKey(topicId);
        }
        Long creatorId = LoginUserContextHolder.getUserId();

        NoteDO noteDO = NoteDO.builder()
                .id(Long.valueOf(snowflakeId))
                .title(publishNoteReqVO.getTitle())
                .isContentEmpty(isContentEmpty)
                .contentUuid(contentUuid)
                .creatorId(creatorId)
                .topicId(topicId)
                .topicName(topicName)
                .isTop(Boolean.FALSE)
                .type(type)
                .imgUris(imgUris)
                .videoUri(videoUri)
                .visible(NoteVisibleEnum.PUBLIC.getCode())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .status(NoteStatusEnum.NORMAL.getCode())
                .build();

        try {
            noteDOMapper.insert(noteDO);
        }catch (Exception e){
            log.error("==> 笔记存储失败", e);
            if(StringUtils.isNotBlank(contentUuid)){
                keyValueRpcService.deleteNoteContent(contentUuid);
            }
        }
        return Response.success();
    }
}
