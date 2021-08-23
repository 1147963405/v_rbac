package com.vanda.vRbac.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import com.vanda.vRbac.annotation.TokenForm;
import com.vanda.vRbac.service.RoleService;
import com.vanda.vRbac.utils.DataInitializeUitls;
import com.vanda.vRbac.utils.Global;
import com.vanda.vRbac.utils.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value="/role")
public class RoleController {

	
	@Autowired
	private RoleService roleService;
	
	private static final Logger LOGGER= LogManager.getLogger(RoleController.class);
	/**
	 * 请求路径：${pageContext.request.contextPath }/role/toRoleList
	 * @return
	 */
	//@RequestMapping(value="/toRoleList/{index}",method=RequestMethod.GET)
	@GetMapping(value="/toRoleList/{index}")
	public String toRoleList(@PathVariable Integer index, HttpServletRequest request) {
		LOGGER.debug("跳转到角色列表");
		
		Page rolePage = roleService.findRoleByConditionToPage(null, index, Global.PAGE_SIZE);
		request.setAttribute("rolePage", rolePage);
		return "manager/roleList";
	}
	
	
	/**
	 * 跳转到增加角色页面
	 * 请求路径：${pageContext.request.contextPath}/role/toRoleAdd
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/toRoleAdd")
	@TokenForm(create=true)
	public String toRoleAdd(HttpServletRequest request) {
		//构建权限矩阵数据集合
		ServletContext context = request.getServletContext();
		//第一步：获得所有的模块的集合数据
		List<Map<String, Object>> modulars = (List<Map<String, Object>>) context.getAttribute("global_modulars");
		//第二步：获得全部权限的数据
		List<Map<String, Object>> permissions = (List<Map<String, Object>>) context.getAttribute("global_permissions");
		//第三部：将权限数据放在模块里面
		for (Map<String, Object> modular : modulars) {
			//1.创建一个临时的集合，用于存储该模块的权限
			List<Map<String, Object>> modularPermissions=new ArrayList<>();
			for (Map<String, Object> permission : permissions) {
				//2.如果该权限的模块编号与模块的相同，就讲该权限加入到集合里面
				if (String.valueOf(modular.get("modular_id")).equals(String.valueOf(permission.get("modular_id")))) {
					modularPermissions.add(permission);
				}
			}
			//3.一次循环结束后，讲所有同一模块的权限赋予该模块
			modular.put("permissions", modularPermissions);
		}

		request.setAttribute("modulars", modulars);

		return "manager/roleAdd";
	}
	/**
	 * 请求路径：${pageContext.request.contextPath }/role/addRole
	 * 增加角色
	 * @param role
	 * @param request
	 * @return
	 */
	//@RequestMapping(value="/addRole",method=RequestMethod.POST)
	@PostMapping(value="/addRole")
	@TokenForm(remove=true)
	public String addRole(@RequestParam Map<String, Object> role ,@RequestParam("permissionId") Long[] permissionIds,HttpServletRequest request) {
		LOGGER.debug("增加角色："+role);
        //将数组转变为一个字符串 [1,2,3,4,5,6]
		StringBuilder builder=new StringBuilder(Arrays.toString(permissionIds));
		//删除 中括号
		builder.delete(0, 1);
		builder.delete(builder.length()-1, builder.length());
		//设置权限字符串
		role.put("role_permissions", builder.toString());
		
		try {
			Map<String, Object> resultRole = roleService.addRole(role);
			if (resultRole!=null) {
				request.setAttribute("role_add_msg", "增加成功");
				DataInitializeUitls.insert(request.getServletContext(), "global_roles", role);
			}else {
				request.setAttribute("role_add_msg", "10007-增加角色失败");
			}
		} catch (Exception e) {
			request.setAttribute("role_add_msg", "10008-未知异常，增加角色失败");
			e.printStackTrace();
		}
 		return "forward:/role/toRoleAdd";
	}
	
	/**
	 * 跳转到角色编辑页面
	 * @param roleId
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/toRoleEdit/{roleId}")
	public String toRoleEdit(@PathVariable Long roleId,HttpServletRequest request) {
		Map<String, Object> role = roleService.findRoleById(roleId);
		request.setAttribute("role", role);
		ServletContext context = request.getServletContext();
		//第一步：获得所有的模块的集合数据
		List<Map<String, Object>> modulars = (List<Map<String, Object>>) context.getAttribute("global_modulars");
		//第二步：获得全部权限的数据
		List<Map<String, Object>> permissions = (List<Map<String, Object>>) context.getAttribute("global_permissions");
		//第三部：将权限数据放在模块里面
		for (Map<String, Object> modular : modulars) {
			//1.创建一个临时的集合，用于存储该模块的权限
			List<Map<String, Object>> modularPermissions=new ArrayList<>();
			for (Map<String, Object> permission : permissions) {
				//2.如果该权限的模块编号与模块的相同，就讲该权限加入到集合里面
				//System.out.println(permission.get("permission_name")+"===="+modular.get("modular_id").getClass()+"=="+permission.get("modular_id").getClass()+"==="+modular.get("modular_id")+"==="+permission.get("modular_id"));
				if (String.valueOf(modular.get("modular_id")).equals(String.valueOf(permission.get("modular_id")))) {
					modularPermissions.add(permission);
				}
			}
			//3.一次循环结束后，讲所有同一模块的权限赋予该模块
			modular.put("permissions", modularPermissions);
		}
		request.setAttribute("modulars", modulars);
		return "manager/roleEdit";
	}
	
	//${pageContext.request.contextPath }/role/editRole
	@PostMapping(value="/editRole")
	public String editRole(@RequestParam Map<String, Object> role,@RequestParam(name="permissionId",required=false) Long[] permissionIds,HttpServletRequest req) {
		LOGGER.debug("编辑角色："+role);
		
	    //将数组转变为一个字符串 [1,2,3,4,5,6]
		if (permissionIds!=null) {
			StringBuilder builder=new StringBuilder(Arrays.toString(permissionIds));
			//删除 中括号
			builder.delete(0, 1);
			builder.delete(builder.length()-1, builder.length());
			//设置权限字符串
			role.put("role_permissions", builder.toString());
		}else {
			role.put("role_permissions", "");
		}
		try {
			Map<String, Object> resultRole = roleService.editRole(role);
			if (resultRole!=null) {
				req.setAttribute("role_edit_msg", "角色更新成功");
				DataInitializeUitls.update(req.getServletContext(), "global_roles", "role_id", resultRole);
			}else {
				req.setAttribute("role_edit_msg", "10010-角色更新失败");
			}
		} catch (Exception e) {
			req.setAttribute("role_edit_msg", "10009-角色更新失败-未知异常");
			e.printStackTrace();
		}
		
		//编辑后跳回到编辑页面
		return "forward:/role/toRoleEdit/"+role.get("role_id");
	}
	
	//${pageContext.request.contextPath}/role/deleteRoleById/${role.role_id}
	@GetMapping(value="/deleteRoleById/{roleId}")
	public String deleteRoleById(@PathVariable Long roleId,HttpServletRequest request) {
		LOGGER.debug("-删除角色-"+roleId);
		try {
			roleService.deleteRoleByIds(roleId);
			DataInitializeUitls.delete(request.getServletContext(), "global_roles", "role_id", roleId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//删除成功。返回列表页面
		return "redirect:/role/toRoleList/1";
	}
	

	/**
	 * 请求路径：/role/deleteCheckRoles?roleId=9&roleId=12
	 * 批量删除选中的角色
	 * @return
	 */

	@RequestMapping(value="/deleteCheckRoles")
	public String deleteCheckRoles(@RequestParam("roleId")Long[] roleIds,HttpServletRequest request) {
		
		LOGGER.debug("-批量删除角色-"+roleIds);
		try {
			 roleService.deleteRoleByIds((Object[])roleIds);
			 DataInitializeUitls.delete(request.getServletContext(), "global_roles", "role_id",(Object[]) roleIds);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/role/toRoleList/1";
	}
	
}
