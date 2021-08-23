SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS tb_basic_dictionary;
DROP TABLE IF EXISTS tb_basic_permission;
DROP TABLE IF EXISTS tb_basic_modular;
DROP TABLE IF EXISTS tb_basic_user;
DROP TABLE IF EXISTS tb_basic_role;




/* Create Tables */

-- 数据字典表
CREATE TABLE tb_basic_dictionary
(
	dictionary_id bigint NOT NULL AUTO_INCREMENT COMMENT '字典编号',
	dictionary_name varchar(100) COMMENT '数据字典名称',
	dictionary_value varchar(200) COMMENT '字典值',
	dictionary_type_code int COMMENT '字典类型编码',
	dictionary_type_name varchar(200) COMMENT '数据字典类型名称',
	PRIMARY KEY (dictionary_id)
) COMMENT = '数据字典表';


-- 模块表
CREATE TABLE tb_basic_modular
(
	modular_id bigint NOT NULL AUTO_INCREMENT COMMENT '模块编号',
	modular_name varchar(50) COMMENT '模块名',
	modular_sort bigint COMMENT '排序序号',
	PRIMARY KEY (modular_id)
) COMMENT = '模块表';


-- 权限表
CREATE TABLE tb_basic_permission
(
	permission_id bigint NOT NULL AUTO_INCREMENT COMMENT '权限编号',
	permission_name varchar(20) COMMENT '权限名',
	permission_action varchar(1024) COMMENT '权限路径',
	-- 为0表示顶级权限（菜单）
	permission_parent bigint COMMENT '父权限',
	-- 是否显示，0显示，1隐藏
	permission_is_show int COMMENT '是否显示',
	modular_id bigint COMMENT '模块编号',
	PRIMARY KEY (permission_id)
) COMMENT = '权限表';


-- 角色表
CREATE TABLE tb_basic_role
(
	role_id bigint NOT NULL AUTO_INCREMENT COMMENT '角色编号',
	role_name varchar(100) COMMENT '角色名',
	role_permissions varchar(4000) COMMENT '角色的权限',
	PRIMARY KEY (role_id)
) COMMENT = '角色表';


-- 用户表
CREATE TABLE tb_basic_user
(
	user_id bigint NOT NULL AUTO_INCREMENT COMMENT '用户编号',
	user_name varchar(50) COMMENT '用户名',
	user_account varchar(50) COMMENT '账号',
	user_password varchar(200) COMMENT '密码',
	user_status int DEFAULT 0 COMMENT '状态，0可用，1禁用',
	user_createdate datetime COMMENT '创建日期',
	user_salt varchar(128) COMMENT '盐值',
	role_id bigint COMMENT '角色编号',
	PRIMARY KEY (user_id)
) COMMENT = '用户表';







