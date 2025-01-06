package com.swc.orangeBook.kv.biz.domain.repository;

import com.swc.orangeBook.kv.biz.domain.dataobject.NoteContentDO;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.UUID;

/**
 * @author Wilson
 * @Description: TODO
 * @date 2024/9/17 11:05
 */
public interface NoteContentRepository extends CassandraRepository<NoteContentDO, UUID> {
}
