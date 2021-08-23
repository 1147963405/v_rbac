package com.vanda.vRbac.controller;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.vanda.vRbac.annotation.TokenForm;
import com.vanda.vRbac.service.UserService;
import com.vanda.vRbac.utils.Global;
import com.vanda.vRbac.utils.Md5Utils;
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
@RequestMapping(value="/user")
public class UserController {
	
	private static final Logger LOGGER= LogManager.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;


	
	/**
	 * 用户登录
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/login")
	public String login(@RequestParam Map<String, Object> user,HttpServletRequest request) {
		
		LOGGER.debug("用户登录"+user);
		try {
			Map<String, Object> loginUser = userService.loginUser(user);
			//如果返回的对象不为空，登录成功
			if (loginUser!=null) {
				//登录成功，将数据放在session里面
				HttpSession session = request.getSession();
				session.setAttribute("user", loginUser);
				return "manager/index";
			}else {
				//返回消息到登录页面
				request.setAttribute("user_login_msg", "10001-账号或者密码出错");
			}
		} catch (Exception e) {
			request.setAttribute("user_login_msg", "10002-出现未知异常，请求联系管理员");
			e.printStackTrace();
		}
		return "forward:/login.jsp";
	}
	
	/**
	 * 用户注销
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/undo")
	public String undo(HttpSession session) {
		try {
			Object user = session.getAttribute("user");
			if (user!=null) {
				session.removeAttribute("user");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "forward:/login.jsp";
	}
	
	/**
	 * 跳转到密码修改表单页面
	 * @return
	 */
	@RequestMapping(value="/toSetting/*")
	public String toSetting() {
		return "manager/setting";
	}
	
	/**
	 * 编辑密码
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/editPassword")
	public String editPassword(@RequestParam Map<String, Object> entity,HttpServletRequest request) {
		LOGGER.debug("修改密码表单："+entity);
		try {
			//通过session里面的当前用户验证就可以
			HttpSession session = request.getSession();
			Map<String, Object> user=(Map<String, Object>) session.getAttribute("user");
			//必须要先将表单明文的密码加密后再对比
			String md5Password= Md5Utils.md5(entity.get("source_password"), user.get("user_salt"));
			//判断原密码是否正确
			if (md5Password.equals(user.get("user_password"))) {
				//然后修改密码，确认密码是否一致
				if (entity.get("new_password").equals(entity.get("confirm_password"))) {
					//如果相同，更新新密码到数据库
					//修改用户信息的密码，先构建一个新密码以及盐
					String salt=UUID.randomUUID().toString();
					String newPassword=Md5Utils.md5(entity.get("new_password"), salt);
					user.put("user_password", newPassword);
					user.put("user_salt", salt);
					Map<String, Object> resultUser = userService.editPassword(user);
					if (resultUser!=null) {
						session.setAttribute("user", resultUser);
						request.setAttribute("user_edit_password_msg", "10007-修改密码成功");
					}else {
						request.setAttribute("user_edit_password_msg", "10006-密码更新失败");
					}
					
				}else {
					//否则原密码不正确，返回提示信息
					request.setAttribute("user_edit_password_msg", "10003-确认密码与新密码不一致");
				}
			}else {
				//否则原密码不正确，返回提示信息
				request.setAttribute("user_edit_password_msg", "10004-原密码错误");
			}
		} catch (Exception e) {
			request.setAttribute("user_edit_password_msg", "10005-发现未知异常，请联系管理员");
			e.printStackTrace();
		}
		
		return "manager/setting";
	}
	
	/**
	 * 请求路径：${pageContext.request.contextPath }/user/toUserList
	 * @return
	 */
	//@RequestMapping(value="/toUserList/{index}",method=RequestMethod.GET)
	@GetMapping(value="/toUserList/{index}")
	public String toUserList(@PathVariable Integer index, HttpServletRequest request) {
		LOGGER.debug("跳转到用户列表");
		Page userPage = userService.findUserByConditionToPage(null, index, Global.PAGE_SIZE);
		request.setAttribute("userPage", userPage);
		return "manager/userList";
	}
	/**
	 * 跳转到增加用户页面
	 * 请求路径：${pageContext.request.contextPath}/user/toUserAdd
	 * @return
	 */
	//@RequestMapping(value="/toUserAdd")
	@GetMapping(value="/toUserAdd")
	@TokenForm(create=true)
	public String toUserAdd(HttpServletRequest request) {
		//注意：我们在监听器里面获得全局数据，所以这里不需要查询了
		//获得数据字典值
		//List<Map<String, Object>> dictionarys = dictionaryService.findAllDictionary();
		//获得查询的角色
		//List<Map<String, Object>> roles = roleService.findAllRole();
		
		//request.setAttribute("dictionarys", dictionarys);
		//request.setAttribute("roles", roles);
		return "manager/userAdd";
	}
	/**
	 * 请求路径：${pageContext.request.contextPath }/user/addUser
	 * 增加用户
	 * @param user
	 * @param request
	 * @return
	 */
	//@RequestMapping(value="/addUser",method=RequestMethod.POST)
	@PostMapping(value="/addUser")
	@TokenForm(remove=true)
	public String addUser(@RequestParam Map<String, Object> user,HttpServletRequest request) {
		LOGGER.debug("增加用户："+user);
		try {
			//创建日期
			user.put("user_createdate", new Date());
			//盐值
			String salt = UUID.randomUUID().toString();
			user.put("user_salt",salt );
			//密码加密
			String md5Pwd=Md5Utils.md5(user.get("user_password"), salt);
			user.put("user_password", md5Pwd);
			
			
			Map<String, Object> resultUser = userService.addUser(user);
			if (resultUser!=null) {
				request.setAttribute("user_add_msg", "增加成功");
			}else {
				request.setAttribute("user_add_msg", "10007-增加用户失败");
			}
		} catch (Exception e) {
			request.setAttribute("user_add_msg", "10008-未知异常，增加用户失败");
			e.printStackTrace();
		}
		return "manager/userAdd";
	}
	
	/**
	 * 跳转到用户编辑页面
	 * @param userId
	 * @param request
	 * @return
	 */
	//${pageContext.request.contextPath}/user/toUserEdit
	@RequestMapping(value="/toUserEdit/{userId}")
	//@GetMapping(value="/toUserEdit/{userId}")
	public String toUserEdit(@PathVariable Long userId,HttpServletRequest request) {
		Map<String, Object> user = userService.findUserById(userId);
		request.setAttribute("user", user);
		return "manager/userEdit";
	}
	
	//${pageContext.request.contextPath }/user/editUser
	@PostMapping(value="/editUser")
	public String editUser(@RequestParam Map<String, Object> user,HttpServletRequest req) {
		LOGGER.debug("编辑用户："+user);
		
		//密码加密后再更新
		//获得盐值
		if (user.get("user_password")!=null) {
			String salt = UUID.randomUUID().toString();
			String md5Pwd = Md5Utils.md5(user.get("user_password"), salt);
			user.put("user_password", md5Pwd);
			user.put("user_salt", salt);
		}
		
		
		try {
			Map<String, Object> resultUser= userService.editUser(user);
			if (resultUser!=null) {
				req.setAttribute("user_edit_msg", "用户更新成功");
			}else {
				req.setAttribute("user_edit_msg", "10010-用户更新失败");
			}
		} catch (Exception e) {
			req.setAttribute("user_edit_msg", "10009-用户更新失败-未知异常");
			e.printStackTrace();
		}
		
		//编辑后跳回到编辑页面
		return "forward:/user/toUserEdit/"+user.get("user_id");
	}
	
	//${pageContext.request.contextPath}/user/deleteUserById/${user.user_id}
	@GetMapping(value="/deleteUserById/{userId}")
	public String deleteUserById(@PathVariable Long userId,ServletContext context) {
		LOGGER.debug("-删除用户-"+userId);
		try {
			userService.deleteUserByIds(userId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//删除成功。返回列表页面
		return "redirect:/user/toUserList/1";
	}
	

	/**
	 * 请求路径：/user/deleteCheckUsers?userId=9&userId=12
	 * 批量删除选中的用户
	 * @return
	 */

	@RequestMapping(value="/deleteCheckUsers")
	public String deleteCheckUsers(@RequestParam("userId")Long[] userIds) {
		
		LOGGER.debug("-批量删除用户-"+userIds);
		try {
			 userService.deleteUserByIds((Object[])userIds);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/user/toUserList/1";
	}
	

}
