package com.vanda.vRbac.filter;

import java.util.Enumeration;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 思路：
 * 第一步：声明一个成员变量是Map。用于设置我们修改后的表单参数
 * 第二部：重写request里面的获得表单参数的四个方法。设置值都是通过我们修改后参数的变量Map来获得值
 * @author ranger
 *
 */
public class NullValueHandlerRequestWrapper extends HttpServletRequestWrapper  {
	
	public NullValueHandlerRequestWrapper(HttpServletRequest request) {
		super(request);

	}

	private Map<String, String[]> parameterMap;

	public void setParameterMap(Map<String, String[]> parameterMap) {
		this.parameterMap = parameterMap;
	}
	


	@Override
	public String getParameter(String name) {
		String[] results = parameterMap.get(name);
		return results[0];
	}

	@Override
	public Map<String, String[]> getParameterMap() {

		return this.parameterMap;
	}

	@Override
	public Enumeration<String> getParameterNames() {
	
		Vector<String> vector = new Vector<String>(parameterMap.keySet());
		return vector.elements();
	}

	@Override
	public String[] getParameterValues(String name) {
	
		return parameterMap.get(name);
	}
	
	

}
