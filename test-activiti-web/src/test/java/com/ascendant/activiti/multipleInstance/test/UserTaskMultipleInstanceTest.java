package com.ascendant.activiti.multipleInstance.test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;
import org.junit.Before;
import org.junit.Test;

import com.ascendant.activiti.test.base.AbstractTest;

/**
 * 测试用户任务多实例
 * @author qiaolin
 * @date 2017年4月6日
 *
 */

public class UserTaskMultipleInstanceTest extends AbstractTest{

	@Before
	public void init(){
		super.setUp();
	}
	
	/**
	 *  顺序方式执行。
	 *  	完美通过。
	 */
	@Test
	@Deployment(resources="multipleInstance/UserTaskMultipleInstance.bpmn")
	public void userTaskMultipleInstance(){
		Map<String,Object> params = new HashMap<>();
		// 准备一组用户。
		List<String> users = Arrays.asList("user1","user2","user3");
		params.put("users",users);
		ProcessInstance instance = runtimeService.startProcessInstanceByKey("userTaskMultipleInstance",params);
		for (String user : users) {
			Task task = taskService.createTaskQuery().processInstanceId(instance.getId()).singleResult();
			taskService.complete(task.getId());
		}
	}
	
	/**
	 * 并行方式执行。
	 *  完美通过。
	 */
	@Test
	@Deployment(resources="multipleInstance/UserTaskMultipleInstance2.bpmn")
	public void userTaskMultipleInstance2(){
		Map<String,Object> params = new HashMap<>();
		// 准备一组用户。
		List<String> users = Arrays.asList("user1","user2","user3");
		params.put("users",users);
		ProcessInstance instance = runtimeService.startProcessInstanceByKey("userTaskMultipleInstance2",params);
		System.out.println();
		System.out.println();
		List<Task> tasks = taskService.createTaskQuery().processInstanceId(instance.getId()).list();
		for (Task task : tasks) {
			taskService.complete(task.getId());
		}
	}
	
}
