package com.swc.orangebook.auth.model.vo.verificationcode;
/**
 * @Description:
 * @author swc
 * @date 2024/9/8 11:27
 */

import com.swc.framework.common.validator.PhoneNumber;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: orangeBook
 *
 * @description: 短信验证入参实体类
 *
 * @author: Wilson
 *
 * @create: 2024-09-08 11:27
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SendVerificationCodeReqVO {
    @NotBlank(message = "手机号不能为空")
    @PhoneNumber
    private String phone;
}
