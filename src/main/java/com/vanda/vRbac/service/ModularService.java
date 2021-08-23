package com.vanda.vRbac.service;

import com.vanda.vRbac.utils.Page;

import java.util.List;
import java.util.Map;


public interface ModularService {
	
	/**
	 * 增加模块
	 * @param modular
	 * @return
	 */
	Map<String, Object> addModular(Map<String, Object> modular);

	/**
	 * 查询所有的模块
	 * @return
	 */
	List<Map<String, Object>> findAllModular();
	
	/**
	 * 分页查询
	 * @param condition 分页的条件
	 * @param index 分页索引
	 * @param size 每页记录数
	 * @return
	 */
	Page findModularByConditionToPage(Map<String, Object> condition, int index, int size);
	
	/**
	 * 通过编号查询模块
	 * @param modularId
	 * @return
	 */
	Map<String, Object> findModularById(Object modularId);
	
	/**
	 * 编辑模块
	 * @param modular
	 * @return
	 */
	Map<String, Object> editModular(Map<String, Object> modular);
	
	/**
	 * 通过编号数组删除多个模块
	 * @param modularIds
	 * @return
	 */
	int deleteModularByIds(Object... modularIds);

}
