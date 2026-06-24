package com.sec.carbon.base.controller;

import com.sec.carbon.base.common.Result;
import com.sec.carbon.base.entity.SysRole;
import com.sec.carbon.base.model.vo.RoleVO;
import com.sec.carbon.base.service.SysRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.sec.carbon.base.common.EntityConverter.INSTANCE;

/**
 * 角色管理 Controller
 * <p>提供角色列表查询接口（供前端下拉选择使用）</p>
 *
 * @author Power-Station-Carbon-Team
 */
@Slf4j
@RestController
@RequestMapping("/api/sys/role")
@RequiredArgsConstructor
@Tag(name = "角色管理", description = "系统角色查询接口")
public class SysRoleController {

    private final SysRoleService sysRoleService;

    /**
     * 获取所有角色列表（无分页，用于下拉选择框）
     *
     * @return 角色列表
     */
    @GetMapping("/list")
    @Operation(summary = "获取所有角色列表")
    public Result<List<RoleVO>> list() {
        List<SysRole> roles = sysRoleService.list();
        List<RoleVO> roleVOs = roles.stream()
                .map(INSTANCE::toRoleVO)
                .collect(Collectors.toList());
        return Result.success(roleVOs);
    }
}
