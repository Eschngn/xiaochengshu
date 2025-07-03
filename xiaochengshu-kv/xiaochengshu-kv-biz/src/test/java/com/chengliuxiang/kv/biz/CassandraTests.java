package com.chengliuxiang.kv.biz;

import com.chengliuxiang.framework.common.utils.JsonUtil;
import com.chengliuxiang.kv.biz.domain.dataobject.NoteContentDO;
import com.chengliuxiang.kv.biz.domain.repository.NoteContentRepository;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@Slf4j
public class CassandraTests {

    @Resource
    private NoteContentRepository noteContentRepository;

    @Test
    void testInsert() {
        NoteContentDO nodeContent = NoteContentDO.builder()
                .id(UUID.randomUUID())
                .content("代码测试笔记内容插入")
                .build();

        noteContentRepository.save(nodeContent);
    }

    @Test
    void testUpdate() {
        NoteContentDO nodeContent = NoteContentDO.builder()
                .id(UUID.fromString("0b6ed125-208c-4646-9a2b-87937581244e"))
                .content("代码测试笔记内容更新")
                .build();

        noteContentRepository.save(nodeContent);
    }

    @Test
    void testSelect() {
        Optional<NoteContentDO> optional = noteContentRepository.findById(UUID.fromString("0b6ed125-208c-4646-9a2b-87937581244e"));
        optional.ifPresent(noteContentDO -> log.info("查询结果：{}", JsonUtil.toJsonString(noteContentDO)));
    }

    @Test
    void testDelete() {
        noteContentRepository.deleteById(UUID.fromString("0b6ed125-208c-4646-9a2b-87937581244e"));
    }
}
