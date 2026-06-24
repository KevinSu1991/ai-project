package com.sec.carbon.base.service.strategy;

import com.sec.carbon.base.model.dto.UserCreateDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 管理员注册初始化策略
 * <p>管理员角色：注册时记录日志，不需要额外初始化逻辑</p>
 *
 * @author Power-Station-Carbon-Team
 */
@Slf4j
@Component
public class AdminInitStrategy implements UserInitStrategy {

    @Override
    public void init(UserCreateDTO userCreateDTO) {
        log.info("管理员用户 [{}] 注册成功，默认拥有全部权限", userCreateDTO.getUsername());
        // 管理员初始化逻辑：可在此处分配系统全部菜单权限
    }

    @Override
    public String getRoleKey() {
        return "admin";
    }
}
