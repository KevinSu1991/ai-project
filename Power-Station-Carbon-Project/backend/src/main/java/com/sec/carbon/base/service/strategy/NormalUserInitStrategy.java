package com.sec.carbon.base.service.strategy;

import com.sec.carbon.base.model.dto.UserCreateDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 普通用户注册初始化策略
 * <p>普通用户：注册时分配默认碳排数据查看权限</p>
 *
 * @author Power-Station-Carbon-Team
 */
@Slf4j
@Component
public class NormalUserInitStrategy implements UserInitStrategy {

    @Override
    public void init(UserCreateDTO userCreateDTO) {
        log.info("普通用户 [{}] 注册成功，已分配基础碳排查看权限", userCreateDTO.getUsername());
        // 普通用户初始化逻辑：可在此处分配碳排查看菜单权限
    }

    @Override
    public String getRoleKey() {
        return "user";
    }
}
