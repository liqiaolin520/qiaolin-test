package com.ascendant.activiti.test.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.activiti.engine.FormService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.identity.User;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ascendant.activiti.util.UserUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 任务相关Controller
 * @author qiaolin
 * @version 2017年3月15日
 *
 */

@Api("任务相关操作")
@Controller
@RequestMapping("task")
public class TaskController {
	
	@Autowired
	private TaskService taskService;
	@Autowired
	private FormService formService;
	
	
	@ApiOperation("获得用户的个人任务与组任务")
	@RequestMapping(value = "getTask")
	public String getTask(HttpSession session, ModelMap modelMap)	{ 
		/**
		 *  获得用户的参与的任务与个人任务。在Activiti5.16以后的新功能
		 *  	在此之前必须分两次查询，才能得到此效果。
		 */
		User user = UserUtil.getUserFromSession(session);
		List<Task> task = taskService.createTaskQuery().taskCandidateOrAssigned(user.getId()).list();
		modelMap.addAttribute("tasks", task);
		return "form/task-list";
	}
	
	@ApiOperation("签收任务")
	@RequestMapping(value = "claim/{taskId}")
	public String claim(@PathVariable("taskId")String taskId,HttpSession session){
		User user = UserUtil.getUserFromSession(session);
		taskService.claim(taskId, user.getId());
		return "redirect:/task/getTask";
	}
	
	@ApiOperation("获取要办理任务的表单")
	@RequestMapping(value = "getForm/{taskId}")
	public String getForm(@PathVariable("taskId")String taskId,ModelMap modelMap){
		// 检查任务表单为哪种表单。
		TaskFormData taskFormData = formService.getTaskFormData(taskId);
		if(taskFormData.getFormKey() != null){
			Object data = formService.getRenderedTaskForm(taskId);
			Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
			modelMap.addAttribute("hasFormKey", true);
			modelMap.addAttribute("data", data);
			modelMap.addAttribute("task", task);
		}else{
			modelMap.addAttribute("data", taskFormData);
		}
		modelMap.addAttribute("taskId", taskId);
		return "form/task-form";
	}
	
	@ApiOperation("完成任务")
	@RequestMapping("complete/{taskId}")
	public String complete(@PathVariable("taskId") String taskId,HttpServletRequest request){
		Map<String, String> properties = new HashMap<>();
		TaskFormData data = formService.getTaskFormData(taskId);
		if(data.getFormKey() != null){
			Map<String,String[]> params = request.getParameterMap();
			Set<Entry<String,String[]>> set = params.entrySet();
			for (Entry<String, String[]> entry : set) {
				properties.put(entry.getKey(), entry.getValue()[0]);
			}
		}else{
			List<FormProperty> formData = data.getFormProperties();
			// 讲任务需要的表单参数从请求中获取
			for (FormProperty formProperty : formData) {
				properties.put(formProperty.getId(), request.getParameter(formProperty.getId()));
			}
		}
		formService.submitTaskFormData(taskId, properties);
		return "redirect:/task/getTask";
	}
	
	
	
	
	
	
	
	
	
	
}
