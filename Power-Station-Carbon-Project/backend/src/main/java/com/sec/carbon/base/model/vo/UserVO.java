package com.sec.carbon.base.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户信息 VO（返回给前端）
 * <p>字段与数据库 sys_user 表对应，采用 camelCase 命名</p>
 *
 * @author Power-Station-Carbon-Team
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {

    /** 用户ID */
    private Long id;

    /** 用户名 */
    private String username;

    /** 昵称 */
    private String nickname;

    /** 角色ID */
    private Long roleId;

    /** 角色名称（冗余显示） */
    private String roleName;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;
}
