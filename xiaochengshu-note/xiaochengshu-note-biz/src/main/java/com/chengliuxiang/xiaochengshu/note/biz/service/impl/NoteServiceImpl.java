package com.chengliuxiang.xiaochengshu.note.biz.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import com.chengliuxiang.framework.biz.context.holder.LoginUserContextHolder;
import com.chengliuxiang.framework.common.exception.BizException;
import com.chengliuxiang.framework.common.response.Response;
import com.chengliuxiang.framework.common.util.JsonUtils;
import com.chengliuxiang.xiaochengshu.note.biz.constant.RedisKeyConstants;
import com.chengliuxiang.xiaochengshu.note.biz.domain.dataobject.NoteDO;
import com.chengliuxiang.xiaochengshu.note.biz.domain.mapper.NoteDOMapper;
import com.chengliuxiang.xiaochengshu.note.biz.domain.mapper.TopicDOMapper;
import com.chengliuxiang.xiaochengshu.note.biz.enums.NoteStatusEnum;
import com.chengliuxiang.xiaochengshu.note.biz.enums.NoteTypeEnum;
import com.chengliuxiang.xiaochengshu.note.biz.enums.NoteVisibleEnum;
import com.chengliuxiang.xiaochengshu.note.biz.enums.ResponseCodeEnum;
import com.chengliuxiang.xiaochengshu.note.biz.model.vo.DeleteNoteReqVO;
import com.chengliuxiang.xiaochengshu.note.biz.model.vo.FindNoteDetailReqVO;
import com.chengliuxiang.xiaochengshu.note.biz.model.vo.FindNoteDetailRspVO;
import com.chengliuxiang.xiaochengshu.note.biz.model.vo.PublishNoteReqVO;
import com.chengliuxiang.xiaochengshu.note.biz.rpc.DistributedIdGeneratorRpcService;
import com.chengliuxiang.xiaochengshu.note.biz.rpc.KeyValueRpcService;
import com.chengliuxiang.xiaochengshu.note.biz.rpc.UserRpcService;
import com.chengliuxiang.xiaochengshu.note.biz.service.NoteService;
import com.chengliuxiang.xiaochengshu.user.dto.resp.FindUserByIdRspDTO;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.base.Preconditions;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

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
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Resource(name = "taskExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Resource
    private UserRpcService userRpcService;
    /**
     * 笔记详情本地缓存
     */
    private static final Cache<Long, String> LOCAL_CACHE = Caffeine.newBuilder()
            .initialCapacity(10000) // 设置初始容量为 10000 个条目
            .maximumSize(10000) // 设置缓存的最大容量为 10000 个条目
            .expireAfterWrite(1, TimeUnit.HOURS) // 设置缓存条目在写入后 1 小时过期
            .build();

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
        if (Objects.nonNull(topicId)) {
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
        } catch (Exception e) {
            log.error("==> 笔记存储失败", e);
            if (StringUtils.isNotBlank(contentUuid)) {
                keyValueRpcService.deleteNoteContent(contentUuid);
            }
        }
        return Response.success();
    }

    @Override
    public Response<?> deleteNote(DeleteNoteReqVO deleteNoteReqVO) {
        Long noteId = deleteNoteReqVO.getId();
        NoteDO selectNoteDO = noteDOMapper.selectByPrimaryKey(noteId);
        if (Objects.isNull(selectNoteDO)) {
            throw new BizException(ResponseCodeEnum.NOTE_NOT_FOUND);
        }
        Long currUserId = LoginUserContextHolder.getUserId();
        if (!Objects.equals(selectNoteDO.getCreatorId(), currUserId)) {
            throw new BizException(ResponseCodeEnum.NOTE_CANT_OPERATE);
        }
        NoteDO noteDO = NoteDO.builder()
                .id(noteId)
                .status(NoteStatusEnum.DELETED.getCode())
                .updateTime(LocalDateTime.now())
                .build();
        int count = noteDOMapper.updateByPrimaryKeySelective(noteDO);
        if (count == 0) {
            throw new BizException(ResponseCodeEnum.NOTE_NOT_FOUND);
        }
        return Response.success();
    }

    @Override
    @SneakyThrows
    public Response<FindNoteDetailRspVO> findNoteDetail(FindNoteDetailReqVO findNoteDetailReqVO) {
        Long noteId = findNoteDetailReqVO.getId();
        Long userId = LoginUserContextHolder.getUserId();
        String noteDetailLocalCache = LOCAL_CACHE.getIfPresent(noteId);
        // 命中本地缓存（包括 “null” 字符串）
        if (StringUtils.isNotBlank(noteDetailLocalCache)) {
            FindNoteDetailRspVO findNoteDetailRspVO = JsonUtils.parseObject(noteDetailLocalCache, FindNoteDetailRspVO.class);
            if (Objects.nonNull(findNoteDetailRspVO)) {
                // 校验可见性
                checkNoteVisible(findNoteDetailRspVO.getVisible(), userId, findNoteDetailRspVO.getCreatorId());
                return Response.success(findNoteDetailRspVO);
            } else {
                throw new BizException(ResponseCodeEnum.NOTE_NOT_FOUND);
            }
        }
        String noteDetailRedisKey = RedisKeyConstants.buildNoteDetailKey(noteId);
        String noteDetailJson = redisTemplate.opsForValue().get(noteDetailRedisKey);
        // 命中 Redis 缓存（包括 “null” 字符串）
        if (StringUtils.isNotBlank(noteDetailJson)) {
            FindNoteDetailRspVO findNoteDetailRspVO = JsonUtils.parseObject(noteDetailJson, FindNoteDetailRspVO.class);
            if (Objects.nonNull(findNoteDetailRspVO)) {
                threadPoolTaskExecutor.submit(() -> {
                    LOCAL_CACHE.put(noteId, JsonUtils.toJsonString(findNoteDetailRspVO));
                });
                checkNoteVisible(findNoteDetailRspVO.getVisible(), userId, findNoteDetailRspVO.getCreatorId());
                return Response.success(findNoteDetailRspVO);
            } else {
                threadPoolTaskExecutor.submit(() -> {
                    LOCAL_CACHE.put(noteId, "null");
                });
                throw new BizException(ResponseCodeEnum.NOTE_NOT_FOUND);
            }
        }

        // 如果缓存都没命中，查询数据库
        NoteDO noteDO = noteDOMapper.selectByPrimaryKey(noteId);
        // 如果笔记不存在
        if (Objects.isNull(noteDO)) {
            threadPoolTaskExecutor.submit(() -> {
                // 保底1分钟的随机秒数
                long expireSeconds = 60 + RandomUtil.randomInt(60);
                redisTemplate.opsForValue().set(noteDetailRedisKey, "null", expireSeconds, TimeUnit.SECONDS);
            });
            throw new BizException(ResponseCodeEnum.NOTE_NOT_FOUND);
        }
        checkNoteVisible(noteDO.getVisible(), userId, noteDO.getCreatorId());

        Long creatorId = noteDO.getCreatorId();
        CompletableFuture<FindUserByIdRspDTO> userInfoFuture = CompletableFuture
                .supplyAsync(() -> userRpcService.findUserById(creatorId), threadPoolTaskExecutor);
        CompletableFuture<String> noteContentFuture = CompletableFuture.completedFuture(null);
        if (Objects.equals(noteDO.getIsContentEmpty(), Boolean.FALSE)) {
            noteContentFuture = CompletableFuture
                    .supplyAsync(() -> keyValueRpcService.findNoteContent(noteDO.getContentUuid()), threadPoolTaskExecutor);
        }

        CompletableFuture<String> finalNoteContentFuture = noteContentFuture;
        CompletableFuture<FindNoteDetailRspVO> resultFuture = CompletableFuture
                .allOf(userInfoFuture, noteContentFuture)
                .thenApply(s -> {
                    FindUserByIdRspDTO findUserByIdRspDTO = userInfoFuture.join();
                    String content = finalNoteContentFuture.join();
                    Integer noteType = noteDO.getType();
                    String imgUrisStr = noteDO.getImgUris();
                    List<String> imgUris = null;
                    if (Objects.equals(noteType, NoteTypeEnum.IMAGE_TEXT.getCode())
                            && StringUtils.isNotBlank(imgUrisStr)) {
                        imgUris = List.of(imgUrisStr.split(","));
                    }
                    return FindNoteDetailRspVO.builder()
                            .id(noteDO.getId())
                            .type(noteDO.getType())
                            .title(noteDO.getTitle())
                            .content(content)
                            .imgUris(imgUris)
                            .topicId(noteDO.getTopicId())
                            .topicName(noteDO.getTopicName())
                            .creatorId(noteDO.getCreatorId())
                            .creatorName(findUserByIdRspDTO.getNickName())
                            .avatar(findUserByIdRspDTO.getAvatar())
                            .videoUri(noteDO.getVideoUri())
                            .updateTime(noteDO.getUpdateTime())
                            .visible(noteDO.getVisible())
                            .build();
                });

        FindNoteDetailRspVO findNoteDetailRspVO = resultFuture.get();

        // 异步将笔记详情存入 Redis
        threadPoolTaskExecutor.submit(() -> {
            String noteDetail = JsonUtils.toJsonString(findNoteDetailRspVO);
            // 保底1天的随机秒数
            long expireSeconds = 60 * 60 * 24 + RandomUtil.randomInt(60 * 60 * 24);
            redisTemplate.opsForValue().set(noteDetailRedisKey, noteDetail, expireSeconds, TimeUnit.SECONDS);
        });
        return Response.success(findNoteDetailRspVO);
    }

    private void checkNoteVisible(Integer visible, Long currUserId, Long creatorId) {
        if (Objects.equals(visible, NoteVisibleEnum.PRIVATE.getCode())
                && !Objects.equals(creatorId, currUserId)) {
            throw new BizException(ResponseCodeEnum.NOTE_PRIVATE);
        }
    }


}
