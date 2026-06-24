package com.sec.carbon.base.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sec.carbon.base.common.Result;
import com.sec.carbon.base.entity.SysUser;
import com.sec.carbon.base.model.dto.UserCreateDTO;
import com.sec.carbon.base.model.vo.UserVO;
import com.sec.carbon.base.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理 Controller
 * <p>提供用户 CRUD RESTful 接口</p>
 *
 * @author Power-Station-Carbon-Team
 */
@Slf4j
@RestController
@RequestMapping("/api/sys/user")
@RequiredArgsConstructor
@Tag(name = "用户管理", description = "系统用户增删改查接口")
public class SysUserController {

    private final SysUserService sysUserService;

    /**
     * 分页查询用户列表
     *
     * @param page     页码（从1开始）
     * @param pageSize 每页条数
     * @param username 用户名（可选，模糊搜索）
     * @return 分页用户数据
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询用户列表")
    public Result<IPage<UserVO>> page(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String username) {
        Page<SysUser> pageParam = new Page<>(page, pageSize);
        IPage<UserVO> result = sysUserService.pageUsers(pageParam, username);
        return Result.success(result);
    }

    /**
     * 根据 ID 查询用户详情
     *
     * @param id 用户ID
     * @return 用户详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "查询用户详情")
    public Result<UserVO> getById(@PathVariable Long id) {
        UserVO userVO = sysUserService.getUserById(id);
        return Result.success(userVO);
    }

    /**
     * 创建用户
     *
     * @param userCreateDTO 用户创建参数
     * @return 创建成功的用户信息
     */
    @PostMapping
    @Operation(summary = "创建用户")
    public Result<UserVO> create(@Valid @RequestBody UserCreateDTO userCreateDTO) {
        UserVO userVO = sysUserService.createUser(userCreateDTO);
        return Result.success("用户创建成功", userVO);
    }

    /**
     * 更新用户信息
     *
     * @param user 用户实体
     * @return 更新后的用户信息
     */
    @PutMapping
    @Operation(summary = "更新用户信息")
    public Result<UserVO> update(@RequestBody SysUser user) {
        UserVO userVO = sysUserService.updateUser(user);
        return Result.success("用户更新成功", userVO);
    }

    /**
     * 删除用户
     *
     * @param id 用户ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除用户")
    public Result<Void> delete(@PathVariable Long id) {
        sysUserService.deleteUser(id);
        return Result.success("用户删除成功", null);
    }
}
