package com.vanda.vRbac.mapper;

import java.util.List;
import java.util.Map;

import com.vanda.vRbac.mapper.provider.DictionaryProvider;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;


@Mapper
public interface DictionaryMapper {
	
	/**
	 * 插入数据字典记录
	 * @param dictionary
	 * @return
	 */
	@Insert("INSERT INTO tb_basic_dictionary (dictionary_name, dictionary_value, dictionary_type_code, dictionary_type_name) VALUES (#{dictionary_name}, #{dictionary_value},#{dictionary_type_code}, #{dictionary_type_name})")
	@Options(useGeneratedKeys=true,keyProperty="dictionary_id")
	int insert(Map<String, Object> dictionary);
	
	/**
	 * 查询所有的数据字典
	 * @return
	 */
	@Select("SELECT * FROM tb_basic_dictionary")
	List<Map<String, Object>> findAll();
	
	/**
	 * 通过条件判断，返回总记录
	 * @param condition
	 * @return
	 */
	@SelectProvider(type= DictionaryProvider.class,method="countByCondition")
	int countByCondition(Map<String, Object> condition);
	
	/**
	 * 条件查询
	 * @param condition
	 * @return
	 */
	@SelectProvider(type=DictionaryProvider.class,method="findByCondition")
	List<Map<String, Object>> findByCondition(@Param("condition") Map<String, Object> condition, @Param("start") int start, @Param("size") int size);
	
	/**
	 * 通过编号查询数据字典
	 * @param dictionaryId
	 * @return
	 */
	@Select("SELECT * FROM tb_basic_dictionary WHERE dictionary_id=#{dictionaryId}")
	Map<String, Object> findById(Object dictionaryId);
	
	/**
	 * 更新数据字典的非空字段
	 * @param
	 * @return
	 */
	@UpdateProvider(type=DictionaryProvider.class,method="updateForNotNull")
	int updateForNotNull(Map<String, Object> dictionary);
	
	/**
	 * 根据编号的数组，删除数据字典
	 * @param dictionaryIds
	 * @return
	 */
	@DeleteProvider(type=DictionaryProvider.class,method="delteByIds")
	int delteByIds(Object... dictionaryIds);
	
	/**
	 * 通过类型编码和字典值，查询唯一的字典记录
	 * @param value
	 * @param code
	 * @return
	 */
	@Select("SELECT * FROM tb_basic_dictionary WHERE dictionary_value =#{value} and dictionary_type_code=#{code}")
	Map<String, Object> findByValueAndTypeCode(@Param("value") Object value, @Param("code") Object code);


}
