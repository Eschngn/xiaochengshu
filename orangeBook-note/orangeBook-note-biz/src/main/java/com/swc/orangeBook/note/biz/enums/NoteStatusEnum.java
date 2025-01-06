package com.swc.orangeBook.note.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Wilson
 * @Description: 笔记状态
 * @date 2024/9/18 0:04
 */
@Getter
@AllArgsConstructor
public enum NoteStatusEnum {
    BE_EXAMINE(0), // 待审核
    NORMAL(1), // 正常展示
    DELETED(2), // 被删除
    DOWNED(2), // 被下架
    ;

    private final Integer code;
}
