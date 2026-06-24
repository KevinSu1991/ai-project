# 全栈工程初始化指令文档 (V1.0)

## 0. 项目基础信息
- **项目名称**: `Power-Station-Carbon-Project`
- **开发目标**: 构建一个标准的管理系统基础框架，确保前后端字段对齐、架构解耦、代码可维护。

---

## 1. 后端架构规范 (Java Stack)
### 1.1 技术栈要求
- **核心框架**: Spring Boot 3.x
- **权限校验**: Spring Security + JWT
- **持久层**: MyBatis Plus (含自动填充处理)
- **数据库**: MySQL 8.0
- **工具类**: Lombok, MapStruct (用于对象转换)

### 1.2 目录结构定义
请按照以下 Maven 标准结构生成代码：
```text
com.sec.carbon.base
├── common          // 通用模块：常量、工具类、全局异常处理
├── config          // 配置类：MyBatis-Plus配置、Security配置、Swagger/Knife4j
├── controller      // RESTful 接口层
├── entity          // 数据库实体类 (PO)
├── mapper          // MyBatis Mapper 接口及 XML
├── service         // 业务接口
│   └── impl        // 业务实现 (要求使用构造器注入，遵循设计模式)
└── model           // 数据传输模型
    ├── dto         // 前端输入模型
    └── vo          // 后端输出模型 (与前端展示对应)
```

### 1.3 核心契约 (API Response)
所有接口必须返回统一的 JSON 格式：
```java
public class Result<T> {
    private Integer code;    // 200成功，500错误
    private String message;
    private T data;
}
```

---

## 2. 前端架构规范 (Web Stack)
### 2.1 技术栈要求
- **框架**: React 18 + TypeScript
- **构建工具**: Vite
- **UI 组件**: Ant Design 5.x
- **状态管理**: Redux (轻量化选择)
- **网络请求**: Axios (需封装拦截器)

### 2.2 目录结构定义
```text
/src
├── api             // 接口定义（字段需与后端 DTO/VO 严格对应）
├── assets          // 静态资源
├── components      // 公共组件
├── hooks           // 自定义 Hooks
├── layout          // 页面布局组件（Sider, Header, Content）
├── pages           // 业务页面
├── store           // 状态管理
└── utils           // 工具函数 (含 Axios 拦截器)
```

### 2.3 交互规范
- 使用 `Ant Design` 的 `Pro-Components`（如 ProTable, ProForm）以提升生成效率。
- 网络请求拦截器需处理 `Result<T>` 的 `code` 逻辑，非200状态码自动触发 `message.error`。

---

## 3. 数据库初始化 (SQL)
请根据下述通用管理需求生成 SQL：
1. **sys_user**: 用户表 (id, username, password, nickname, role_id, create_time, update_time)
2. **sys_role**: 角色表 (id, role_name, role_key, description)
3. **sys_menu**: 权限菜单表 (id, parent_id, menu_name, path, component)

> **AI 指令**: 请输出符合 MySQL 5.7+ 规范的 DDL 语句，包含必要的索引和注释。

---

## 4. AI 执行分步指令 (Execution Steps)

请按照以下顺序生成并输出代码：

### Step 1: 后端工程骨架
- 生成 `pom.xml` 依赖配置。
- 生成全局异常处理器 `GlobalExceptionHandler` 和统一返回类 `Result`。
- 生成数据库表的 Entity 和 MyBatis-Plus Mapper。

### Step 2: 后端业务逻辑
- 为上述三个系统表生成基础的 CRUD Service 和 Controller。
- 要求在 Service 层演示一种**设计模式**（如：在用户注册逻辑中使用策略模式处理不同角色的初始化）。

### Step 3: 前端工程骨架
- 生成 `vite.config.ts` 和 `tsconfig.json`。
- 编写 `src/utils/request.ts` 封装 Axios。

### Step 4: 基础页面生成
- 基于 Ant Design ProTable 生成“用户管理”页面。
- 实现与后端 API 的对接，确保字段命名采用**小驼峰 (camelCase)** 保持前后端一致。

---

## 5. 开发者约束 (Critical)
1. **命名一致性**: 数据库 `user_name` -> 后端 `userName` -> 前端 `userName`。
2. **代码注释**: 所有类、关键方法、前端组件必须包含中文 JSDoc 或 JavaDoc 注释。
3. **安全性**: 数据库密码、JWT 密钥等敏感信息需放在 `application-dev.yml` 配置文件中。

---