package com.chengliuxiang.xiaochengshu.kv.dto.req;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FindNoteContentReqDTO {
    @NotNull(message = "笔记内容 UUID 不能为空")
    private String uuid;
}
