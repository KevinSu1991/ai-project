package com.sec.carbon.base.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 系统权限菜单实体
 * <p>对应数据库表 sys_menu，支持树形层级结构</p>
 *
 * @author Power-Station-Carbon-Team
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_menu")
public class SysMenu {

    /** 菜单ID（主键，自增） */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 父菜单ID（0 表示顶级菜单） */
    private Long parentId;

    /** 菜单名称 */
    private String menuName;

    /** 前端路由路径 */
    private String path;

    /** 前端组件路径 */
    private String component;

    /** 创建时间（自动填充） */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /** 更新时间（自动填充） */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
