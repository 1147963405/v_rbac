package com.vanda.vRbac.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vanda.vRbac.mapper.*;
import com.vanda.vRbac.service.UserService;
import com.vanda.vRbac.utils.Md5Utils;
import com.vanda.vRbac.utils.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
	
	private static final Logger LOGGER= LogManager.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private DictionaryMapper dictionaryMapper;
	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private PermissionMapper permissionMapper;
	
	@Autowired
	private ModularMapper modularMapper;

	@Override
	public Map<String, Object> loginUser(Map<String, Object> user) {
		//第一步：通过数据库，传入账号名查询用户信息
		Map<String, Object> resultUser = userMapper.findByUserAccount(user.get("user_account"));
		//第二步：判断返回的用户信息是否为null
		if (resultUser!=null) {
			//第三步：通过返回的信息获得密码，对比密码是否一致
			//对比密文的时候，必须要将表单传递过来的明文先加密再对比
			String md5Password = Md5Utils.md5(user.get("user_password"), resultUser.get("user_salt"));
			LOGGER.debug("盐："+resultUser.get("user_salt")+",明文:"+user.get("user_password")+",密文："+md5Password);
			if (md5Password.equals(resultUser.get("user_password"))) {
				//1.角色信息
				Object roleId = resultUser.get("role_id");
				Map<String, Object> role = roleMapper.findById(roleId);
				
				//2.角色的权限信息
				String rolePermissionsStr = (String)role.get("role_permissions");
				List<Map<String, Object>> permissions = permissionMapper.findByIds((Object[])rolePermissionsStr.split(","));
				 
				
				//3.权限的模块信息（去除重复的模块） 
				List<Map<String, Object>> modulars=new ArrayList<>();
				
				for (Map<String, Object> permission : permissions) {
					Object modularId = permission.get("modular_id");
				
					
					//每次增加记录到modulars集合，先判断是否有该模块
					//判断是否modulars是否有当前查询的模块了。false表示没有，true表示已经存在
					boolean flag=false;
					for (Map<String, Object> m : modulars) {
						//如果当前权限查询的模块编号与集合里面的模块编号相同，表示已存在
						if (m.get("modular_id")==modularId) {
							flag=true;
							break;
						}
					}
					//modulars不存在该模块才增加
					if (flag==false) {
						Map<String, Object> modular = modularMapper.findById(modularId);
						modulars.add(modular);
					}
				
				}
				role.put("permissions", permissions);
				resultUser.put("role", role);
				resultUser.put("modulars", modulars);
				
				LOGGER.debug(resultUser);
				
				return resultUser;
			}
			
		}
		//第四步：返回。用户或者密码不正确都是返回null
		return null;
	}

	@Override
	public Map<String, Object> editPassword(Map<String, Object> user) {
		//构建更新的条件
		Map<String, Object> condition=new HashMap<>();
		
		condition.put("user_password", user.get("user_password"));
		condition.put("user_salt", user.get("user_salt"));
		condition.put("user_id", user.get("user_id"));
		//更新密码
		int count = userMapper.updateForNotNull(condition);
		if (count>0) {
			return user;
		}
		return null;
	}
	
	@Override
	public List<Map<String, Object>> findAllUser() {
		return userMapper.findAll();
	}

	@Override
	public Page findUserByConditionToPage(Map<String, Object> condition, int index, int size) {
		//获得总记录数
		int count=userMapper.countByCondition(condition);
		//获得当前页面的数据
		//数据库索引=分页索引-1
		int start=(index-1)*size;
		List<Map<String, Object>> data=userMapper.findByCondition(condition, start, size);
		//根据显示的列表，拼接多个的数据
		for (Map<String, Object> user : data) {
			//基于map存储数据建议规则：如果是字段值，直接覆盖原来的值就可以，如果是外键表，存对象
			//将状态值放在user里面
			Map<String, Object> dic = dictionaryMapper.findByValueAndTypeCode(user.get("user_status"), "100001");
			user.put("user_status", dic.get("dictionary_name"));
			
			//通过用户编号查询角色
			Object roleId=user.get("role_id");
			Map<String, Object> role = roleMapper.findById(roleId);
			user.put("role", role);
			
		}
		
		//创建一个分页对象，
		Page page=new Page(index, count, size, data)  ;
		return page;
	}

	@Transactional
	@Override
	public Map<String, Object> addUser(Map<String, Object> user) {
		int count = userMapper.insert(user);
		if (count>0) {
			return user;
		}
		return null;
	}

	@Override
	public Map<String, Object> findUserById(Object userId) {
		return userMapper.findById(userId);
	}

	@Override
	public Map<String, Object> editUser(Map<String, Object> user) {
		int count= userMapper.updateForNotNull(user);
		if (count>0) {
			return user;
		}
		return null;
	}

	@Override
	public int deleteUserByIds(Object... userIds) {
		return userMapper.delteByIds(userIds);
	}


}
