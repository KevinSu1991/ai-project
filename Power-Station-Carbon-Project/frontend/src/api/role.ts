/**
 * 角色管理 API 接口定义
 */

import request from "@/utils/request";
import { ApiResult } from "@/utils/request";

/** 角色信息（对应 RoleVO） */
export interface RoleVO {
  id: number;
  roleName: string;
  roleKey: string;
  description: string;
  createTime: string;
}

/**
 * 获取所有角色列表
 */
export function getRoleList() {
  return request.get<any, ApiResult<RoleVO[]>>("/sys/role/list");
}
