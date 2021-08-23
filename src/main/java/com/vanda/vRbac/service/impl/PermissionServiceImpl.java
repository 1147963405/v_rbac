package com.vanda.vRbac.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.vanda.vRbac.mapper.DictionaryMapper;
import com.vanda.vRbac.mapper.ModularMapper;
import com.vanda.vRbac.mapper.PermissionMapper;
import com.vanda.vRbac.service.PermissionService;
import com.vanda.vRbac.utils.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class PermissionServiceImpl implements PermissionService {
	@Autowired
	private PermissionMapper permissionMapper;
	
	@Autowired
	private ModularMapper modularMapper;
	@Autowired
	private DictionaryMapper dictionaryMapper;

	@Override
	public List<Map<String, Object>> findAllPermission() {
		return permissionMapper.findAll();
	}

	@Override
	public Page findPermissionByConditionToPage(Map<String, Object> condition, int index, int size) {
		//获得总记录数
		int count=permissionMapper.countByCondition(condition);
		//获得当前页面的数据
		//数据库索引=分页索引-1
		int start=(index-1)*size;
		List<Map<String, Object>> data=permissionMapper.findByCondition(condition, start, size);
		for (Map<String, Object> permission : data) {
			Object modularId = permission.get("modular_id");
			//封装模块对象
			Map<String, Object> modular = modularMapper.findById(modularId);
			permission.put("modular", modular);
			//是否显示
			Map<String, Object> dic = dictionaryMapper.findByValueAndTypeCode(permission.get("permission_is_show"), 100002);
			permission.put("permission_is_show", dic.get("dictionary_name"));
			
			//父权限
			if ((Long)permission.get("permission_parent")==0L) {
				Map<String, Object> parent=new HashMap<>();
				parent.put("permission_id", 0);
				parent.put("permission_name", "菜单");
				permission.put("parent", parent);
			}else {
				Map<String, Object> parent = permissionMapper.findById(permission.get("permission_parent"));
				permission.put("parent", parent);
			}
			
			
		}
		//创建一个分页对象，
		Page page=new Page(index, count, size, data)  ;
		return page;
	}

	@Transactional
	@Override
	public Map<String, Object> addPermission(Map<String, Object> permission) {
		int count = permissionMapper.insert(permission);
		if (count>0) {
			return permission;
		}
		return null;
	}

	@Override
	public Map<String, Object> findPermissionById(Object permissionId) {
		return permissionMapper.findById(permissionId);
	}

	@Override
	public Map<String, Object> editPermission(Map<String, Object> permission) {
		int count= permissionMapper.updateForNotNull(permission);
		if (count>0) {
			return permission;
		}
		return null;
	}

	@Override
	public int deletePermissionByIds(Object... permissionIds) {
		return permissionMapper.delteByIds(permissionIds);
	}

}
