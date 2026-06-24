import React, { useRef, useState } from "react";
import { ProTable } from "@ant-design/pro-components";
import type { ProColumns, ActionType } from "@ant-design/pro-components";
import { Button, Modal, Form, Input, Select, Space, message, Popconfirm } from "antd";
import { PlusOutlined } from "@ant-design/icons";
import {
  getUserPage,
  createUser,
  updateUser,
  deleteUser,
  UserVO,
  UserCreateDTO,
  UserUpdateDTO,
} from "@/api/user";
import { getRoleList, RoleVO } from "@/api/role";

/**
 * 用户管理页面
 * <p>使用 Ant Design ProTable 实现分页、搜索、增删改查</p>
 * <p>所有字段命名采用 camelCase，与后端 VO/DTO 保持一致</p>
 */
const UserList: React.FC = () => {
  /** ProTable action 引用（用于刷新表格） */
  const actionRef = useRef<ActionType>();

  /** 弹窗状态 */
  const [modalOpen, setModalOpen] = useState(false);
  /** 编辑中的用户（null 表示新增模式） */
  const [editingUser, setEditingUser] = useState<UserVO | null>(null);
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
  const openModal = async (user?: UserVO) => {
    await loadRoleOptions();

    if (user) {
      // 编辑模式：回填表单
      setEditingUser(user);
      form.setFieldsValue({
        username: user.username,
        nickname: user.nickname,
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

      if (editingUser) {
        // 编辑
        const params: UserUpdateDTO = {
          id: editingUser.id,
          ...values,
        };
        await updateUser(params);
        message.success("更新成功");
      } else {
        // 新增
        const params: UserCreateDTO = {
          ...values,
        };
        await createUser(params);
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
    await deleteUser(id);
    message.success("删除成功");
    actionRef.current?.reload();
  };

  /** ProTable 列定义（字段名与后端 UserVO 对应） */
  const columns: ProColumns<UserVO>[] = [
    {
      title: "ID",
      dataIndex: "id",
      width: 80,
      search: false,
    },
    {
      title: "用户名",
      dataIndex: "username",
      // 支持模糊搜索
      fieldProps: {
        placeholder: "请输入用户名搜索",
      },
    },
    {
      title: "昵称",
      dataIndex: "nickname",
      search: false,
    },
    {
      title: "角色",
      dataIndex: "roleName",
      search: false,
      // 角色名称由后端 join 后返回
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
      <ProTable<UserVO>
        headerTitle="用户列表"
        actionRef={actionRef}
        rowKey="id"
        // 分页/搜索请求
        request={async (params) => {
          const { current, pageSize, username } = params;
          const res = await getUserPage({
            page: current || 1,
            pageSize: pageSize || 10,
            username,
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
        }}
        pagination={{
          defaultPageSize: 10,
          showSizeChanger: true,
          pageSizeOptions: ["10", "20", "50"],
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
      >
        <Form form={form} layout="vertical" style={{ marginTop: 16 }}>
          <Form.Item
            name="username"
            label="用户名"
            rules={[
              { required: true, message: "请输入用户名" },
              { min: 4, max: 20, message: "用户名长度需在 4-20 之间" },
            ]}
          >
            <Input placeholder="请输入用户名" disabled={!!editingUser} />
          </Form.Item>

          {/* 新增模式才显示密码 */}
          {!editingUser && (
            <Form.Item
              name="password"
              label="密码"
              rules={[
                { required: true, message: "请输入密码" },
                { min: 6, max: 20, message: "密码长度需在 6-20 之间" },
              ]}
            >
              <Input.Password placeholder="请输入密码" />
            </Form.Item>
          )}

          <Form.Item name="nickname" label="昵称">
            <Input placeholder="请输入昵称" />
          </Form.Item>

          <Form.Item
            name="roleId"
            label="角色"
            rules={[{ required: true, message: "请选择角色" }]}
          >
            <Select placeholder="请选择角色" options={roleOptions} />
          </Form.Item>
        </Form>
      </Modal>
    </>
  );
};

export default UserList;
