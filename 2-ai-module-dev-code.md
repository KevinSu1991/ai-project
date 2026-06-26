# AI 全栈模块生成指令 (Generic Module Generator)

## 1. 任务背景
你是一个高级全栈工程师。当前任务是根据指定的“设计稿图片”和“模块名称”，在已有的工程结构中自动化生成完整的业务模块代码。

## 2. 输入参数 (Input Context)
- **目标模块名称**: {{MODULE_NAME}} (例如: user-management)
- **设计稿路径**: `frontend-design/{{MODULE_NAME}}/DESIGN-{{MODULE_NAME}}.png`
- **当前工程基础**: 
  - 后端：Java 17, Spring Boot 3, MyBatis Plus, MySQL
  - 前端：React 18, Ant Design 5.x, TypeScript, Axios

## 3. 第一阶段：视觉分析与建模 (Visual Analysis)
在编写代码前，请先深度解析设计稿图片：
1. **识别字段**: 提取页面中所有的表单项、表格列名、搜索条件、弹窗字段。
2. **定义元数据**: 
   - 确定字段类型 (String, Integer, Datetime, Boolean)。
   - 确定字段命名（严格遵循小驼峰 `camelCase`）。
   - 识别交互逻辑（点击哪个按钮打开 Modal，哪个按钮触发搜索）。

## 4. 第二阶段：数据库与后端生成 (Backend Generation)
请在 `backend/` 目录下生成以下内容：
1. **SQL**: 生成 `sys_{{MODULE_NAME}}` 表的 DDL 语句。
2. **Entity**: 生成 PO 类，必须包含 `id`, `create_time`, `update_time` 等基础字段。
3. **DTO/VO**: 根据设计稿字段生成输入/输出对象，确保字段对齐。
4. **Mapper**: 生成 MyBatis Plus Mapper 接口及 XML。
5. **Service**: 生成业务逻辑接口及实现类。
6. **Controller**: 生成 RESTful API 接口，路径统一为 `/api/v1/{{MODULE_NAME}}`。
   - **注意**: 必须使用之前定义的 `Result<T>` 统一返回格式。

## 5. 第三阶段：前端页面生成 (Frontend Generation)
请在 `frontend/` 目录下生成以下内容：
1. **API 定义**: 在 `src/api/` 下新建 `{{MODULE_NAME}}.ts`，封装所有 Axios 请求。
2. **类型定义**: 在 `src/api/types.ts` 中增加该模块的 TypeScript Interface。
3. **主页面**: 在 `src/pages/{{MODULE_NAME}}/` 下生成 `index.tsx`。
   - 使用 **Ant Design ProTable** 实现搜索、分页和列表展示。
   - 使用 **ProForm** 或 **Modal + Form** 实现新增和编辑功能。
   - 样式需高度还原设计稿。

## 6. 开发规范限制 (Strict Rules)
1. **命名一致性**: 
   - 数据库：`user_name` (snake_case)
   - 后端/前端：`userName` (camelCase)
2. **零人工介入**: 生成的代码必须是完整的，禁止使用 `// TODO` 或 `此处省略`。
3. **错误处理**: 后端需包含全局异常捕获，前端需包含请求失败的 `message.error` 提示。
4. **代码注释**: 关键业务逻辑必须附带中文注释。

## 7. 输出要求
请直接按文件路径列出所有需要新建或修改的文件内容。