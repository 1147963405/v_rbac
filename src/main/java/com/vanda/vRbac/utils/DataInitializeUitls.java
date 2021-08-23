package com.vanda.vRbac.utils;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

public class DataInitializeUitls {

	/**
	 * 新增数据时，我们更新应用作用域的数据
	 * 
	 * @param context 上下文作用域
	 * @param entity  新增的实体
	 */
	@SuppressWarnings("unchecked")
	public static void insert(ServletContext context, String key, Map<String, Object> entity) {
		// 获得作用域的记录
		List<Map<String, Object>> entitys = (List<Map<String, Object>>) context.getAttribute(key);
		entitys.add(entity);
	}

	@SuppressWarnings("unchecked")
	public static void delete(ServletContext context, String key, String idName, Object... id) {
		// 获得作用域的记录
		List<Map<String, Object>> entitys = (List<Map<String, Object>>) context.getAttribute(key);
		for (int i = 0; i < id.length; i++) {
			for (Map<String, Object> entity : entitys) {
				if (id[i].equals(entity.get(idName))) {
					entitys.remove(entity);
					//删除一条记录后，结束当次循环
					break;
				}
			}
		}

	}
	
	@SuppressWarnings("unchecked")
	public static void update(ServletContext context, String key,String idName, Map<String, Object> entity) {
		// 第一步：获得全局作用域的集合
		List<Map<String, Object>> entitys = (List<Map<String, Object>>) context.getAttribute(key);
		// 第二步：替换全局作用于集合对应的记录。条件是ID=ID
		String entityId =(String) entity.get(idName);
		for (Map<String, Object> resultEntity : entitys) {
			
			if (entityId.equals(String.valueOf(resultEntity.get(idName)))) {
				//替换集合里面的对象.思考题：引用传值
				entitys.remove(resultEntity);
				entitys.add(entity);
				break;
			}
		}
	}

}
