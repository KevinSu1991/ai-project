package com.sec.carbon.base.service.strategy;

import com.sec.carbon.base.model.dto.UserCreateDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 用户初始化策略上下文
 * <p>负责管理所有 UserInitStrategy 实现，根据角色标识分发执行</p>
 * <p>使用构造器注入，Spring 会自动收集所有 UserInitStrategy Bean 到 Map 中</p>
 *
 * @author Power-Station-Carbon-Team
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserInitStrategyContext {

    /**
     * Spring 自动注入所有 UserInitStrategy 实现，key = getRoleKey() 返回值
     */
    private final Map<String, UserInitStrategy> strategyMap;

    /**
     * 根据用户角色执行对应的初始化策略
     *
     * @param roleKey       角色标识
     * @param userCreateDTO 用户创建请求
     */
    public void execute(String roleKey, UserCreateDTO userCreateDTO) {
        UserInitStrategy strategy = strategyMap.get(roleKey);
        if (strategy != null) {
            strategy.init(userCreateDTO);
        } else {
            log.warn("未找到角色 [{}] 对应的初始化策略，跳过初始化", roleKey);
        }
    }
}
