package com.vanda.vRbac.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import com.vanda.vRbac.annotation.TokenForm;
import com.vanda.vRbac.service.DictionaryService;
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
@RequestMapping(value="/dictionary")
public class DictionaryController {
	
	@Autowired
	private DictionaryService dictionaryService;
	
	private static final Logger LOGGER= LogManager.getLogger(DictionaryController.class);
	/**
	 * 请求路径：${pageContext.request.contextPath }/dictionary/toDictionaryList
	 * @return
	 */
	//@RequestMapping(value="/toDictionaryList/{index}",method=RequestMethod.GET)
					
	@GetMapping(value="/toDictionaryList/{index}")
	public String toDictionaryList(@PathVariable Integer index, HttpServletRequest request) {
		LOGGER.debug("跳转到数据字典列表");
		Page dictionaryPage = dictionaryService.findDictionaryByConditionToPage(null, index, Global.PAGE_SIZE);
		request.setAttribute("dictionaryPage", dictionaryPage);
		return "manager/dictionaryList";
	}
	/**
	 * 跳转到增加数据字典页面
	 * 请求路径：${pageContext.request.contextPath}/dictionary/toDictionaryAdd
	 * @return
	 */
	//@RequestMapping(value="/toDictionaryAdd")
	@GetMapping(value="/toDictionaryAdd")
	@TokenForm(create=true)
	public String toDictionaryAdd() {
		return "manager/dictionaryAdd";
	}
	/**
	 * 请求路径：${pageContext.request.contextPath }/dictionary/addDictionary
	 * 增加数据字典
	 * @param dictionary
	 * @param request
	 * @return
	 */
	//@RequestMapping(value="/addDictionary",method=RequestMethod.POST)
	@TokenForm(remove=true)
	@PostMapping(value="/addDictionary")
	public String addDictionary(@RequestParam Map<String, Object> dictionary,HttpServletRequest request) {
		LOGGER.debug("增加数据字典："+dictionary);
		try {
			Map<String, Object> resultDictionary = dictionaryService.addDictionary(dictionary);
			if (resultDictionary!=null) {
				request.setAttribute("dictionary_add_msg", "增加成功");
				DataInitializeUitls.insert(request.getServletContext(), "global_dictionarys", resultDictionary);
			}else {
				request.setAttribute("dictionary_add_msg", "10007-增加数据字典失败");
			}
		} catch (Exception e) {
			request.setAttribute("dictionary_add_msg", "10008-未知异常，增加数据字典失败");
			e.printStackTrace();
		}
		return "manager/dictionaryAdd";
	}
	
	/**
	 * 跳转到数据字典编辑页面
	 * @param dictionaryId
	 * @param request
	 * @return
	 */
	//${pageContext.request.contextPath}/dictionary/toDictionaryEdit
	@RequestMapping(value="/toDictionaryEdit/{dictionaryId}")
	//@GetMapping(value="/toDictionaryEdit/{dictionaryId}")
	public String toDictionaryEdit(@PathVariable Long dictionaryId,HttpServletRequest request) {
		Map<String, Object> dictionary = dictionaryService.findDictionaryById(dictionaryId);
		request.setAttribute("dictionary", dictionary);
		return "manager/dictionaryEdit";
	}
	
	//${pageContext.request.contextPath }/dictionary/editDictionary
	@PostMapping(value="/editDictionary")
	public String editDictionary(@RequestParam Map<String, Object> dictionary,HttpServletRequest req) {
		LOGGER.debug("编辑数据字典："+dictionary);
		try {
			Map<String, Object> resultDictionary = dictionaryService.editDictionary(dictionary);
			if (resultDictionary!=null) {
				req.setAttribute("dictionary_edit_msg", "数据字典更新成功");
				DataInitializeUitls.update(req.getServletContext(), "global_dictionarys", "dictionary_id", resultDictionary);
			}else {
				req.setAttribute("dictionary_edit_msg", "10010-数据字典更新失败");
			}
		} catch (Exception e) {
			req.setAttribute("dictionary_edit_msg", "10009-数据字典更新失败-未知异常");
			e.printStackTrace();
		}
		
		//编辑后跳回到编辑页面
		return "forward:/dictionary/toDictionaryEdit/"+dictionary.get("dictionary_id");
	}
	
	//${pageContext.request.contextPath}/dictionary/deleteDictionaryById/${dictionary.dictionary_id}
	@GetMapping(value="/deleteDictionaryById/{dictionaryId}")
	public String deleteDictionaryById(@PathVariable Long dictionaryId,HttpServletRequest request) {
		LOGGER.debug("-删除数据字典-"+dictionaryId);
		try {
			dictionaryService.deleteDictionaryByIds(dictionaryId);
			DataInitializeUitls.delete(request.getServletContext(), "global_dictionarys", "dictionary_id", dictionaryId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//删除成功。返回列表页面
		return "redirect:/dictionary/toDictionaryList/1";
	}
	

	/**
	 * 请求路径：/dictionary/deleteCheckDictionarys?dictionaryId=9&dictionaryId=12
	 * 批量删除选中的数据字典
	 * @return
	 */

	@RequestMapping(value="/deleteCheckDictionarys")
	public String deleteCheckDictionarys(@RequestParam("dictionaryId")Long[] dictionaryIds,HttpServletRequest request) {
		
		LOGGER.debug("-批量删除数据字典-"+dictionaryIds);
		try {
			 dictionaryService.deleteDictionaryByIds((Object[])dictionaryIds);
			 DataInitializeUitls.delete(request.getServletContext(), "global_dictionarys", "dictionary_id", (Object[])dictionaryIds);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/dictionary/toDictionaryList/1";
	}
	

}
