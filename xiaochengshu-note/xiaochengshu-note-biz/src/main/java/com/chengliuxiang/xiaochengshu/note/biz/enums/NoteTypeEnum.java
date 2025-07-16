package com.chengliuxiang.xiaochengshu.note.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public enum NoteTypeEnum {

    IMAGE_TEXT(0, "图文"),
    VIDEO(1, "视频");

    private final Integer code;

    private final String description;

    public static boolean isValid(Integer code) {
        for (NoteTypeEnum noteTypeEnum : NoteTypeEnum.values()) {
            if (Objects.equals(noteTypeEnum.getCode(), code)) {
                return true;
            }
        }
        return false;
    }

    public static NoteTypeEnum valueOf(Integer code) {
        for (NoteTypeEnum noteTypeEnum : NoteTypeEnum.values()) {
            if (Objects.equals(noteTypeEnum.getCode(), code)) {
                return noteTypeEnum;
            }
        }
        return null;
    }
}
