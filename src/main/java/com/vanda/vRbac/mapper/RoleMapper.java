package com.vanda.vRbac.mapper;

import java.util.List;
import java.util.Map;

import com.vanda.vRbac.mapper.provider.RoleProvider;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;


@Mapper
public interface RoleMapper {
	
	/**
	 * 插入角色记录
	 * @param role
	 * @return
	 */
	@Insert("INSERT INTO tb_basic_role (role_name, role_permissions) VALUES (#{role_name}, #{role_permissions})")
	@Options(useGeneratedKeys=true,keyProperty="role_id")
	int insert(Map<String, Object> role);
	
	/**
	 * 查询所有的角色
	 * @return
	 */
	@Select("SELECT * FROM tb_basic_role")
	List<Map<String, Object>> findAll();
	
	/**
	 * 通过条件判断，返回总记录
	 * @param condition
	 * @return
	 */
	@SelectProvider(type= RoleProvider.class,method="countByCondition")
	int countByCondition(Map<String, Object> condition);
	
	/**
	 * 条件查询
	 * @param condition
	 * @return
	 */
	@SelectProvider(type=RoleProvider.class,method="findByCondition")
	List<Map<String, Object>> findByCondition(@Param("condition") Map<String, Object> condition, @Param("start") int start, @Param("size") int size);
	
	/**
	 * 通过编号查询角色
	 * @param roleId
	 * @return
	 */
	@Select("SELECT * FROM tb_basic_role WHERE role_id=#{roleId}")
	Map<String, Object> findById(Object roleId);
	
	/**
	 * 更新角色的非空字段
	 * @param user
	 * @return
	 */
	@UpdateProvider(type=RoleProvider.class,method="updateForNotNull")
	int updateForNotNull(Map<String, Object> role);
	
	/**
	 * 根据编号的数组，删除角色
	 * @param roleIds
	 * @return
	 */
	@DeleteProvider(type=RoleProvider.class,method="delteByIds")
	int delteByIds(Object... roleIds);


}
