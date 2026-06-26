import React from "react";
import { Outlet, useNavigate, useLocation } from "react-router-dom";
import { Layout, Menu } from "antd";
import type { MenuProps } from "antd";
import {
  UserOutlined,
  DatabaseOutlined,
  DashboardOutlined,
} from "@ant-design/icons";

const { Header, Sider, Content } = Layout;

/**
 * 主布局组件
 * <p>包含侧边栏（Sider）、顶部栏（Header）和内容区域（Content）</p>
 * <p>侧边栏根据当前路由自动高亮对应菜单项</p>
 */
const MainLayout: React.FC = () => {
  const navigate = useNavigate();
  const location = useLocation();

  /** 菜单配置（与后端 sys_menu 表对应） */
  const menuItems: MenuProps["items"] = [
    {
      key: "/system",
      icon: <DatabaseOutlined />,
      label: "系统管理",
      children: [
        {
          key: "/system/user",
          icon: <UserOutlined />,
          label: "用户管理",
        },
      ],
    },
    {
      key: "/carbon",
      icon: <DashboardOutlined />,
      label: "碳排管理",
      children: [
        {
          key: "/carbon/dashboard",
          icon: <DashboardOutlined />,
          label: "数据看板",
        },
        {
          key: "/carbon/records",
          icon: <DatabaseOutlined />,
          label: "排放记录",
        },
        {
          key: "/carbon/user-management",
          icon: <UserOutlined />,
          label: "用户管理",
        },
      ],
    },
  ];

  /** 菜单点击跳转 */
  const handleMenuClick: MenuProps["onClick"] = ({ key }) => {
    navigate(key);
  };

  return (
    <Layout style={{ minHeight: "100vh" }}>
      {/* 侧边栏 */}
      <Sider collapsible breakpoint="lg" theme="dark">
        <div
          style={{
            height: 64,
            display: "flex",
            alignItems: "center",
            justifyContent: "center",
            color: "#fff",
            fontSize: 18,
            fontWeight: "bold",
            borderBottom: "1px solid rgba(255,255,255,0.1)",
          }}
        >
          电站碳管理
        </div>
        <Menu
          theme="dark"
          mode="inline"
          selectedKeys={[location.pathname]}
          defaultOpenKeys={["/system", "/carbon"]}
          items={menuItems}
          onClick={handleMenuClick}
        />
      </Sider>

      {/* 右侧内容区域 */}
      <Layout>
        <Header
          style={{
            background: "#fff",
            padding: "0 24px",
            borderBottom: "1px solid #f0f0f0",
            display: "flex",
            alignItems: "center",
            fontSize: 16,
          }}
        >
          电站碳管理系统
        </Header>
        <Content
          style={{
            margin: 24,
            padding: 24,
            background: "#fff",
            borderRadius: 8,
            minHeight: 280,
          }}
        >
          <Outlet />
        </Content>
      </Layout>
    </Layout>
  );
};

export default MainLayout;
