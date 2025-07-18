package com.chengliuxiang.xiaochengshu.kv.dto.req;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
public class DeleteNoteContentReqDTO {

    @NotNull(message = "笔记内容 UUID 不能为空")
    private String uuid;
}
