package com.vanda.vRbac.mapper;

import java.util.List;
import java.util.Map;

import com.vanda.vRbac.mapper.provider.UserProvider;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

//标量类型=基础数据类型+包装类+String
@Mapper
public interface UserMapper {

	
	/**
	 * 查询所有的用户
	 * @return
	 */
	@Select("SELECT * FROM tb_basic_user")
	List<Map<String, Object>> findAll();
	
	/**
	 * 通过条件判断，返回总记录
	 * @param condition
	 * @return
	 */
	@SelectProvider(type= UserProvider.class,method="countByCondition")
	int countByCondition(Map<String, Object> condition);
	
	/**
	 * 条件查询
	 * @param condition
	 * @return
	 */
	@SelectProvider(type=UserProvider.class,method="findByCondition")
	List<Map<String, Object>> findByCondition(@Param("condition") Map<String, Object> condition, @Param("start") int start, @Param("size") int size);
	
	/**
	 * 通过编号查询用户
	 * @param userId
	 * @return
	 */
	@Select("SELECT * FROM tb_basic_user WHERE user_id=#{userId}")
	Map<String, Object> findById(Object userId);
	

	
	/**
	 * 根据编号的数组，删除用户
	 * @param userIds
	 * @return
	 */
	@DeleteProvider(type=UserProvider.class,method="delteByIds")
	int delteByIds(Object... userIds);

	
	
	@Insert("INSERT INTO tb_basic_user	(user_name, user_account, user_password, user_status,user_salt, user_createdate, role_id)	VALUES (#{user_name}, #{user_account}, #{user_password}, #{user_status},#{user_salt}, #{user_createdate}, #{role_id})")
	@Options(useGeneratedKeys=true,keyProperty="user_id")
	int insert(Map<String, Object> user);
	

	/**
	 * 通过账号名查询用户信息
	 * @param account
	 * @return
	 */
	@Select("SELECT * FROM tb_basic_user WHERE user_account=#{account}")
	Map<String, Object> findByUserAccount(Object account);
	
	/**
	 * 更新用户的非空字段
	 * @param user
	 * @return
	 */
	@UpdateProvider(type=UserProvider.class,method="updateForNotNull")
	int updateForNotNull(Map<String, Object> user);
	

}
