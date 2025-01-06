package com.swc.orangeBook.note.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Wilson
 * @Description: 笔记可见性
 * @date 2024/9/18 0:04
 */
@Getter
@AllArgsConstructor
public enum NoteVisibleEnum {
    PUBLIC(0), // 公开，所有人可见
    PRIVATE(1); // 仅自己可见

    private final Integer code;
}
