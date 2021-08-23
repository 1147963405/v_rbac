package com.vanda.vRbac.mapper.provider;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class DictionaryProvider {
	
	private static final Logger LOGGER= LogManager.getLogger(DictionaryProvider.class);

	public String countByCondition(Map<String, Object> condition) {
		// 第一步：声明公共的SQL语句
		String sql = "SELECT count(1) FROM tb_basic_dictionary WHERE 1=1 ";
		// 第二步：构建动态SQL语句
		StringBuilder builder = new StringBuilder(sql);
		if (condition != null) {

			if (condition.get("dictionary_name") != null) {
				builder.append("AND dictionary_name like CONCAT('%',#{dictionary_name},'%')");
			}
			
			if (condition.get("dictionary_type_name") != null) {
				builder.append("AND dictionary_type_name like CONCAT('%',#{dictionary_type_name},'%')");
			}
			
		}

		// 第三步：返回SQL语句
		return builder.toString();
	}

	public String findByCondition(@Param("condition") Map<String, Object> condition, @Param("start") int start,
			@Param("size") int size) {
		// 第一步：声明公共的SQL语句
		String sql = "SELECT * FROM tb_basic_dictionary WHERE 1=1 ";
		// 第二步：构建动态SQL语句
		StringBuilder builder = new StringBuilder(sql);
		if (condition != null) {
			if (condition.get("dictionary_name") != null) {
				//注意：因为参数绑定的对象名为condition，所以属性需要通过对象名来访问
				builder.append("AND dictionary_name like CONCAT('%',#{condition.dictionary_name},'%') ");
			}
			
			if (condition.get("dictionary_type_name") != null) {
				builder.append("AND dictionary_type_name like CONCAT('%',#{dictionary_type_name},'%')");
			}
		}
		builder.append(" LIMIT #{start},#{size}");

		// 第三步：返回SQL语句
		return builder.toString();
	}
	
	public String updateForNotNull(Map<String, Object> dictionary) {
		//1.构建公共的SQL语句
		String sql="UPDATE tb_basic_dictionary SET ";
		//2.构建SQL动态语句
		StringBuilder builder=new StringBuilder(sql);
		if (dictionary.get("dictionary_name")!=null) {
			builder.append("dictionary_name=#{dictionary_name},");
		}
		if (dictionary.get("dictionary_value")!=null) {
			builder.append("dictionary_value=#{dictionary_value},");
		}
		
		if (dictionary.get("dictionary_type_code")!=null) {
			builder.append("dictionary_type_code=#{dictionary_type_code},");
		}
		if (dictionary.get("dictionary_type_name")!=null) {
			builder.append("dictionary_type_name=#{dictionary_type_name},");
		}
	
		//删除最后一个逗号
		builder.delete(builder.length()-1, builder.length());
		//追加条件
		builder.append(" WHERE dictionary_id=#{dictionary_id}");
		LOGGER.debug("updateForNotNull SQL:"+builder.toString());
		
		
		//3.返回SQL语句
		return builder.toString();
	}
	
	//问题：为什么数组或者集合需要包一层Map呢？
	//答：可以根据DefaultSqlSession.update(DefaultSqlSession.java:198)，的代码分析，Mybatis内置决定了。如果是集合或者数组就返回map
	public String delteByIds(Map<String, Object[]>  idsMap) {
		//获得参数中的ID
		Object[] dictionaryIds = idsMap.get("array");
		//第一步：公共SQL
		String sql="DELETE FROM tb_basic_dictionary WHERE dictionary_id in";
		//第二步：构建动态SQL
		 StringBuilder builder=new StringBuilder(sql);
		 builder.append("(");
		 for(int i=0;i<dictionaryIds.length;i++) {
			 builder.append("#{array["+i+"]},");
		 }
		 //删除最后一个逗号
		 builder.delete(builder.length()-1, builder.length());
		 builder.append(")");
		//第三步：返回结果
		LOGGER.debug("delteByIds SQL:"+builder.toString());
		
		return builder.toString();
	}


}
