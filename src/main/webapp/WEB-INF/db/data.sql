-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        10.1.22-MariaDB - mariadb.org binary distribution
-- 服务器操作系统:                      Win32
-- HeidiSQL 版本:                  9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- 导出 rbac-0225 的数据库结构
DROP DATABASE IF EXISTS `rbac-0225`;
CREATE DATABASE IF NOT EXISTS `rbac-0225` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `rbac-0225`;

-- 导出  表 rbac-0225.tb_basic_dictionary 结构
DROP TABLE IF EXISTS `tb_basic_dictionary`;
CREATE TABLE IF NOT EXISTS `tb_basic_dictionary` (
  `dictionary_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典编号',
  `dictionary_name` varchar(100) DEFAULT NULL COMMENT '数据字典名称',
  `dictionary_value` varchar(200) DEFAULT NULL COMMENT '字典值',
  `dictionary_type_code` int(11) DEFAULT NULL COMMENT '字典类型编码',
  `dictionary_type_name` varchar(200) DEFAULT NULL COMMENT '数据字典类型名称',
  PRIMARY KEY (`dictionary_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COMMENT='数据字典表';

-- 正在导出表  rbac-0225.tb_basic_dictionary 的数据：~4 rows (大约)
/*!40000 ALTER TABLE `tb_basic_dictionary` DISABLE KEYS */;
INSERT INTO `tb_basic_dictionary` (`dictionary_id`, `dictionary_name`, `dictionary_value`, `dictionary_type_code`, `dictionary_type_name`) VALUES
	(1, '可用', '0', 100001, '用户状态'),
	(2, '禁用', '1', 100001, '用户状态'),
	(3, '显示', '0', 100002, '权限显示状态'),
	(4, '隐藏', '1', 100002, '权限显示状态');
/*!40000 ALTER TABLE `tb_basic_dictionary` ENABLE KEYS */;

-- 导出  表 rbac-0225.tb_basic_modular 结构
DROP TABLE IF EXISTS `tb_basic_modular`;
CREATE TABLE IF NOT EXISTS `tb_basic_modular` (
  `modular_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `modular_name` varchar(50) DEFAULT NULL,
  `modular_sort` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`modular_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- 正在导出表  rbac-0225.tb_basic_modular 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `tb_basic_modular` DISABLE KEYS */;
INSERT INTO `tb_basic_modular` (`modular_id`, `modular_name`, `modular_sort`) VALUES
	(1, '系统管理模块', '0');
/*!40000 ALTER TABLE `tb_basic_modular` ENABLE KEYS */;

-- 导出  表 rbac-0225.tb_basic_permission 结构
DROP TABLE IF EXISTS `tb_basic_permission`;
CREATE TABLE IF NOT EXISTS `tb_basic_permission` (
  `permission_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `permission_name` varchar(20) DEFAULT NULL,
  `permission_action` varchar(1024) DEFAULT NULL,
  `permission_parent` bigint(20) DEFAULT NULL COMMENT '为0表示顶级权限（菜单）',
  `permission_is_show` int(11) DEFAULT NULL COMMENT '是否显示，0显示，1隐藏',
  `modular_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4;

-- 正在导出表  rbac-0225.tb_basic_permission 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `tb_basic_permission` DISABLE KEYS */;
INSERT INTO `tb_basic_permission` (`permission_id`, `permission_name`, `permission_action`, `permission_parent`, `permission_is_show`, `modular_id`) VALUES
	(1, '用户管理', '/user/toUserList/1', 0, 0, 1),
	(2, '角色管理', '/role/toRoleList/1', 0, 0, 1),
	(3, '模块管理', '/modular/toModularList/1', 0, 0, 1),
	(4, '权限管理', '/permission/toPermissionList/1', 0, 0, 1),
	(5, '数据字典', '/dictionary/toDictionaryList/1', 0, 0, 1),
	(6, '修改密码', '/user/toSetting', 0, 0, 1),
	(7, '用户管理-To增加', '/user/toUserAdd', 1, 0, 1),
	(8, '用户管理-增加', '/user/addUser', 1, 0, 1),
	(9, '用户管理-To编辑', '/user/toUserEdit', 1, 0, 1),
	(10, '用户管理-编辑', '/user/editUser', 1, 0, 1),
	(11, '用户管理-删除', '/user/deleteUserById', 1, 0, 1),
	(12, '用户管理-批量删除', '/user/deleteCheckUsers', 1, 0, 1),
	(13, '角色管理-To增加', '/role/toRoleAdd', 2, 0, 1),
	(14, '角色管理-增加', '/role/addRole', 2, 0, 1),
	(15, '角色管理-To编辑', '/role/toRoleEdit', 2, 0, 1),
	(16, '角色管理-编辑', '/role/editRole', 2, 0, 1),
	(17, '角色管理-删除', '/role/deleteRoleById', 2, 0, 1),
	(18, '角色管理-批量删除', '/role/deleteCheckRoles', 2, 0, 1),
	(19, '模块管理-To增加', '/modular/toModularAdd', 3, 0, 1),
	(20, '模块管理-增加', '/modular/addModular', 3, 0, 1),
	(21, '模块管理-To编辑', '/modular/toModularEdit', 3, 0, 1),
	(22, '模块管理-编辑', '/modular/editModular', 3, 0, 1),
	(23, '模块管理-删除', '/modular/deleteModularById', 3, 0, 1),
	(24, '模块管理-批量删除', '/modular/deleteCheckModulars', 3, 0, 1),
	(25, '权限管理-To增加', '/permission/toPermissionAdd', 4, 0, 1),
	(26, '权限管理-增加', '/permission/addPermission', 4, 0, 1),
	(27, '权限管理-To编辑', '/permission/toPermissionEdit', 4, 0, 1),
	(28, '权限管理-编辑', '/permission/editPermission', 4, 0, 1),
	(29, '权限管理-删除', '/permission/deletePermissionById', 4, 0, 1),
	(30, '权限管理-批量删除', '/permission/deleteCheckPermissions', 4, 0, 1),
	(31, '数据字典-To增加', '/dictionary/toDictionaryAdd', 5, 0, 1),
	(32, '数据字典-增加', '/dictionary/addDictionary', 5, 0, 1),
	(33, '数据字典-To编辑', '/dictionary/toDictionaryEdit', 5, 0, 1),
	(34, '数据字典-编辑', '/dictionary/editDictionary', 5, 0, 1),
	(35, '数据字典-删除', '/dictionary/deleteDictionaryById', 5, 0, 1),
	(36, '数据字典-批量删除', '/dictionary/deleteCheckDictionarys', 5, 0, 1);
/*!40000 ALTER TABLE `tb_basic_permission` ENABLE KEYS */;

-- 导出  表 rbac-0225.tb_basic_role 结构
DROP TABLE IF EXISTS `tb_basic_role`;
CREATE TABLE IF NOT EXISTS `tb_basic_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) DEFAULT NULL,
  `role_permissions` varchar(4000) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- 正在导出表  rbac-0225.tb_basic_role 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `tb_basic_role` DISABLE KEYS */;
INSERT INTO `tb_basic_role` (`role_id`, `role_name`, `role_permissions`) VALUES
	(1, '超级管理员', '1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36');
/*!40000 ALTER TABLE `tb_basic_role` ENABLE KEYS */;

-- 导出  表 rbac-0225.tb_basic_user 结构
DROP TABLE IF EXISTS `tb_basic_user`;
CREATE TABLE IF NOT EXISTS `tb_basic_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) DEFAULT NULL,
  `user_account` varchar(50) DEFAULT NULL,
  `user_password` varchar(200) DEFAULT NULL,
  `user_status` int(11) DEFAULT '0',
  `user_createdate` datetime DEFAULT NULL,
  `user_salt` varchar(128) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_account` (`user_account`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- 正在导出表  rbac-0225.tb_basic_user 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `tb_basic_user` DISABLE KEYS */;
INSERT INTO `tb_basic_user` (`user_id`, `user_name`, `user_account`, `user_password`, `user_status`, `user_createdate`, `user_salt`, `role_id`) VALUES
	(1, '配置管理员', 'root', '1V36yWw/eFXG5xKuOaE6jQ==', 0, '2019-06-25 16:05:54', '9cfde50c-2ce7-492a-9cd5-e55196daa3a5', 1);
/*!40000 ALTER TABLE `tb_basic_user` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
