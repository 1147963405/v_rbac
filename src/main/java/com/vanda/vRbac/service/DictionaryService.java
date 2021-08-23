package com.vanda.vRbac.service;

import com.vanda.vRbac.utils.Page;

import java.util.List;
import java.util.Map;


public interface DictionaryService {
	
	/**
	 * 增加数据字典
	 * @param dictionary
	 * @return
	 */
	Map<String, Object> addDictionary(Map<String, Object> dictionary);

	/**
	 * 查询所有的数据字典
	 * @return
	 */
	List<Map<String, Object>> findAllDictionary();
	
	/**
	 * 分页查询
	 * @param condition 分页的条件
	 * @param index 分页索引
	 * @param size 每页记录数
	 * @return
	 */
	Page findDictionaryByConditionToPage(Map<String, Object> condition, int index, int size);
	
	/**
	 * 通过编号查询数据字典
	 * @param dictionaryId
	 * @return
	 */
	Map<String, Object> findDictionaryById(Object dictionaryId);
	
	/**
	 * 编辑数据字典
	 * @param dictionary
	 * @return
	 */
	Map<String, Object> editDictionary(Map<String, Object> dictionary);
	
	/**
	 * 通过编号数组删除多个数据字典
	 * @param dictionaryIds
	 * @return
	 */
	int deleteDictionaryByIds(Object... dictionaryIds);

}
