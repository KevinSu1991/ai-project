package com.sec.carbon.base.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 用户注册/创建请求 DTO
 *
 * @author Power-Station-Carbon-Team
 */
@Data
public class UserCreateDTO {

    /** 用户名（必填，4-20位） */
    @NotBlank(message = "用户名不能为空")
    @Size(min = 4, max = 20, message = "用户名长度需在 4-20 之间")
    private String username;

    /** 密码（必填，6-20位） */
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度需在 6-20 之间")
    private String password;

    /** 昵称 */
    private String nickname;

    /** 角色ID */
    private Long roleId;
}
