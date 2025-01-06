package com.swc.orangeBook.user.dto.req;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Wilson
 * @Description: TODO
 * @date 2024/9/29 14:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindUsersByIdsReqDTO {

    @NotNull(message = "用户 ID 集合不能为空")
    @Size(min = 1, max = 10, message = "用户 ID 集合大小必须大于等于 1, 小于等于 10")
    private List<Long> ids;

}
