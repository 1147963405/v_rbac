package com.vanda.vRbac.mapper;

import java.util.List;
import java.util.Map;

import com.vanda.vRbac.mapper.provider.ModularProvider;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;


@Mapper
public interface ModularMapper {
	
	/**
	 * 插入模块记录
	 * @param modular
	 * @return
	 */
	@Insert("INSERT INTO tb_basic_modular (modular_name, modular_sort) VALUES (#{modular_name}, #{modular_sort})")
	@Options(useGeneratedKeys=true,keyProperty="modular_id")
	int insert(Map<String, Object> modular);
	
	/**
	 * 查询所有的模块
	 * @return
	 */
	@Select("SELECT * FROM tb_basic_modular")
	List<Map<String, Object>> findAll();
	
	/**
	 * 通过条件判断，返回总记录
	 * @param condition
	 * @return
	 */
	@SelectProvider(type= ModularProvider.class,method="countByCondition")
	int countByCondition(Map<String, Object> condition);
	
	/**
	 * 条件查询
	 * @param condition
	 * @return
	 */
	@SelectProvider(type=ModularProvider.class,method="findByCondition")
	List<Map<String, Object>> findByCondition(@Param("condition") Map<String, Object> condition, @Param("start") int start, @Param("size") int size);
	
	/**
	 * 通过编号查询模块
	 * @param modularId
	 * @return
	 */
	@Select("SELECT * FROM tb_basic_modular WHERE modular_id=#{modularId}")
	Map<String, Object> findById(Object modularId);
	
	/**
	 * 更新模块的非空字段
	 * @param user
	 * @return
	 */
	@UpdateProvider(type=ModularProvider.class,method="updateForNotNull")
	int updateForNotNull(Map<String, Object> modular);
	
	/**
	 * 根据编号的数组，删除模块
	 * @param modularIds
	 * @return
	 */
	@DeleteProvider(type=ModularProvider.class,method="delteByIds")
	int delteByIds(Object... modularIds);

}
