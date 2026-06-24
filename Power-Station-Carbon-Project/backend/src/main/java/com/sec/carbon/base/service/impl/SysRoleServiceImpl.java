package com.sec.carbon.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sec.carbon.base.entity.SysRole;
import com.sec.carbon.base.mapper.SysRoleMapper;
import com.sec.carbon.base.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 角色管理服务实现
 *
 * @author Power-Station-Carbon-Team
 */
@Slf4j
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
}
