package com.ascendant.activiti.el.test;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;
import org.activiti.spring.impl.test.SpringActivitiTestCase;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author qiaolin
 * @version 2017年3月27日
 * 测试Spring 代理EL表达式中的bean对象，这样bean对象就不用写到数据库，减轻数据库的数据量。
 */

@ContextConfiguration("classpath:applicationContext-expression.xml")
public class SpringActivitiEL extends SpringActivitiTestCase {

	@Test
	@Deployment(resources = "el/elTest.bpmn")
	public void testSpringActivitiEL() {
		// 准备流程需要的数据
		Map<String, Object> params = new HashMap<>();
		String name = "qiaoqiaolin";
		params.put("name", name);
		// 设置流程启动人 
		identityService.setAuthenticatedUserId("试试");
		// 设置业务Key
		String businessKey = "8888";
		// 启动流程
		ProcessInstance instance = runtimeService.startProcessInstanceByKey("el", businessKey, params);
		// 验证我们猜测结果。
		Assert.assertEquals("试试", runtimeService.getVariable(instance.getId(), "startUserIdForTest"));
		Assert.assertEquals("qiaoqiaolinprint(String name)",
				runtimeService.getVariable(instance.getId(), "returnValue"));
		Assert.assertEquals(businessKey, runtimeService.getVariable(instance.getId(), "businessKey"));
		// 查询任务。
		Task task = taskService.createTaskQuery().processInstanceId(instance.getId()).singleResult();
		// 根据任务获得任务监听器中表达式产生的流程变量是不是跟我们猜测的一样。
		String obj = (String) taskService.getVariable(task.getId(), "setByTask");
		Assert.assertEquals("啊啊, " + name, obj);
	}

}
