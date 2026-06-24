package com.sec.carbon.base.service.strategy;

import com.sec.carbon.base.model.dto.UserCreateDTO;

/**
 * 用户注册初始化策略接口
 * <p>策略模式：不同角色注册时执行不同的初始化逻辑</p>
 *
 * @author Power-Station-Carbon-Team
 */
public interface UserInitStrategy {

    /**
     * 根据角色执行用户初始化逻辑
     *
     * @param userCreateDTO 用户创建请求
     */
    void init(UserCreateDTO userCreateDTO);

    /**
     * 返回该策略对应的角色标识
     *
     * @return 角色标识（如 "admin"、"user"）
     */
    String getRoleKey();
}
