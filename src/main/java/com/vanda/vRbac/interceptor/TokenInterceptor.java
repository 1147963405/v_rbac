package com.vanda.vRbac.interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.vanda.vRbac.annotation.TokenForm;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 防重提交
 * @author ranger
 *
 */
public class TokenInterceptor implements HandlerInterceptor {

	@SuppressWarnings("unchecked")
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//第一步：如果是一个增加的请求，我们就创建一个Token
		//问题：如何标志请求的方法是一个增加的方法呢？通过注解来标识
		//HandlerMethod对象可以获得请求准备要调用的方法的方法信息
		List<String> tokenPool=null;
		HandlerMethod hm=(HandlerMethod) handler;
		TokenForm tokenForm = hm.getMethodAnnotation(TokenForm.class);
		if (tokenForm!=null) {
			HttpSession session = request.getSession();
			//问题：如果多个功能使用的SessionToken是同一个。会出现相互覆盖的情况
			//解决方案，使用一个集合来存储sessionToken。将Token分为当前Token （用于返回到页面），Token池
			if (session.getAttribute("tokenPool")==null) {
				tokenPool=new ArrayList<>();
			}else {
				tokenPool=(List<String>) session.getAttribute("tokenPool");
			}
			//只要，进入或者提交都要出境一个新的Token放在Session里面
			String sessionToken = UUID.randomUUID().toString();
			//将创建的Token放在会话的tokenPool里面
			tokenPool.add(sessionToken);
			//将创建的Token放在会话方放在一个字段里面用于返回到页面
			session.setAttribute("sessionToken", sessionToken);
			session.setAttribute("tokenPool", tokenPool);
			
			//remove为true,表示是一个提交增加的请求
			if (tokenForm.remove()) {
				//判断请求表单的token和会话里面的Token是否相同
				String formToken = request.getParameter("formToken");
				List<String> resultTokenPool = (List<String>) session.getAttribute("tokenPool");
				boolean flag=false;
				for (String token : resultTokenPool) {
					if (token.equals(formToken)) {
						//如果发现token池有对应的Token就移除
						resultTokenPool.remove(token);
						flag=true;
						break;
					}
				}
				//如果表单的Token在Token池里面没有。就跳转到指定的页面
				if (flag==false) {
	
					response.sendRedirect(request.getParameter("token.invoke"));
					return false;
				}
			}
		}
		
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

}
