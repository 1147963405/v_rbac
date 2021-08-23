package com.vanda.vRbac.service;

import com.vanda.vRbac.utils.Page;

import java.util.List;
import java.util.Map;


public interface RoleService {
	
	/**
	 * 增加角色
	 * @param role
	 * @return
	 */
	Map<String, Object> addRole(Map<String, Object> role);

	/**
	 * 查询所有的角色
	 * @return
	 */
	List<Map<String, Object>> findAllRole();
	
	/**
	 * 分页查询
	 * @param condition 分页的条件
	 * @param index 分页索引
	 * @param size 每页记录数
	 * @return
	 */
	Page findRoleByConditionToPage(Map<String, Object> condition, int index, int size);
	
	/**
	 * 通过编号查询角色
	 * @param roleId
	 * @return
	 */
	Map<String, Object> findRoleById(Object roleId);
	
	/**
	 * 编辑角色
	 * @param role
	 * @return
	 */
	Map<String, Object> editRole(Map<String, Object> role);
	
	/**
	 * 通过编号数组删除多个角色
	 * @param roleIds
	 * @return
	 */
	int deleteRoleByIds(Object... roleIds);


}
