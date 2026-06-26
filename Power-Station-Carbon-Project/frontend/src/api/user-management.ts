/**
 * 用户管理 API 接口定义
 * <p>字段与后端 UserManagementDTO / UserManagementVO 严格对应，使用 camelCase 命名</p>
 */

import request from "@/utils/request";
import { ApiResult } from "@/utils/request";

// ======================== 类型定义（与后端 VO/DTO 对应） ========================

/** 用户管理信息（对应 UserManagementVO） */
export interface UserManagementVO {
  id: number;
  userName: string;
  realName: string;
  gender: number;
  phone: string;
  email: string;
  department: string;
  position: string;
  status: number;
  remark: string;
  roleId: number;
  roleName: string;
  createTime: string;
  updateTime: string;
}

/** 用户管理创建/编辑参数（对应 UserManagementDTO） */
export interface UserManagementDTO {
  userName: string;
  realName?: string;
  gender?: number;
  phone?: string;
  email?: string;
  department?: string;
  position?: string;
  status?: number;
  remark?: string;
  roleId?: number;
}

/** 分页查询参数 */
export interface UserManagementPageParams {
  page: number;
  pageSize: number;
  userName?: string;
  realName?: string;
  department?: string;
  status?: number;
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
 * 分页查询用户管理列表
 */
export function getUserManagementPage(params: UserManagementPageParams) {
  return request.get<any, ApiResult<PageResult<UserManagementVO>>>(
    "/v1/user-management/page",
    { params }
  );
}

/**
 * 根据 ID 查询用户管理详情
 */
export function getUserManagementById(id: number) {
  return request.get<any, ApiResult<UserManagementVO>>(
    `/v1/user-management/${id}`
  );
}

/**
 * 创建用户管理记录
 */
export function createUserManagement(data: UserManagementDTO) {
  return request.post<any, ApiResult<UserManagementVO>>(
    "/v1/user-management",
    data
  );
}

/**
 * 更新用户管理记录
 */
export function updateUserManagement(id: number, data: UserManagementDTO) {
  return request.put<any, ApiResult<UserManagementVO>>(
    `/v1/user-management/${id}`,
    data
  );
}

/**
 * 删除用户管理记录
 */
export function deleteUserManagement(id: number) {
  return request.delete<any, ApiResult<null>>(`/v1/user-management/${id}`);
}
