package com.sec.carbon.base.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 菜单信息 VO（返回给前端，支持树形结构）
 *
 * @author Power-Station-Carbon-Team
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuVO {

    /** 菜单ID */
    private Long id;

    /** 父菜单ID */
    private Long parentId;

    /** 菜单名称 */
    private String menuName;

    /** 前端路由路径 */
    private String path;

    /** 前端组件路径 */
    private String component;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 子菜单列表 */
    private List<MenuVO> children;
}
