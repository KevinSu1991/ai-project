package com.sec.carbon.base.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 角色信息 VO（返回给前端）
 *
 * @author Power-Station-Carbon-Team
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleVO {

    /** 角色ID */
    private Long id;

    /** 角色名称 */
    private String roleName;

    /** 角色标识 */
    private String roleKey;

    /** 角色描述 */
    private String description;

    /** 创建时间 */
    private LocalDateTime createTime;
}
