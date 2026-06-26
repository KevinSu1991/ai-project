package com.sec.carbon.base.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户管理信息 VO（返回给前端）
 * <p>字段与数据库 sys_user_management 表对应，采用 camelCase 命名</p>
 *
 * @author Power-Station-Carbon-Team
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserManagementVO {

    /** 主键ID */
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

    /** 角色ID */
    private Long roleId;

    /** 角色名称（关联查询 sys_role 表获得） */
    private String roleName;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;
}
