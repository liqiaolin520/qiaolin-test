package com.ascendant.activiti.listener.test;

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

import com.ascendant.activiti.listener.ProcessEndListener;
import com.ascendant.activiti.listener.TaskAssigneeListener;

/**
* @author qiaolin
* @version 2017年3月29日
* 测试Spring 代理监听器bean对象
* 
*/

@ContextConfiguration("classpath:applicationContext-expression.xml")
public class TestSpringListener extends SpringActivitiTestCase{
	
	@Test
	@Ignore
	@Deployment(resources="listener/TestListener.bpmn")
	public void testSpringListener(){
		Map<String,Object> params = new HashMap<>();
		/**
		 *  创建流程结束监听器与任务分配监听器作为参数 传入
		 *		写到数据库的对象必须实现Serializable接口。
		 */
		params.put("name", "qiaoqiaolin");
		ProcessInstance instance = runtimeService.startProcessInstanceByKey("listener",params);
		Assert.assertNotNull(instance);
		
		Task task = taskService.createTaskQuery().processInstanceId(instance.getId()).singleResult();
		Assert.assertNotNull(task);
		
		System.out.println();
		System.out.println();
		System.out.println();
	}
	
	
	
}
