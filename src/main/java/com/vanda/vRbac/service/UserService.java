package com.vanda.vRbac.service;

import com.vanda.vRbac.utils.Page;

import java.util.List;
import java.util.Map;


public interface UserService {
	
	/**
	 * 增加用户
	 * @param user
	 * @return
	 */
	Map<String, Object> addUser(Map<String, Object> user);

	/**
	 * 查询所有的用户
	 * @return
	 */
	List<Map<String, Object>> findAllUser();
	
	/**
	 * 分页查询
	 * @param condition 分页的条件
	 * @param index 分页索引
	 * @param size 每页记录数
	 * @return
	 */
	Page findUserByConditionToPage(Map<String, Object> condition, int index, int size);
	
	/**
	 * 通过编号查询用户
	 * @param userId
	 * @return
	 */
	Map<String, Object> findUserById(Object userId);
	
	/**
	 * 编辑用户
	 * @param user
	 * @return
	 */
	Map<String, Object> editUser(Map<String, Object> user);
	
	/**
	 * 通过编号数组删除多个用户
	 * @param userIds
	 * @return
	 */
	int deleteUserByIds(Object... userIds);

	
	/**
	 * 用户登录
	 * @param user
	 * @return
	 */
	Map<String, Object> loginUser(Map<String, Object> user);
	
	/**
	 * 编辑密码
	 * @param user
	 * @return
	 */
	Map<String, Object> editPassword(Map<String, Object> user);

}
