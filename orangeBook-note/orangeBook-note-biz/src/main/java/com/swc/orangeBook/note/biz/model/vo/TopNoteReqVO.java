package com.swc.orangeBook.note.biz.model.vo;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Wilson
 * @Description: 笔记置顶/取消置顶
 * @date 2024/9/19 14:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TopNoteReqVO {
    @NotNull(message = "笔记 ID 不能为空")
    private Long id;

    @NotNull(message = "置顶状态不能为空")
    private Boolean isTop;
}
