package com.vanda.vRbac.service;


import com.vanda.vRbac.utils.Page;

import java.util.List;
import java.util.Map;


public interface PermissionService {
	
	/**
	 * 增加权限
	 * @param permission
	 * @return
	 */
	Map<String, Object> addPermission(Map<String, Object> permission);

	/**
	 * 查询所有的权限
	 * @return
	 */
	List<Map<String, Object>> findAllPermission();
	
	/**
	 * 分页查询
	 * @param condition 分页的条件
	 * @param index 分页索引
	 * @param size 每页记录数
	 * @return
	 */
	Page findPermissionByConditionToPage(Map<String, Object> condition, int index, int size);
	
	/**
	 * 通过编号查询权限
	 * @param permissionId
	 * @return
	 */
	Map<String, Object> findPermissionById(Object permissionId);
	
	/**
	 * 编辑权限
	 * @param permission
	 * @return
	 */
	Map<String, Object> editPermission(Map<String, Object> permission);
	
	/**
	 * 通过编号数组删除多个权限
	 * @param permissionIds
	 * @return
	 */
	int deletePermissionByIds(Object... permissionIds);


}
