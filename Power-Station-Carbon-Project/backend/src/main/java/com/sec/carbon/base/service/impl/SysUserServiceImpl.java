package com.sec.carbon.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sec.carbon.base.common.EntityConverter;
import com.sec.carbon.base.entity.SysRole;
import com.sec.carbon.base.entity.SysUser;
import com.sec.carbon.base.mapper.SysRoleMapper;
import com.sec.carbon.base.mapper.SysUserMapper;
import com.sec.carbon.base.model.dto.UserCreateDTO;
import com.sec.carbon.base.model.vo.UserVO;
import com.sec.carbon.base.service.SysUserService;
import com.sec.carbon.base.service.strategy.UserInitStrategyContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * 用户管理服务实现
 * <p>使用构造器注入（@RequiredArgsConstructor），符合 Spring 最佳实践</p>
 * <p>用户注册时通过策略模式（UserInitStrategyContext）分发不同角色的初始化逻辑</p>
 *
 * @author Power-Station-Carbon-Team
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final SysUserMapper sysUserMapper;
    private final SysRoleMapper sysRoleMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserInitStrategyContext userInitStrategyContext;

    @Override
    public IPage<UserVO> pageUsers(Page<SysUser> page, String username) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(username)) {
            wrapper.like(SysUser::getUsername, username);
        }
        wrapper.orderByDesc(SysUser::getCreateTime);

        IPage<SysUser> userPage = sysUserMapper.selectPage(page, wrapper);

        // Entity + RoleName → VO
        return userPage.convert(user -> {
            SysRole role = sysRoleMapper.selectById(user.getRoleId());
            String roleName = role != null ? role.getRoleName() : "未分配";
            return EntityConverter.INSTANCE.toUserVO(user, roleName);
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserVO createUser(UserCreateDTO userCreateDTO) {
        log.info("创建用户: username={}", userCreateDTO.getUsername());

        // 1. 校验用户名是否已存在
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername, userCreateDTO.getUsername());
        if (sysUserMapper.selectCount(wrapper) > 0) {
            throw new RuntimeException("用户名已存在");
        }

        // 2. 构建用户实体
        SysUser user = new SysUser();
        user.setUsername(userCreateDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));
        user.setNickname(userCreateDTO.getNickname());
        user.setRoleId(userCreateDTO.getRoleId());

        sysUserMapper.insert(user);

        // 3. 策略模式：根据角色执行不同的初始化逻辑
        SysRole role = sysRoleMapper.selectById(user.getRoleId());
        if (role != null) {
            userInitStrategyContext.execute(role.getRoleKey(), userCreateDTO);
        }

        // 4. 返回结果
        String roleName = role != null ? role.getRoleName() : "未分配";
        return EntityConverter.INSTANCE.toUserVO(user, roleName);
    }

    @Override
    public UserVO updateUser(SysUser user) {
        log.info("更新用户: id={}", user.getId());
        sysUserMapper.updateById(user);

        SysUser updated = sysUserMapper.selectById(user.getId());
        SysRole role = sysRoleMapper.selectById(updated.getRoleId());
        String roleName = role != null ? role.getRoleName() : "未分配";
        return EntityConverter.INSTANCE.toUserVO(updated, roleName);
    }

    @Override
    public UserVO getUserById(Long id) {
        SysUser user = sysUserMapper.selectById(id);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        SysRole role = sysRoleMapper.selectById(user.getRoleId());
        String roleName = role != null ? role.getRoleName() : "未分配";
        return EntityConverter.INSTANCE.toUserVO(user, roleName);
    }

    @Override
    public boolean deleteUser(Long id) {
        log.info("删除用户: id={}", id);
        return sysUserMapper.deleteById(id) > 0;
    }
}
