package com.sec.carbon.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sec.carbon.base.entity.SysMenu;
import com.sec.carbon.base.model.vo.MenuVO;

import java.util.List;

/**
 * 菜单管理服务接口
 *
 * @author Power-Station-Carbon-Team
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 获取菜单树（递归组装父子关系）
     *
     * @return 树形菜单列表
     */
    List<MenuVO> getMenuTree();
}
