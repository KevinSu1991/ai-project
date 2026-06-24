-- ============================================
-- Power-Station-Carbon 数据库初始化脚本
-- 适用：MySQL 8.0+
-- 描述：管理系统核心表结构
-- ============================================

-- 创建数据库（如不存在）
CREATE DATABASE IF NOT EXISTS carbon_db
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_unicode_ci;

USE carbon_db;

-- ----------------------------
-- 1. 系统角色表 (sys_role)
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '角色ID（主键）',
    `role_name`   VARCHAR(64)  NOT NULL                COMMENT '角色名称',
    `role_key`    VARCHAR(64)  NOT NULL                COMMENT '角色标识（权限判断用）',
    `description` VARCHAR(255) DEFAULT NULL            COMMENT '角色描述',
    `create_time` DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_role_key` (`role_key`) USING BTREE COMMENT '角色标识唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统角色表';

-- ----------------------------
-- 2. 系统用户表 (sys_user)
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '用户ID（主键）',
    `username`    VARCHAR(64)  NOT NULL                COMMENT '用户名（登录账号）',
    `password`    VARCHAR(255) NOT NULL                COMMENT '密码（BCrypt 加密存储）',
    `nickname`    VARCHAR(64)  DEFAULT NULL            COMMENT '昵称（显示名称）',
    `role_id`     BIGINT       DEFAULT NULL            COMMENT '角色ID（关联 sys_role.id）',
    `create_time` DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_username` (`username`) USING BTREE COMMENT '用户名唯一索引',
    KEY `idx_role_id` (`role_id`) USING BTREE          COMMENT '角色ID索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统用户表';

-- ----------------------------
-- 3. 系统权限菜单表 (sys_menu)
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '菜单ID（主键）',
    `parent_id`   BIGINT       NOT NULL DEFAULT 0      COMMENT '父菜单ID（0=顶级菜单）',
    `menu_name`   VARCHAR(64)  NOT NULL                COMMENT '菜单名称',
    `path`        VARCHAR(128) DEFAULT NULL            COMMENT '前端路由路径',
    `component`   VARCHAR(128) DEFAULT NULL            COMMENT '前端组件路径',
    `create_time` DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_parent_id` (`parent_id`) USING BTREE      COMMENT '父菜单索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统权限菜单表';

-- ----------------------------
-- 4. 初始化种子数据
-- ----------------------------
-- 默认角色
INSERT INTO `sys_role` (`role_name`, `role_key`, `description`) VALUES
('超级管理员', 'admin', '系统最高权限，拥有所有菜单和操作权限'),
('普通用户',   'user',  '基础用户，仅可查看和操作部分功能');

-- 默认管理员用户（密码: admin123，BCrypt 加密）
-- 注意: 以下 BCrypt 密文对应明文 "admin123"，生产环境请更换
INSERT INTO `sys_user` (`username`, `password`, `nickname`, `role_id`) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '系统管理员', 1);

-- 默认菜单（两级结构）
INSERT INTO `sys_menu` (`id`, `parent_id`, `menu_name`, `path`, `component`) VALUES
(1,  0, '系统管理', '/system',     NULL),
(2,  1, '用户管理', '/system/user', 'system/user/index'),
(3,  1, '角色管理', '/system/role', 'system/role/index'),
(4,  0, '碳排管理', '/carbon',     NULL),
(5,  4, '数据看板', '/carbon/dashboard', 'carbon/dashboard/index'),
(6,  4, '排放记录', '/carbon/records',   'carbon/records/index');
