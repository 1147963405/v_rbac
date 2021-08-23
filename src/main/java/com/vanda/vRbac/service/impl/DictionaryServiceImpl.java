package com.vanda.vRbac.service.impl;

import java.util.List;
import java.util.Map;


import com.vanda.vRbac.mapper.DictionaryMapper;
import com.vanda.vRbac.service.DictionaryService;
import com.vanda.vRbac.utils.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class DictionaryServiceImpl implements DictionaryService {
	
	@Autowired
	private DictionaryMapper dictionaryMapper;

	@Override
	public List<Map<String, Object>> findAllDictionary() {
		return dictionaryMapper.findAll();
	}

	@Override
	public Page findDictionaryByConditionToPage(Map<String, Object> condition, int index, int size) {
		//获得总记录数
		int count=dictionaryMapper.countByCondition(condition);
		//获得当前页面的数据
		//数据库索引=分页索引-1
		int start=(index-1)*size;
		List<Map<String, Object>> data=dictionaryMapper.findByCondition(condition, start, size);
		//创建一个分页对象，
		Page page=new Page(index, count, size, data)  ;
		return page;
	}

	@Transactional
	@Override
	public Map<String, Object> addDictionary(Map<String, Object> dictionary) {
		int count = dictionaryMapper.insert(dictionary);
		if (count>0) {
			return dictionary;
		}
		return null;
	}

	@Override
	public Map<String, Object> findDictionaryById(Object dictionaryId) {
		return dictionaryMapper.findById(dictionaryId);
	}

	@Override
	public Map<String, Object> editDictionary(Map<String, Object> dictionary) {
		int count= dictionaryMapper.updateForNotNull(dictionary);
		if (count>0) {
			return dictionary;
		}
		return null;
	}

	@Override
	public int deleteDictionaryByIds(Object... dictionaryIds) {
		return dictionaryMapper.delteByIds(dictionaryIds);
	}

}
