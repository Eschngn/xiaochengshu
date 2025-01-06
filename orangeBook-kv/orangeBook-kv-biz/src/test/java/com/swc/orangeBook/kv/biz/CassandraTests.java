package com.swc.orangeBook.kv.biz;

import com.swc.framework.common.util.JsonUtil;
import com.swc.orangeBook.kv.biz.domain.dataobject.NoteContentDO;
import com.swc.orangeBook.kv.biz.domain.repository.NoteContentRepository;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Wilson
 * @Description: TODO
 * @date 2024/9/17 11:23
 */
@SpringBootTest
@Slf4j
public class CassandraTests {
    @Resource
    private NoteContentRepository noteContentRepository;


    /**
     * 测试插入数据
     */
    @Test
    void testInsert(){
        NoteContentDO nodeContent = NoteContentDO.builder()
                .id(UUID.randomUUID())
                .content("代码测试笔记内容插入")
                .build();
        noteContentRepository.save(nodeContent);
    }

    /**
     * 测试修改数据
     */
    @Test
    void testUpdate() {
        NoteContentDO nodeContent = NoteContentDO.builder()
                .id(UUID.fromString("f56cddb1-9a31-4100-b759-a3e532855477"))
                .content("代码测试笔记内容更新")
                .build();

        noteContentRepository.save(nodeContent);
    }

    /**
     * 测试查询数据
     */
    @Test
    void testSelect() {
        Optional<NoteContentDO> optional = noteContentRepository.findById(UUID.fromString("f56cddb1-9a31-4100-b759-a3e532855477"));
        optional.ifPresent(noteContentDO -> log.info("查询结果：{}", JsonUtil.toJsonString(noteContentDO)));
    }
}
