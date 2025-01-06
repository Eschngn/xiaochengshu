package com.swc.orangeBook.note.biz.model.vo;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Wilson
 * @Description: 笔记仅对自己可见
 * @date 2024/9/19 14:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateNoteVisibleOnlyMeReqVO {
    @NotNull(message = "笔记 ID 不能为空")
    private Long id;
}
