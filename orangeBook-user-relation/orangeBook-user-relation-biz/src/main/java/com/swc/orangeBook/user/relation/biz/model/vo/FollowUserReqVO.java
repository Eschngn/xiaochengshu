package com.swc.orangeBook.user.relation.biz.model.vo;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Wilson
 * @Description: 关注用户
 * @date 2024/9/19 15:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FollowUserReqVO {
    @NotNull(message = "被关注用户 ID 不能为空")
    private Long followUserId;
}
