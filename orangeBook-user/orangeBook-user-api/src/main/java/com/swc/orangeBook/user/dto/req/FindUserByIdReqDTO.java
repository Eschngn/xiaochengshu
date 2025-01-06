package com.swc.orangeBook.user.dto.req;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Wilson
 * @Description: 根据用户 ID 查询用户信息
 * @date 2024/9/18 13:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindUserByIdReqDTO {
    /**
     * 手机号
     */
    @NotNull(message = "用户 ID 不能为空")
    private Long id;
}
