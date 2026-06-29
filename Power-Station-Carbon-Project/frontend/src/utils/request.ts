/**
 * Axios 请求封装
 * <p>统一拦截请求和响应，处理 Result&lt;T&gt; 的 code 逻辑</p>
 * <p>非 200 状态码自动触发 message.error 提示</p>
 * <p>TODO: 当前已注释掉权限认证跳转逻辑，开发调试阶段使用</p>
 */

import axios, { AxiosInstance, AxiosRequestConfig, InternalAxiosRequestConfig } from "axios";
import { message } from "antd";

/** 后端统一响应结构（与后端 Result<T> 对应） */
export interface ApiResult<T = unknown> {
  code: number;
  message: string;
  data: T;
}

/** 创建 Axios 实例 */
const request: AxiosInstance = axios.create({
  baseURL: "/api", // 统一前缀，Vite proxy 会转发到 localhost:8080
  timeout: 15000, // 15秒超时
  headers: {
    "Content-Type": "application/json",
  },
});

/**
 * 请求拦截器
 * <p>在此处添加 Token 等公共请求头</p>
 * <p>TODO: 当前已注释 Token 注入，开发调试阶段使用</p>
 */
request.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    // TODO: 已注释掉 Token 认证，开发调试阶段使用
    // const token = localStorage.getItem("token");
    // if (token && config.headers) {
    //   config.headers.Authorization = `Bearer ${token}`;
    // }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

/**
 * 响应拦截器
 * <p>统一处理 Result.code：非 200 状态码自动弹出错误提示</p>
 */
request.interceptors.response.use(
  (response) => {
    const res = response.data as ApiResult;
    // 200 表示成功，直接返回 data 字段
    if (res.code === 200) {
      return response.data;
    }
    // 非 200 状态码统一弹窗报错
    message.error(res.message || "请求失败");
    return Promise.reject(new Error(res.message || "请求失败"));
  },
  (error) => {
    // HTTP 错误（如 401、500）统一处理
    if (error.response) {
      const status = error.response.status;
      switch (status) {
        case 401:
          // TODO: 已注释掉登录页跳转，开发调试阶段使用
          // message.error("未登录，请重新登录");
          // 可在此处跳转登录页
          console.warn("401 Unauthorized - auth disabled, skipping login redirect");
          break;
        case 403:
          message.error("权限不足");
          break;
        case 404:
          message.error("请求的资源不存在");
          break;
        case 500:
          message.error("服务器内部错误");
          break;
        default:
          message.error(`请求失败 (${status})`);
      }
    } else if (error.message.includes("timeout")) {
      message.error("请求超时，请稍后重试");
    } else {
      message.error("网络异常，请检查网络连接");
    }
    return Promise.reject(error);
  }
);

export default request;
