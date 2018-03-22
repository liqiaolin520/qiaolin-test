package com.ascendant.activiti.test.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.FormService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.identity.User;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ascendant.activiti.util.UserUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
*	流程定义相关控制器
* @author qiaolin
* @version 2017年3月14日
*
*/

@Api(value = "流程定义相关")
@RestController
@RequestMapping("processDefinition")
public class ProcessDefinitionController {
	
	@Autowired
	private FormService formService;
	@Autowired
	private IdentityService identityService;
	@Autowired
	private RepositoryService repositoryService;
	
	
	@ApiOperation("读取from表单中的字段")
	@RequestMapping(value = "getFrom/{processDefinitionId}",method = RequestMethod.POST)
	public ModelAndView getFrom(@PathVariable("processDefinitionId") String processDefinitionId){
		ModelAndView mv = new ModelAndView("form/start-process-form");
		ProcessDefinition pDinstance = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
		// 判断是否有外置表单。
		if(pDinstance.hasStartFormKey()){
			Object obj = formService.getRenderedStartForm(processDefinitionId);
			mv.addObject("data",obj);
			mv.addObject("processDefinition", pDinstance);
		}else{
			// 获得空启动事件中的动态表单
			StartFormData data = formService.getStartFormData(processDefinitionId);
			mv.addObject("data",data);
		}
		mv.addObject("formKey", pDinstance.hasStartFormKey());
		mv.addObject("processDefinitionId", processDefinitionId);
		return mv;
	}
	
	@ApiOperation("启动流程实例")
	@RequestMapping(value="startProcessInstance/{processDefinitionId}",method = RequestMethod.POST)
	public ModelAndView startProcessInstance(@PathVariable("processDefinitionId")String processDefinitionId,HttpServletRequest request){
		ModelAndView mv = new ModelAndView("redirect:/deployment/processList");
		// 获得当前登陆用户  
		User user = UserUtil.getUserFromSession(request.getSession());
		// 检查用户是否已登录，或者是否登录超时
		if(user == null || StringUtils.isBlank(user.getId())){
			mv.setViewName("redirect:/login.jsp");
			return mv;
		}
		Map<String,String> properties = new HashMap<>();
		// 查询流程表单类型
		ProcessDefinition pricessDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
		boolean hasStartFormKey = pricessDefinition.hasStartFormKey();
		if(hasStartFormKey){
			Map<String, String[]> params = request.getParameterMap();
			Set<Entry<String, String[]>> set = params.entrySet();
			for (Entry<String, String[]> entry : set) {
				properties.put(entry.getKey(), entry.getValue()[0]);
			}
		}else{
			// 获得流程中定义的动态表单
			StartFormData data = formService.getStartFormData(processDefinitionId);
			// 取出表中的字段
			List<FormProperty> formProperties = data.getFormProperties();
			for (FormProperty formProperty : formProperties) {
				// 根据取得的流程动态表单来在request中获得对应的值
				String value = request.getParameter(formProperty.getId());
				properties.put(formProperty.getId(), value);
			}
		}
		// 设置当前用户为流程启动人
		identityService.setAuthenticatedUserId(user.getId());
		// 提交表单，启动一个新的流程实例
		formService.submitStartFormData(processDefinitionId, properties);
		return mv;
	}
	

	
	
	
	
	
	
	
	
	
}
