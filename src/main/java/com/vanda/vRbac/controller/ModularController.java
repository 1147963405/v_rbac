package com.vanda.vRbac.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import com.vanda.vRbac.annotation.TokenForm;
import com.vanda.vRbac.service.ModularService;
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
@RequestMapping(value="/modular")
public class ModularController {
	
	@Autowired
	private ModularService modularService;
	
	private static final Logger LOGGER= LogManager.getLogger(ModularController.class);
	/**
	 * 请求路径：${pageContext.request.contextPath }/modular/toModularList
	 * @return
	 */
	//@RequestMapping(value="/toModularList/{index}",method=RequestMethod.GET)
	@GetMapping(value="/toModularList/{index}")
	public String toModularList(@PathVariable Integer index, HttpServletRequest request) {
		LOGGER.debug("跳转到模块列表");
		Page modularPage = modularService.findModularByConditionToPage(null, index, Global.PAGE_SIZE);
		request.setAttribute("modularPage", modularPage);
		return "manager/modularList";
	}
	/**
	 * 跳转到增加模块页面
	 * 请求路径：${pageContext.request.contextPath}/modular/toModularAdd
	 * @return
	 */
	//@RequestMapping(value="/toModularAdd")
	@GetMapping(value="/toModularAdd")
	@TokenForm(create=true)
	public String toModularAdd() {
		return "manager/modularAdd";
	}
	/**
	 * 请求路径：${pageContext.request.contextPath }/modular/addModular
	 * 增加模块
	 * @param modular
	 * @param request
	 * @return
	 */
	//@RequestMapping(value="/addModular",method=RequestMethod.POST)
	@PostMapping(value="/addModular")
	@TokenForm(remove=true)
	public String addModular(@RequestParam Map<String, Object> modular,HttpServletRequest request) {
		LOGGER.debug("增加模块："+modular);
		try {
			Map<String, Object> resultModular = modularService.addModular(modular);
			if (resultModular!=null) {
				request.setAttribute("modular_add_msg", "增加成功");
				DataInitializeUitls.insert(request.getServletContext(), "global_modulars", resultModular);
			}else {
				request.setAttribute("modular_add_msg", "10007-增加模块失败");
			
			}
		} catch (Exception e) {
			request.setAttribute("modular_add_msg", "10008-未知异常，增加模块失败");
			e.printStackTrace();
		}
		return "manager/modularAdd";
	}
	
	/**
	 * 跳转到模块编辑页面
	 * @param modularId
	 * @param request
	 * @return
	 */
	//${pageContext.request.contextPath}/modular/toModularEdit
	@RequestMapping(value="/toModularEdit/{modularId}")
	//@GetMapping(value="/toModularEdit/{modularId}")
	public String toModularEdit(@PathVariable Long modularId,HttpServletRequest request) {
		Map<String, Object> modular = modularService.findModularById(modularId);
		request.setAttribute("modular", modular);
		return "manager/modularEdit";
	}
	
	//${pageContext.request.contextPath }/modular/editModular
	@PostMapping(value="/editModular")
	public String editModular(@RequestParam Map<String, Object> modular,HttpServletRequest req) {
		LOGGER.debug("编辑模块："+modular);
		try {
			Map<String, Object> resultModular = modularService.editModular(modular);
			if (resultModular!=null) {
				req.setAttribute("modular_edit_msg", "模块更新成功");
				DataInitializeUitls.update(req.getServletContext(), "global_modulars", "modular_id", resultModular);
			}else {
				req.setAttribute("modular_edit_msg", "10010-模块更新失败");
			}
		} catch (Exception e) {
			req.setAttribute("modular_edit_msg", "10009-模块更新失败-未知异常");
			e.printStackTrace();
		}
		
		//编辑后跳回到编辑页面
		return "forward:/modular/toModularEdit/"+modular.get("modular_id");
	}
	
	//${pageContext.request.contextPath}/modular/deleteModularById/${modular.modular_id}
	@GetMapping(value="/deleteModularById/{modularId}")
	public String deleteModularById(@PathVariable Long modularId,HttpServletRequest req) {
		LOGGER.debug("-删除模块-"+modularId);
		try {
			modularService.deleteModularByIds(modularId);
			DataInitializeUitls.delete(req.getServletContext(), "global_modulars", "modular_id", modularId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//删除成功。返回列表页面
		return "redirect:/modular/toModularList/1";
	}
	

	/**
	 * 请求路径：/modular/deleteCheckModulars?modularId=9&modularId=12
	 * 批量删除选中的模块
	 * @return
	 */

	@RequestMapping(value="/deleteCheckModulars")
	public String deleteCheckModulars(@RequestParam("modularId")Long[] modularIds,HttpServletRequest req) {
		
		LOGGER.debug("-批量删除模块-"+modularIds);
		try {
			 modularService.deleteModularByIds((Object[])modularIds);
				DataInitializeUitls.delete(req.getServletContext(), "global_modulars", "modular_id", (Object[])modularIds);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/modular/toModularList/1";
	}
	

}
