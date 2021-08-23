package com.vanda.vRbac.service.impl;

import java.util.List;
import java.util.Map;

import com.vanda.vRbac.mapper.RoleMapper;
import com.vanda.vRbac.service.RoleService;
import com.vanda.vRbac.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleMapper roleMapper;

	@Override
	public List<Map<String, Object>> findAllRole() {
		return roleMapper.findAll();
	}

	@Override
	public Page findRoleByConditionToPage(Map<String, Object> condition, int index, int size) {
		//获得总记录数
		int count=roleMapper.countByCondition(condition);
		//获得当前页面的数据
		//数据库索引=分页索引-1
		int start=(index-1)*size;
		List<Map<String, Object>> data=roleMapper.findByCondition(condition, start, size);
		//创建一个分页对象，
		Page page=new Page(index, count, size, data)  ;
		return page;
	}

	@Transactional
	@Override
	public Map<String, Object> addRole(Map<String, Object> role) {
		int count = roleMapper.insert(role);
		if (count>0) {
			return role;
		}
		return null;
	}

	@Override
	public Map<String, Object> findRoleById(Object roleId) {
		return roleMapper.findById(roleId);
	}

	@Override
	public Map<String, Object> editRole(Map<String, Object> role) {
		int count= roleMapper.updateForNotNull(role);
		if (count>0) {
			return role;
		}
		return null;
	}

	@Override
	public int deleteRoleByIds(Object... roleIds) {
		return roleMapper.delteByIds(roleIds);
	}

}
