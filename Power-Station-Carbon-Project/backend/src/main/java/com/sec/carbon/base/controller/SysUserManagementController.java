package com.sec.carbon.base.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sec.carbon.base.common.Result;
import com.sec.carbon.base.entity.SysUserManagement;
import com.sec.carbon.base.model.dto.UserManagementDTO;
import com.sec.carbon.base.model.vo.UserManagementVO;
import com.sec.carbon.base.service.SysUserManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理 Controller
 * <p>提供用户管理模块 CRUD RESTful 接口，路径统一为 /api/v1/user-management</p>
 *
 * @author Power-Station-Carbon-Team
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/user-management")
@RequiredArgsConstructor
@Tag(name = "用户管理", description = "用户管理模块增删改查接口")
public class SysUserManagementController {

    private final SysUserManagementService sysUserManagementService;

    /**
     * 分页查询用户管理列表（支持多条件搜索）
     *
     * @param page       页码（从1开始）
     * @param pageSize   每页条数
     * @param userName   用户名（可选，模糊搜索）
     * @param realName   真实姓名（可选，模糊搜索）
     * @param department 所属部门（可选，模糊搜索）
     * @param status     状态（可选，精确匹配）
     * @return 分页用户管理数据
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询用户管理列表")
    public Result<IPage<UserManagementVO>> page(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) String realName,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) Integer status) {
        Page<SysUserManagement> pageParam = new Page<>(page, pageSize);
        IPage<UserManagementVO> result = sysUserManagementService.pageUsers(
                pageParam, userName, realName, department, status);
        return Result.success(result);
    }

    /**
     * 根据 ID 查询用户管理详情
     *
     * @param id 主键ID
     * @return 用户管理详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "查询用户管理详情")
    public Result<UserManagementVO> getById(@PathVariable Long id) {
        UserManagementVO vo = sysUserManagementService.getUserById(id);
        return Result.success(vo);
    }

    /**
     * 创建用户管理记录
     *
     * @param dto 用户管理创建参数
     * @return 创建成功的用户信息
     */
    @PostMapping
    @Operation(summary = "创建用户管理记录")
    public Result<UserManagementVO> create(@Valid @RequestBody UserManagementDTO dto) {
        UserManagementVO vo = sysUserManagementService.createUser(dto);
        return Result.success("用户创建成功", vo);
    }

    /**
     * 更新用户管理记录
     *
     * @param id  主键ID
     * @param dto 用户管理更新参数
     * @return 更新后的用户信息
     */
    @PutMapping("/{id}")
    @Operation(summary = "更新用户管理记录")
    public Result<UserManagementVO> update(@PathVariable Long id, @Valid @RequestBody UserManagementDTO dto) {
        UserManagementVO vo = sysUserManagementService.updateUser(id, dto);
        return Result.success("用户更新成功", vo);
    }

    /**
     * 删除用户管理记录
     *
     * @param id 主键ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除用户管理记录")
    public Result<Void> delete(@PathVariable Long id) {
        sysUserManagementService.deleteUser(id);
        return Result.success("用户删除成功", null);
    }
}
