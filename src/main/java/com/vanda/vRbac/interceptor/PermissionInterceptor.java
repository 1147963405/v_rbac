package com.vanda.vRbac.interceptor;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
@Slf4j
public class PermissionInterceptor implements HandlerInterceptor{

	@SuppressWarnings("unchecked")
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//权限校验：所有的权限必须要经过判断，正确后才允许操作。否则返回到登录页面
		//第一步：获得当前用户的权限
		HttpSession session = request.getSession();
		Map<String, Object> user = (Map<String, Object>) session.getAttribute("user");
		Map<String, Object> role = (Map<String, Object>) user.get("role");
		List<Map<String, Object>> permissions = (List<Map<String, Object>>) role.get("permissions");
		
		//第二步：获得请求的路径
		String uri = request.getRequestURI();
		
		//第三部：判断路径是否有权限
		for (Map<String, Object> permission : permissions) {
			if (uri.contains((String)permission.get("permission_action"))) {
				session.setAttribute("path", uri);
				return true;
			}
		}
		
		request.setAttribute("user_login_msg", "10009-你没有该操作的权限");
	    request.getRequestDispatcher("/login.jsp").forward(request, response);
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}



}
