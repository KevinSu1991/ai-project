/**
 * 用户管理 API 接口定义
 * <p>字段与后端 DTO/VO 严格对应，使用 camelCase 命名</p>
 */

import request from "@/utils/request";
import { ApiResult } from "@/utils/request";

// ======================== 类型定义（与后端 VO/DTO 对应） ========================

/** 用户信息（对应 UserVO） */
export interface UserVO {
  id: number;
  username: string;
  nickname: string;
  roleId: number;
  roleName: string;
  createTime: string;
  updateTime: string;
}

/** 创建用户参数（对应 UserCreateDTO） */
export interface UserCreateDTO {
  username: string;
  password: string;
  nickname: string;
  roleId: number;
}

/** 更新用户参数 */
export interface UserUpdateDTO {
  id: number;
  username?: string;
  nickname?: string;
  roleId?: number;
}

/** 分页查询参数 */
export interface PageParams {
  page: number;
  pageSize: number;
  username?: string;
}

/** 分页结果 */
export interface PageResult<T> {
  records: T[];
  total: number;
  size: number;
  current: number;
  pages: number;
}

// ======================== API 方法 ========================

/**
 * 分页查询用户列表
 */
export function getUserPage(params: PageParams) {
  return request.get<any, ApiResult<PageResult<UserVO>>>("/sys/user/page", { params });
}

/**
 * 根据 ID 查询用户详情
 */
export function getUserById(id: number) {
  return request.get<any, ApiResult<UserVO>>(`/sys/user/${id}`);
}

/**
 * 创建用户
 */
export function createUser(data: UserCreateDTO) {
  return request.post<any, ApiResult<UserVO>>("/sys/user", data);
}

/**
 * 更新用户信息
 */
export function updateUser(data: UserUpdateDTO) {
  return request.put<any, ApiResult<UserVO>>("/sys/user", data);
}

/**
 * 删除用户
 */
export function deleteUser(id: number) {
  return request.delete<any, ApiResult<null>>(`/sys/user/${id}`);
}
