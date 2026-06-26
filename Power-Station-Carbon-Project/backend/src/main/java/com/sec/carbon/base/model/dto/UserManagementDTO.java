package com.sec.carbon.base.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 用户管理创建/更新请求 DTO
 * <p>前端新增和编辑用户时传入的参数对象</p>
 *
 * @author Power-Station-Carbon-Team
 */
@Data
public class UserManagementDTO {

    /** 用户名（必填，4-20位） */
    @NotBlank(message = "用户名不能为空")
    @Size(min = 4, max = 20, message = "用户名长度需在 4-20 之间")
    private String userName;

    /** 真实姓名 */
    @Size(max = 64, message = "真实姓名长度不能超过 64 个字符")
    private String realName;

    /** 性别（0未知 1男 2女） */
    private Integer gender;

    /** 手机号 */
    @Size(max = 20, message = "手机号长度不能超过 20 个字符")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    /** 邮箱（可选，最大128字符） */
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "邮箱格式不正确")
    private String email;

    /** 所属部门 */
    @Size(max = 128, message = "部门名称长度不能超过 128 个字符")
    private String department;

    /** 职位 */
    @Size(max = 128, message = "职位名称长度不能超过 128 个字符")
    private String position;

    /** 状态（0禁用 1启用） */
    private Integer status;

    /** 备注 */
    @Size(max = 500, message = "备注长度不能超过 500 个字符")
    private String remark;

    /** 角色ID */
    private Long roleId;
}
