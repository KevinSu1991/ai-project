import React, { useRef, useState } from "react";
import { ProTable } from "@ant-design/pro-components";
import type { ProColumns, ActionType } from "@ant-design/pro-components";
import {
  Button,
  Modal,
  Form,
  Input,
  Select,
  Space,
  Tag,
  message,
  Popconfirm,
} from "antd";
import { PlusOutlined } from "@ant-design/icons";
import {
  getUserManagementPage,
  createUserManagement,
  updateUserManagement,
  deleteUserManagement,
  UserManagementVO,
  UserManagementDTO,
} from "@/api/user-management";
import { getRoleList, RoleVO } from "@/api/role";

/**
 * 用户管理页面
 * <p>基于设计稿还原：使用 Ant Design ProTable 实现搜索、分页和列表展示</p>
 * <p>使用 Modal + Form 实现新增和编辑功能</p>
 * <p>所有字段命名采用 camelCase，与后端 VO/DTO 保持一致</p>
 */
const UserManagement: React.FC = () => {
  /** ProTable action 引用（用于刷新表格） */
  const actionRef = useRef<ActionType>();

  /** 弹窗状态 */
  const [modalOpen, setModalOpen] = useState(false);
  /** 编辑中的用户（null 表示新增模式） */
  const [editingUser, setEditingUser] = useState<UserManagementVO | null>(null);
  /** 角色列表（下拉选项） */
  const [roleOptions, setRoleOptions] = useState<{ label: string; value: number }[]>([]);

  /** Form 实例 */
  const [form] = Form.useForm();

  /**
   * 加载角色列表（用于下拉选择）
   */
  const loadRoleOptions = async () => {
    try {
      const res = await getRoleList();
      const options = (res as any).data.map((role: RoleVO) => ({
        label: role.roleName,
        value: role.id,
      }));
      setRoleOptions(options);
    } catch {
      // 错误在 request 拦截器中已处理
    }
  };

  /**
   * 打开新增/编辑弹窗
   */
  const openModal = async (user?: UserManagementVO) => {
    await loadRoleOptions();

    if (user) {
      // 编辑模式：回填表单
      setEditingUser(user);
      form.setFieldsValue({
        userName: user.userName,
        realName: user.realName,
        gender: user.gender,
        phone: user.phone,
        email: user.email,
        department: user.department,
        position: user.position,
        status: user.status,
        remark: user.remark,
        roleId: user.roleId,
      });
    } else {
      // 新增模式：清空表单
      setEditingUser(null);
      form.resetFields();
    }

    setModalOpen(true);
  };

  /**
   * 提交表单（新增 / 编辑）
   */
  const handleSubmit = async () => {
    try {
      const values = await form.validateFields();

      const params: UserManagementDTO = {
        userName: values.userName,
        realName: values.realName,
        gender: values.gender,
        phone: values.phone,
        email: values.email,
        department: values.department,
        position: values.position,
        status: values.status,
        remark: values.remark,
        roleId: values.roleId,
      };

      if (editingUser) {
        // 编辑
        await updateUserManagement(editingUser.id, params);
        message.success("更新成功");
      } else {
        // 新增
        await createUserManagement(params);
        message.success("创建成功");
      }

      setModalOpen(false);
      actionRef.current?.reload();
    } catch (error) {
      // 表单校验失败或在 request 拦截器中已提示
    }
  };

  /**
   * 删除用户
   */
  const handleDelete = async (id: number) => {
    await deleteUserManagement(id);
    message.success("删除成功");
    actionRef.current?.reload();
  };

  /** 性别映射 */
  const genderMap: Record<number, { text: string; color: string }> = {
    0: { text: "未知", color: "default" },
    1: { text: "男", color: "blue" },
    2: { text: "女", color: "pink" },
  };

  /** ProTable 列定义（字段名与后端 UserManagementVO 对应） */
  const columns: ProColumns<UserManagementVO>[] = [
    {
      title: "ID",
      dataIndex: "id",
      width: 60,
      search: false,
    },
    {
      title: "用户名",
      dataIndex: "userName",
      fieldProps: {
        placeholder: "请输入用户名搜索",
      },
    },
    {
      title: "真实姓名",
      dataIndex: "realName",
      fieldProps: {
        placeholder: "请输入真实姓名搜索",
      },
    },
    {
      title: "性别",
      dataIndex: "gender",
      width: 70,
      search: false,
      render: (_, record) => {
        const info = genderMap[record.gender] || genderMap[0];
        return <Tag color={info.color}>{info.text}</Tag>;
      },
    },
    {
      title: "手机号",
      dataIndex: "phone",
      search: false,
      width: 130,
    },
    {
      title: "邮箱",
      dataIndex: "email",
      search: false,
      width: 200,
      ellipsis: true,
    },
    {
      title: "所属部门",
      dataIndex: "department",
      fieldProps: {
        placeholder: "请输入部门搜索",
      },
      width: 120,
    },
    {
      title: "职位",
      dataIndex: "position",
      search: false,
      width: 120,
    },
    {
      title: "状态",
      dataIndex: "status",
      width: 80,
      valueType: "select",
      valueEnum: {
        0: { text: "禁用", status: "Error" },
        1: { text: "启用", status: "Success" },
      },
    },
    {
      title: "角色",
      dataIndex: "roleName",
      search: false,
      width: 100,
    },
    {
      title: "备注",
      dataIndex: "remark",
      search: false,
      width: 150,
      ellipsis: true,
    },
    {
      title: "创建时间",
      dataIndex: "createTime",
      valueType: "dateTime",
      search: false,
      width: 180,
    },
    {
      title: "更新时间",
      dataIndex: "updateTime",
      valueType: "dateTime",
      search: false,
      width: 180,
    },
    {
      title: "操作",
      valueType: "option",
      width: 180,
      fixed: "right",
      render: (_, record) => (
        <Space>
          <Button type="link" size="small" onClick={() => openModal(record)}>
            编辑
          </Button>
          <Popconfirm
            title="确认删除该用户？"
            description="删除后无法恢复"
            onConfirm={() => handleDelete(record.id)}
            okText="确认"
            cancelText="取消"
          >
            <Button type="link" size="small" danger>
              删除
            </Button>
          </Popconfirm>
        </Space>
      ),
    },
  ];

  return (
    <>
      <ProTable<UserManagementVO>
        headerTitle="用户管理"
        actionRef={actionRef}
        rowKey="id"
        scroll={{ x: 1600 }}
        // 分页/搜索请求
        request={async (params) => {
          const { current, pageSize, userName, realName, department, status } =
            params;
          const res = await getUserManagementPage({
            page: current || 1,
            pageSize: pageSize || 10,
            userName,
            realName,
            department,
            status: status !== undefined ? Number(status) : undefined,
          });
          return {
            data: (res as any).data.records,
            total: (res as any).data.total,
            success: true,
          };
        }}
        columns={columns}
        // 工具栏
        toolBarRender={() => [
          <Button
            key="add"
            type="primary"
            icon={<PlusOutlined />}
            onClick={() => openModal()}
          >
            新增用户
          </Button>,
        ]}
        // 搜索栏配置
        search={{
          labelWidth: "auto",
          defaultCollapsed: false,
        }}
        pagination={{
          defaultPageSize: 10,
          showSizeChanger: true,
          pageSizeOptions: ["10", "20", "50"],
          showTotal: (total) => `共 ${total} 条`,
        }}
      />

      {/* 新增/编辑弹窗 */}
      <Modal
        title={editingUser ? "编辑用户" : "新增用户"}
        open={modalOpen}
        onOk={handleSubmit}
        onCancel={() => setModalOpen(false)}
        destroyOnClose
        okText="确定"
        cancelText="取消"
        width={640}
      >
        <Form form={form} layout="vertical" style={{ marginTop: 16 }}>
          <Form.Item
            name="userName"
            label="用户名"
            rules={[
              { required: true, message: "请输入用户名" },
              { min: 4, max: 20, message: "用户名长度需在 4-20 之间" },
            ]}
          >
            <Input placeholder="请输入用户名" disabled={!!editingUser} />
          </Form.Item>

          <Form.Item
            name="realName"
            label="真实姓名"
          >
            <Input placeholder="请输入真实姓名" />
          </Form.Item>

          <Form.Item
            name="gender"
            label="性别"
          >
            <Select
              placeholder="请选择性别"
              allowClear
              options={[
                { label: "男", value: 1 },
                { label: "女", value: 2 },
                { label: "未知", value: 0 },
              ]}
            />
          </Form.Item>

          <Form.Item
            name="phone"
            label="手机号"
            rules={[
              {
                pattern: /^1[3-9]\d{9}$/,
                message: "手机号格式不正确",
              },
            ]}
          >
            <Input placeholder="请输入手机号" />
          </Form.Item>

          <Form.Item
            name="email"
            label="邮箱"
            rules={[
              {
                type: "email",
                message: "邮箱格式不正确",
              },
            ]}
          >
            <Input placeholder="请输入邮箱" />
          </Form.Item>

          <Form.Item
            name="department"
            label="所属部门"
          >
            <Input placeholder="请输入所属部门" />
          </Form.Item>

          <Form.Item
            name="position"
            label="职位"
          >
            <Input placeholder="请输入职位" />
          </Form.Item>

          <Form.Item
            name="status"
            label="状态"
            initialValue={1}
          >
            <Select
              placeholder="请选择状态"
              options={[
                { label: "启用", value: 1 },
                { label: "禁用", value: 0 },
              ]}
            />
          </Form.Item>

          <Form.Item
            name="roleId"
            label="角色"
            rules={[{ required: true, message: "请选择角色" }]}
          >
            <Select placeholder="请选择角色" options={roleOptions} />
          </Form.Item>

          <Form.Item
            name="remark"
            label="备注"
          >
            <Input.TextArea rows={3} placeholder="请输入备注" maxLength={500} />
          </Form.Item>
        </Form>
      </Modal>
    </>
  );
};

export default UserManagement;
