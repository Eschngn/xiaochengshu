package com.swc.orangeBook.kv.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Wilson
 * @Description: 查询笔记内容
 * @date 2024/9/17 12:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindNoteContentReqDTO {
    @NotBlank(message = "笔记内容 UUID 不能为空")
    private String uuid;
}
