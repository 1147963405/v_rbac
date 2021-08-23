package com.vanda.vRbac.mapper.provider;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class PermissionProvider {
	private static final Logger LOGGER= LogManager.getLogger(PermissionProvider.class);

	public String countByCondition(Map<String, Object> condition) {
		// 第一步：声明公共的SQL语句
		String sql = "SELECT count(1) FROM tb_basic_permission WHERE 1=1 ";
		// 第二步：构建动态SQL语句
		StringBuilder builder = new StringBuilder(sql);
		if (condition != null) {

			if (condition.get("permission_name") != null) {
				builder.append("AND permission_name like CONCAT('%',#{permission_name},'%')");
			}
		}

		// 第三步：返回SQL语句
		return builder.toString();
	}

	public String findByCondition(@Param("condition") Map<String, Object> condition, @Param("start") int start,
			@Param("size") int size) {
		// 第一步：声明公共的SQL语句
		String sql = "SELECT * FROM tb_basic_permission WHERE 1=1 ";
		// 第二步：构建动态SQL语句
		StringBuilder builder = new StringBuilder(sql);
		if (condition != null) {
			if (condition.get("permission_name") != null) {
				//注意：因为参数绑定的对象名为condition，所以属性需要通过对象名来访问
				builder.append("AND permission_name like CONCAT('%',#{condition.permission_name},'%') ");
			}
		}
		builder.append(" LIMIT #{start},#{size}");

		// 第三步：返回SQL语句
		return builder.toString();
	}
	
	public String updateForNotNull(Map<String, Object> permission) {
		//1.构建公共的SQL语句
		String sql="UPDATE tb_basic_permission SET ";
		//2.构建SQL动态语句
		StringBuilder builder=new StringBuilder(sql);
		if (permission.get("permission_name")!=null) {
			builder.append("permission_name=#{permission_name},");
		}
		if (permission.get("permission_action")!=null) {
			builder.append("permission_action=#{permission_action},");
		}
		if (permission.get("permission_parent")!=null) {
			builder.append("permission_parent=#{permission_parent},");
		}
		if (permission.get("permission_is_show")!=null) {
			builder.append("permission_is_show=#{permission_is_show},");
		}
		if (permission.get("modular_id")!=null) {
			builder.append("modular_id=#{modular_id},");
		}
	
		//删除最后一个逗号
		builder.delete(builder.length()-1, builder.length());
		//追加条件
		builder.append(" WHERE permission_id=#{permission_id}");
		LOGGER.debug("updateForNotNull SQL:"+builder.toString());
		
		
		//3.返回SQL语句
		return builder.toString();
	}
	
	//问题：为什么数组或者集合需要包一层Map呢？
	//答：可以根据DefaultSqlSession.update(DefaultSqlSession.java:198)，的代码分析，Mybatis内置决定了。如果是集合或者数组就返回map
	public String delteByIds(Map<String, Object[]>  idsMap) {
		//获得参数中的ID
		Object[] permissionIds = idsMap.get("array");
		//第一步：公共SQL
		String sql="DELETE FROM tb_basic_permission WHERE permission_id in";
		//第二步：构建动态SQL
		 StringBuilder builder=new StringBuilder(sql);
		 builder.append("(");
		 for(int i=0;i<permissionIds.length;i++) {
			 builder.append("#{array["+i+"]},");
		 }
		 //删除最后一个逗号
		 builder.delete(builder.length()-1, builder.length());
		 builder.append(")");
		//第三步：返回结果
		LOGGER.debug("delteByIds SQL:"+builder.toString());
		
		return builder.toString();
	}
	
	/**
	 * 通过权限编号数组，查询多条记录
	 * @param idsMap
	 * @return
	 */
	public String findByIds(Map<String, Object[]>  idsMap) {
		//获得参数中的ID
		Object[] permissionIds = idsMap.get("array");
		//第一步：公共SQL
		String sql="SELECT *	FROM tb_basic_permission WHERE permission_id in";
		//第二步：构建动态SQL
		 StringBuilder builder=new StringBuilder(sql);
		 builder.append("(");
		 for(int i=0;i<permissionIds.length;i++) {
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
