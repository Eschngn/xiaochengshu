package com.chengliuxiang.xiaochengshu.user.biz.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserInfoReqVO {
    /**
     * 头像
     */
    private MultipartFile avatar;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 小橙书 ID
     */
    private String xiaochengshuId;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 生日
     */
    private LocalDateTime birthday;

    /**
     * 个人介绍
     */
    private String introduction;

    /**
     * 背景图
     */
    private MultipartFile backgroundImg;
}
