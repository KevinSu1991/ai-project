package com.sec.carbon.base.controller;

import com.sec.carbon.base.common.Result;
import com.sec.carbon.base.model.vo.MenuVO;
import com.sec.carbon.base.service.SysMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 菜单管理 Controller
 * <p>提供菜单树查询接口（供前端路由/侧边栏使用）</p>
 *
 * @author Power-Station-Carbon-Team
 */
@Slf4j
@RestController
@RequestMapping("/api/sys/menu")
@RequiredArgsConstructor
@Tag(name = "菜单管理", description = "系统菜单树查询接口")
public class SysMenuController {

    private final SysMenuService sysMenuService;

    /**
     * 获取菜单树结构
     *
     * @return 树形菜单列表
     */
    @GetMapping("/tree")
    @Operation(summary = "获取菜单树")
    public Result<List<MenuVO>> tree() {
        List<MenuVO> menuTree = sysMenuService.getMenuTree();
        return Result.success(menuTree);
    }
}
