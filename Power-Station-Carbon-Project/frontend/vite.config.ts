import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";
import path from "path";

/**
 * Vite 构建配置
 *
 * @see https://vitejs.dev/config/
 */
export default defineConfig({
  plugins: [react()],

  // 路径别名
  resolve: {
    alias: {
      "@": path.resolve(__dirname, "src"),
    },
  },

  server: {
    port: 3000,
    // 代理配置：将 /api 请求转发到后端 8080 端口
    proxy: {
      "/api": {
        // target: "http://10.236.20.68:8080",
        target: "http://127.0.0.1:8080",
        changeOrigin: true,
        // 不重写路径，保持 /api 前缀
      },
    },
    // 文件监听配置：忽略 iCloud/spotlight/node_modules 等触发的虚假变更
    watch: {
      usePolling: false,
      ignored: ["**/node_modules/**", "**/.git/**", "**/.DS_Store", "**/*.swp", "**/*~"],
    },
  },

  build: {
    outDir: "dist",
    sourcemap: false,
    // 生产环境去除 console
    minify: "terser",
    terserOptions: {
      compress: {
        drop_console: true,
        drop_debugger: true,
      },
    },
  },
});
