package com.sec.carbon.base.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户管理实体
 * <p>对应数据库表 sys_user_management</p>
 *
 * @author Power-Station-Carbon-Team
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_user_management")
public class SysUserManagement {

    /** 主键ID（自增） */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 用户名 */
    private String userName;

    /** 真实姓名 */
    private String realName;

    /** 性别（0未知 1男 2女） */
    private Integer gender;

    /** 手机号 */
    private String phone;

    /** 邮箱 */
    private String email;

    /** 所属部门 */
    private String department;

    /** 职位 */
    private String position;

    /** 状态（0禁用 1启用） */
    private Integer status;

    /** 备注 */
    private String remark;

    /** 角色ID（关联 sys_role.id） */
    private Long roleId;

    /** 创建时间（自动填充） */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /** 更新时间（自动填充） */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
