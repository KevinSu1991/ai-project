package com.sec.carbon.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sec.carbon.base.entity.SysRole;
import com.sec.carbon.base.entity.SysUserManagement;
import com.sec.carbon.base.mapper.SysRoleMapper;
import com.sec.carbon.base.mapper.SysUserManagementMapper;
import com.sec.carbon.base.model.dto.UserManagementDTO;
import com.sec.carbon.base.model.vo.UserManagementVO;
import com.sec.carbon.base.service.SysUserManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * 用户管理服务实现
 * <p>使用构造器注入（@RequiredArgsConstructor），符合 Spring 最佳实践</p>
 * <p>提供用户管理模块的完整 CRUD 业务逻辑</p>
 *
 * @author Power-Station-Carbon-Team
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserManagementServiceImpl extends ServiceImpl<SysUserManagementMapper, SysUserManagement>
        implements SysUserManagementService {

    private final SysUserManagementMapper userManagementMapper;
    private final SysRoleMapper sysRoleMapper;

    @Override
    public IPage<UserManagementVO> pageUsers(Page<SysUserManagement> page, String userName,
                                              String realName, String department, Integer status) {
        // 构建查询条件
        LambdaQueryWrapper<SysUserManagement> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(userName)) {
            wrapper.like(SysUserManagement::getUserName, userName);
        }
        if (StringUtils.hasText(realName)) {
            wrapper.like(SysUserManagement::getRealName, realName);
        }
        if (StringUtils.hasText(department)) {
            wrapper.like(SysUserManagement::getDepartment, department);
        }
        if (status != null) {
            wrapper.eq(SysUserManagement::getStatus, status);
        }
        wrapper.orderByDesc(SysUserManagement::getCreateTime);

        IPage<SysUserManagement> userPage = userManagementMapper.selectPage(page, wrapper);

        // Entity → VO（关联查询角色名称）
        return userPage.convert(this::convertToVO);
    }

    @Override
    public UserManagementVO getUserById(Long id) {
        SysUserManagement user = userManagementMapper.selectById(id);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        return convertToVO(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserManagementVO createUser(UserManagementDTO dto) {
        log.info("创建用户管理记录: userName={}", dto.getUserName());

        // 1. 校验用户名是否已存在
        LambdaQueryWrapper<SysUserManagement> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUserManagement::getUserName, dto.getUserName());
        if (userManagementMapper.selectCount(wrapper) > 0) {
            throw new RuntimeException("用户名已存在");
        }

        // 2. 构建实体并插入
        SysUserManagement entity = new SysUserManagement();
        fillEntity(entity, dto);
        userManagementMapper.insert(entity);

        return convertToVO(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserManagementVO updateUser(Long id, UserManagementDTO dto) {
        log.info("更新用户管理记录: id={}", id);

        // 1. 校验用户是否存在
        SysUserManagement existing = userManagementMapper.selectById(id);
        if (existing == null) {
            throw new RuntimeException("用户不存在");
        }

        // 2. 校验用户名唯一性（如果修改了用户名）
        if (!existing.getUserName().equals(dto.getUserName())) {
            LambdaQueryWrapper<SysUserManagement> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysUserManagement::getUserName, dto.getUserName());
            if (userManagementMapper.selectCount(wrapper) > 0) {
                throw new RuntimeException("用户名已存在");
            }
        }

        // 3. 更新实体
        fillEntity(existing, dto);
        userManagementMapper.updateById(existing);

        return convertToVO(existing);
    }

    @Override
    public boolean deleteUser(Long id) {
        log.info("删除用户管理记录: id={}", id);
        return userManagementMapper.deleteById(id) > 0;
    }

    /**
     * 将 DTO 字段填充到 Entity
     *
     * @param entity 目标实体
     * @param dto    来源 DTO
     */
    private void fillEntity(SysUserManagement entity, UserManagementDTO dto) {
        entity.setUserName(dto.getUserName());
        entity.setRealName(dto.getRealName());
        entity.setGender(dto.getGender());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setDepartment(dto.getDepartment());
        entity.setPosition(dto.getPosition());
        entity.setStatus(dto.getStatus() != null ? dto.getStatus() : 1);
        entity.setRemark(dto.getRemark());
        entity.setRoleId(dto.getRoleId());
    }

    /**
     * 将 Entity 转换为 VO（含角色名称）
     *
     * @param entity 用户管理实体
     * @return UserManagementVO
     */
    private UserManagementVO convertToVO(SysUserManagement entity) {
        UserManagementVO vo = new UserManagementVO();
        vo.setId(entity.getId());
        vo.setUserName(entity.getUserName());
        vo.setRealName(entity.getRealName());
        vo.setGender(entity.getGender());
        vo.setPhone(entity.getPhone());
        vo.setEmail(entity.getEmail());
        vo.setDepartment(entity.getDepartment());
        vo.setPosition(entity.getPosition());
        vo.setStatus(entity.getStatus());
        vo.setRemark(entity.getRemark());
        vo.setRoleId(entity.getRoleId());
        vo.setCreateTime(entity.getCreateTime());
        vo.setUpdateTime(entity.getUpdateTime());

        // 关联查询角色名称
        if (entity.getRoleId() != null) {
            SysRole role = sysRoleMapper.selectById(entity.getRoleId());
            vo.setRoleName(role != null ? role.getRoleName() : "未分配");
        } else {
            vo.setRoleName("未分配");
        }

        return vo;
    }
}
