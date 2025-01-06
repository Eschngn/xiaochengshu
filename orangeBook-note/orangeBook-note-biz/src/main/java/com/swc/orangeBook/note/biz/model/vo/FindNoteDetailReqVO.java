package com.swc.orangeBook.note.biz.model.vo;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Wilson
 * @Description: 查询笔记详情
 * @date 2024/9/18 14:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindNoteDetailReqVO {
    @NotNull(message = "笔记 ID 不能为空")
    private Long id;
}
