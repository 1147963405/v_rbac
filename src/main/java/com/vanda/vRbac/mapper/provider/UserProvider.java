package com.vanda.vRbac.mapper.provider;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * 用户构建User表的动态SQL语句的
 * @author ranger
 *
 */
public class UserProvider {
	
	private static final Logger LOGGER= LogManager.getLogger(UserProvider.class);


	public String countByCondition(Map<String, Object> condition) {
		// 第一步：声明公共的SQL语句
		String sql = "SELECT count(1) FROM tb_basic_user WHERE 1=1 ";
		// 第二步：构建动态SQL语句
		StringBuilder builder = new StringBuilder(sql);
		if (condition != null) {

			if (condition.get("user_name") != null) {
				builder.append("AND user_name like CONCAT('%',#{user_name},'%')");
			}
			if (condition.get("user_account") != null) {
				builder.append("AND user_account like CONCAT('%',#{user_account},'%')");
			}
			
		}

		// 第三步：返回SQL语句
		return builder.toString();
	}

	public String findByCondition(@Param("condition") Map<String, Object> condition, @Param("start") int start,
			@Param("size") int size) {
		// 第一步：声明公共的SQL语句
		String sql = "SELECT * FROM tb_basic_user WHERE 1=1 ";
		// 第二步：构建动态SQL语句
		StringBuilder builder = new StringBuilder(sql);
		if (condition != null) {
			if (condition.get("user_name") != null) {
				//注意：因为参数绑定的对象名为condition，所以属性需要通过对象名来访问
				builder.append("AND user_name like CONCAT('%',#{condition.user_name},'%') ");
			}
			
			if (condition.get("user_account") != null) {
				builder.append("AND user_account like CONCAT('%',#{user_account},'%')");
			}
		}
		builder.append(" LIMIT #{start},#{size}");

		// 第三步：返回SQL语句
		return builder.toString();
	}
	

	
	//问题：为什么数组或者集合需要包一层Map呢？
	//答：可以根据DefaultSqlSession.update(DefaultSqlSession.java:198)，的代码分析，Mybatis内置决定了。如果是集合或者数组就返回map
	public String delteByIds(Map<String, Object[]>  idsMap) {
		//获得参数中的ID
		Object[] userIds = idsMap.get("array");
		//第一步：公共SQL
		String sql="DELETE FROM tb_basic_user WHERE user_id in";
		//第二步：构建动态SQL
		 StringBuilder builder=new StringBuilder(sql);
		 builder.append("(");
		 for(int i=0;i<userIds.length;i++) {
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
	 * 参数规则：
	 * 1.非数组、集合的参数，调用方法是什么，构建SQL语句的方法就是什么
	 * 2.是数组、集合的参数，构建的方法需要包一层Map。如：调用方法为：String[] ids ,构建方法格式为Map<String,String[]> ids
	 * @param user
	 * @return
	 */
	public String updateForNotNull(Map<String, Object> user) {
		//1.构建公共的SQL语句
		String sql="UPDATE tb_basic_user SET ";
		//2.构建SQL动态语句
		StringBuilder builder=new StringBuilder(sql);
		if (user.get("user_name")!=null) {
			builder.append("user_name=#{user_name},");
		}
		if (user.get("user_account")!=null) {
			builder.append("user_account=#{user_account},");
		}
		if (user.get("user_password")!=null) {
			builder.append("user_password=#{user_password},");
		}
		if (user.get("user_status")!=null) {
			builder.append("user_status=#{user_status},");
		}
		if (user.get("user_createdate")!=null) {
			builder.append("user_createdate=#{user_createdate},");
		}
		if (user.get("user_salt")!=null) {
			builder.append("user_salt=#{user_salt},");
		}
		if (user.get("role_id")!=null) {
			builder.append("role_id=#{role_id},");
		}
		//删除最后一个逗号
		builder.delete(builder.length()-1, builder.length());
		//追加条件
		builder.append(" WHERE user_id=#{user_id}");
		LOGGER.debug("updateForNotNull SQL:"+builder.toString());
		
		
		//3.返回SQL语句
		return builder.toString();
	}

}
