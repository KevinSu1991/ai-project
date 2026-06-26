import { Routes, Route } from "react-router-dom";
import MainLayout from "@/layout/MainLayout";
import UserList from "@/pages/user/UserList";
import UserManagement from "@/pages/user-management";

/**
 * 根组件 - 路由配置
 */
function App() {
  return (
    <Routes>
      <Route path="/" element={<MainLayout />}>
        {/* 默认跳转到用户管理 */}
        <Route index element={<UserList />} />
        <Route path="system/user" element={<UserList />} />
        {/* 用户管理模块（新） */}
        <Route path="carbon/user-management" element={<UserManagement />} />
      </Route>
    </Routes>
  );
}

export default App;
