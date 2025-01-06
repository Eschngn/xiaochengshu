package com.swc.orangeBook.kv.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Wilson
 * @Description: 笔记内容删除
 * @date 2024/9/17 12:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeleteNoteContentReqDTO {
    @NotBlank(message = "笔记内容 UUID 不能为空")
    private String uuid;

}
