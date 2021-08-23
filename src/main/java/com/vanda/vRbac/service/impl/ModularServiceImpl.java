package com.vanda.vRbac.service.impl;

import java.util.List;
import java.util.Map;


import com.vanda.vRbac.mapper.ModularMapper;
import com.vanda.vRbac.service.ModularService;
import com.vanda.vRbac.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ModularServiceImpl implements ModularService {
	
	@Autowired
	private ModularMapper modularMapper;

	@Override
	public List<Map<String, Object>> findAllModular() {
		return modularMapper.findAll();
	}

	@Override
	public Page findModularByConditionToPage(Map<String, Object> condition, int index, int size) {
		//获得总记录数
		int count=modularMapper.countByCondition(condition);
		//获得当前页面的数据
		//数据库索引=分页索引-1
		int start=(index-1)*size;
		List<Map<String, Object>> data=modularMapper.findByCondition(condition, start, size);
		//创建一个分页对象，
		Page page=new Page(index, count, size, data)  ;
		return page;
	}

	@Transactional
	@Override
	public Map<String, Object> addModular(Map<String, Object> modular) {
		int count = modularMapper.insert(modular);
		if (count>0) {
			return modular;
		}
		return null;
	}

	@Override
	public Map<String, Object> findModularById(Object modularId) {
		return modularMapper.findById(modularId);
	}

	@Override
	public Map<String, Object> editModular(Map<String, Object> modular) {
		int count= modularMapper.updateForNotNull(modular);
		if (count>0) {
			return modular;
		}
		return null;
	}

	@Override
	public int deleteModularByIds(Object... modularIds) {
		return modularMapper.delteByIds(modularIds);
	}

}
