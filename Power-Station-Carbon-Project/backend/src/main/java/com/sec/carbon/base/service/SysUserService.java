package com.sec.carbon.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sec.carbon.base.entity.SysUser;
import com.sec.carbon.base.model.dto.UserCreateDTO;
import com.sec.carbon.base.model.vo.UserVO;

/**
 * 用户管理服务接口
 *
 * @author Power-Station-Carbon-Team
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 分页查询用户列表
     *
     * @param page     分页参数
     * @param username 用户名（模糊搜索，可选）
     * @return 分页结果
     */
    IPage<UserVO> pageUsers(Page<SysUser> page, String username);

    /**
     * 创建用户（含策略模式初始化）
     *
     * @param userCreateDTO 用户创建请求
     * @return 创建成功后的 UserVO
     */
    UserVO createUser(UserCreateDTO userCreateDTO);

    /**
     * 更新用户信息
     *
     * @param user 用户实体
     * @return 更新后的 UserVO
     */
    UserVO updateUser(SysUser user);

    /**
     * 根据 ID 查询用户详情
     *
     * @param id 用户ID
     * @return UserVO
     */
    UserVO getUserById(Long id);

    /**
     * 删除用户
     *
     * @param id 用户ID
     * @return 是否成功
     */
    boolean deleteUser(Long id);
}
