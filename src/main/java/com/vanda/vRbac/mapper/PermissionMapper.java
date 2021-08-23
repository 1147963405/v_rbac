package com.vanda.vRbac.mapper;

import java.util.List;
import java.util.Map;

import com.vanda.vRbac.mapper.provider.PermissionProvider;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;


@Mapper
public interface PermissionMapper {
	
	/**
	 * 插入权限记录
	 * @param permission
	 * @return
	 */
	@Insert("INSERT INTO tb_basic_permission (permission_name, permission_action, permission_parent, permission_is_show, modular_id) VALUES (#{permission_name}, #{permission_action}, #{permission_parent}, #{permission_is_show}, #{modular_id})")
	@Options(useGeneratedKeys=true,keyProperty="permission_id")
	int insert(Map<String, Object> permission);
	
	/**
	 * 查询所有的权限
	 * @return
	 */
	@Select("SELECT * FROM tb_basic_permission")
	List<Map<String, Object>> findAll();
	
	/**
	 * 通过条件判断，返回总记录
	 * @param condition
	 * @return
	 */
	@SelectProvider(type= PermissionProvider.class,method="countByCondition")
	int countByCondition(Map<String, Object> condition);
	
	/**
	 * 条件查询
	 * @param condition
	 * @return
	 */
	@SelectProvider(type=PermissionProvider.class,method="findByCondition")
	List<Map<String, Object>> findByCondition(@Param("condition") Map<String, Object> condition, @Param("start") int start, @Param("size") int size);
	
	/**
	 * 通过编号查询权限
	 * @param permissionId
	 * @return
	 */
	@Select("SELECT * FROM tb_basic_permission WHERE permission_id=#{permissionId}")
	Map<String, Object> findById(Object permissionId);
	
	/**
	 * 更新权限的非空字段
	 * @param user
	 * @return
	 */
	@UpdateProvider(type=PermissionProvider.class,method="updateForNotNull")
	int updateForNotNull(Map<String, Object> permission);
	
	/**
	 * 根据编号的数组，删除权限
	 * @param permissionIds
	 * @return
	 */
	@DeleteProvider(type=PermissionProvider.class,method="delteByIds")
	int delteByIds(Object... permissionIds);
	
	/**
	 * 通过编号的数组，查询指定的记录
	 * @param permissionIds
	 * @return
	 */
	@SelectProvider(type=PermissionProvider.class,method="findByIds")
	List<Map<String, Object>> findByIds(Object... permissionIds);


}
