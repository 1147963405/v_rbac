package com.vanda.vRbac.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import com.vanda.vRbac.annotation.TokenForm;
import com.vanda.vRbac.service.PermissionService;
import com.vanda.vRbac.utils.DataInitializeUitls;
import com.vanda.vRbac.utils.Global;
import com.vanda.vRbac.utils.Page;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping(value="/permission")
@Slf4j
public class PermissionController {
	
	@Autowired
	private PermissionService permissionService;
	
	private static final Logger LOGGER= LogManager.getLogger(PermissionController.class);
	/**
	 * 请求路径：${pageContext.request.contextPath }/permission/toPermissionList
	 * @return
	 */
	//@RequestMapping(value="/toPermissionList/{index}",method=RequestMethod.GET)
	@GetMapping(value="/toPermissionList/{index}")
	public String toPermissionList(@PathVariable Integer index, HttpServletRequest request) {
		LOGGER.debug("跳转到权限列表");
		 Page permissionPage = permissionService.findPermissionByConditionToPage(null, index, Global.PAGE_SIZE);
		request.setAttribute("permissionPage", permissionPage);
		return "manager/permissionList";
	}
	/**
	 * 跳转到增加权限页面
	 * 请求路径：${pageContext.request.contextPath}/permission/toPermissionAdd
	 * @return
	 */
	//@RequestMapping(value="/toPermissionAdd")
	@GetMapping(value="/toPermissionAdd")
	@TokenForm(create=true)
	public String toPermissionAdd() {

		return "manager/permissionAdd";
	}
	/**
	 * 请求路径：${pageContext.request.contextPath }/permission/addPermission
	 * 增加权限
	 * @param permission
	 * @param request
	 * @return
	 */
	@PostMapping(value="/addPermission")
	@TokenForm(remove=true)
	public String addPermission(@RequestParam Map<String, Object> permission,HttpServletRequest request) {
		LOGGER.debug("增加权限："+permission);
		try {
			Map<String, Object> resultPermission = permissionService.addPermission(permission);
			log.info("====resultPermission=========================>"+resultPermission);
			if (resultPermission!=null) {
				request.setAttribute("permission_add_msg", "增加成功");
				log.info("====进来啦^_^=========================>");
				DataInitializeUitls.insert(request.getServletContext(), "global_permissions", resultPermission);
			}else {
				request.setAttribute("permission_add_msg", "10007-增加权限失败");
			}
		} catch (Exception e) {
			request.setAttribute("permission_add_msg", "10008-未知异常，增加权限失败");
			e.printStackTrace();
		}
		return "manager/permissionAdd";
	}
	
	/**
	 * 跳转到权限编辑页面
	 * @param permissionId
	 * @param request
	 * @return
	 */
	//${pageContext.request.contextPath}/permission/toPermissionEdit
	@RequestMapping(value="/toPermissionEdit/{permissionId}")
	public String toPermissionEdit(@PathVariable Long permissionId,HttpServletRequest request) {
		Map<String, Object> permission = permissionService.findPermissionById(permissionId);
		request.setAttribute("permission", permission);
		return "manager/permissionEdit";
	}


	@PostMapping(value="/editPermission")
	public String editPermission(@RequestParam Map<String, Object> permission,HttpServletRequest req) {
		LOGGER.debug("编辑权限："+permission);
		try {
			Map<String, Object> resultPermission = permissionService.editPermission(permission);
			if (resultPermission!=null) {
				req.setAttribute("permission_edit_msg", "权限更新成功");
				DataInitializeUitls.update(req.getServletContext(), "global_permissions", "permission_id", resultPermission);
			}else {
				req.setAttribute("permission_edit_msg", "10010-权限更新失败");
			}
		} catch (Exception e) {
			req.setAttribute("permission_edit_msg", "10009-权限更新失败-未知异常");
			e.printStackTrace();
		}
		
		//编辑后跳回到编辑页面
		return "forward:/permission/toPermissionEdit/"+permission.get("permission_id");
	}
	
	//${pageContext.request.contextPath}/permission/deletePermissionById/${permission.permission_id}
	@GetMapping(value="/deletePermissionById/{permissionId}")
	public String deletePermissionById(@PathVariable Long permissionId,HttpServletRequest request) {
		LOGGER.debug("-删除权限-"+permissionId);
		try {
			permissionService.deletePermissionByIds(permissionId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		DataInitializeUitls.delete(request.getServletContext(), "global_permissions", "permission_id", permissionId);
		//删除成功。返回列表页面
		return "redirect:/permission/toPermissionList/1";
	}
	

	/**
	 * 请求路径：/permission/deleteCheckPermissions?permissionId=9&permissionId=12
	 * 批量删除选中的权限
	 * @return
	 */

	@RequestMapping(value="/deleteCheckPermissions")
	public String deleteCheckPermissions(@RequestParam("permissionId")Long[] permissionIds,HttpServletRequest req) {
		
		LOGGER.debug("-批量删除权限-"+permissionIds);
		try {
			 permissionService.deletePermissionByIds((Object[])permissionIds);
		} catch (Exception e) {
			e.printStackTrace();
		}
		DataInitializeUitls.delete(req.getServletContext(), "global_permissions", "permission_id", (Object[])permissionIds);
		return "redirect:/permission/toPermissionList/1";
	}

	@GetMapping(value="/permissionList")
	public String permissionList(HttpServletRequest request) {
		List<Map<String, Object>> allPermission = permissionService.findAllPermission();
		log.info("===============allPermission====================>"+allPermission);
		request.setAttribute("allPermission",allPermission);
		return "manager/permissionList";
	}
}
