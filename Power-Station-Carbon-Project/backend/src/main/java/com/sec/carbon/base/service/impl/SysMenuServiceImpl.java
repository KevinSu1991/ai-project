package com.sec.carbon.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sec.carbon.base.entity.SysMenu;
import com.sec.carbon.base.mapper.SysMenuMapper;
import com.sec.carbon.base.model.vo.MenuVO;
import com.sec.carbon.base.service.SysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单管理服务实现
 *
 * @author Power-Station-Carbon-Team
 */
@Slf4j
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Override
    public List<MenuVO> getMenuTree() {
        // 1. 查询所有菜单
        List<SysMenu> allMenus = baseMapper.selectList(
                new LambdaQueryWrapper<SysMenu>().orderByAsc(SysMenu::getId)
        );

        // 2. 找出顶级菜单并递归构建子菜单
        return allMenus.stream()
                .filter(menu -> menu.getParentId() == 0)
                .map(menu -> buildMenuTree(menu, allMenus))
                .collect(Collectors.toList());
    }

    /**
     * 递归构建单个菜单节点及其子菜单
     *
     * @param parent   父菜单
     * @param allMenus 所有菜单列表
     * @return 包含子菜单的 MenuVO
     */
    private MenuVO buildMenuTree(SysMenu parent, List<SysMenu> allMenus) {
        MenuVO vo = new MenuVO();
        vo.setId(parent.getId());
        vo.setParentId(parent.getParentId());
        vo.setMenuName(parent.getMenuName());
        vo.setPath(parent.getPath());
        vo.setComponent(parent.getComponent());
        vo.setCreateTime(parent.getCreateTime());

        // 递归查找子菜单
        List<MenuVO> children = allMenus.stream()
                .filter(menu -> menu.getParentId().equals(parent.getId()))
                .map(menu -> buildMenuTree(menu, allMenus))
                .collect(Collectors.toList());
        vo.setChildren(children);

        return vo;
    }
}
