package com.chengliuxiang.xiaochengshu.note.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NoteVisibleEnum {
    PUBLIC(0),
    PRIVATE(1);

    private final Integer code;
}
