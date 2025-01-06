package com.swc.orangeBook.user.relation.biz.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Wilson
 * @Description: 关注用户
 * @date 2024/9/19 18:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FollowUserMqDTO {
    private Long userId;

    private Long followUserId;

    private LocalDateTime createTime;
}
