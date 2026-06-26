package com.sec.carbon.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sec.carbon.base.entity.SysUserManagement;
import com.sec.carbon.base.model.dto.UserManagementDTO;
import com.sec.carbon.base.model.vo.UserManagementVO;

/**
 * 用户管理服务接口
 * <p>提供用户管理模块的 CRUD 业务逻辑</p>
 *
 * @author Power-Station-Carbon-Team
 */
public interface SysUserManagementService extends IService<SysUserManagement> {

    /**
     * 分页查询用户管理列表（支持多条件筛选）
     *
     * @param page       分页参数
     * @param userName   用户名（模糊搜索，可选）
     * @param realName   真实姓名（模糊搜索，可选）
     * @param department 部门（模糊搜索，可选）
     * @param status     状态（精确匹配，可选）
     * @return 分页结果
     */
    IPage<UserManagementVO> pageUsers(Page<SysUserManagement> page, String userName,
                                       String realName, String department, Integer status);

    /**
     * 根据 ID 查询用户管理详情
     *
     * @param id 主键ID
     * @return UserManagementVO
     */
    UserManagementVO getUserById(Long id);

    /**
     * 创建用户管理记录
     *
     * @param dto 用户管理创建参数
     * @return 创建成功后的 UserManagementVO
     */
    UserManagementVO createUser(UserManagementDTO dto);

    /**
     * 更新用户管理记录
     *
     * @param id  主键ID
     * @param dto 用户管理更新参数
     * @return 更新后的 UserManagementVO
     */
    UserManagementVO updateUser(Long id, UserManagementDTO dto);

    /**
     * 删除用户管理记录
     *
     * @param id 主键ID
     * @return 是否成功
     */
    boolean deleteUser(Long id);
}
