package com.vanda.vRbac.listener;

import java.util.List;
import java.util.Map;



import com.vanda.vRbac.service.DictionaryService;
import com.vanda.vRbac.service.ModularService;
import com.vanda.vRbac.service.PermissionService;
import com.vanda.vRbac.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


/**
 * 
 * ServletContextListener:作用就是监听Servlet上下文的创建销毁的。
 * @author ranger
 *
 */
@Slf4j
@WebListener
public class DataInitializeListener implements ServletContextListener {
	
	//问题：Spring的依赖注入只能在Spring容器中的对象使用。而监听器不是Spring容器的对象。
	//解决方法：在非容器里的对象获得容器里面的对象，容器对象.getBean();

	/**
	 * 上下文初始化的时候调用的方法
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		log.info("==========Initialize==进来啦=========");
	 System.out.println("==启动啦==");
	 ServletContext context = sce.getServletContext();
	 WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(context);
	 RoleService roleService = applicationContext.getBean(RoleService.class);
	 DictionaryService dictionaryService = applicationContext.getBean(DictionaryService.class);
	 ModularService modularService=applicationContext.getBean(ModularService.class);
	 PermissionService permissionService=applicationContext.getBean(PermissionService.class);
	 //获得权限
	 List<Map<String, Object>> permissions = permissionService.findAllPermission();
	 context.setAttribute("global_permissions", permissions);
	 //获得角色
	 List<Map<String, Object>> roles = roleService.findAllRole();
	 context.setAttribute("global_roles", roles);
	 //数据字典
	 List<Map<String, Object>> dictionarys = dictionaryService.findAllDictionary();
	 context.setAttribute("global_dictionarys", dictionarys);
	 //获得模块
	 List<Map<String, Object>> modulars = modularService.findAllModular();
	 context.setAttribute("global_modulars", modulars);
	 
		
	}

	/**
	 * 上下文销毁的时候调用的方法
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("==关闭了==");
		ServletContext context = sce.getServletContext();
		context.removeAttribute("global_roles");
		context.removeAttribute("global_dictionarys");
		
	}

}
