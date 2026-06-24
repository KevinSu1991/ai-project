package com.sec.carbon.base.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 系统角色实体
 * <p>对应数据库表 sys_role</p>
 *
 * @author Power-Station-Carbon-Team
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_role")
public class SysRole {

    /** 角色ID（主键，自增） */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 角色名称（显示用） */
    private String roleName;

    /** 角色标识（权限判断用，如 admin、user） */
    private String roleKey;

    /** 角色描述 */
    private String description;

    /** 创建时间（自动填充） */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /** 更新时间（自动填充） */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
