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
 * 测试提前结束多实例的用户任务。
 * @author qiaolin
 * @date 2017年4月7日
 *
 */

public class AnEarlyEndTest extends AbstractTest {
	
	@Before
	public void init(){
		super.setUp();
	}
	
	/**
	 * 完美通过。
	 */
	@Test
	@Deployment(resources="multipleInstance/anEarlyEnd.bpmn")
	public void testAnEarlyEnd(){
		List<String> users = Arrays.asList("user1","user2","user3");
		Map<String,Object> params = new HashMap<>();
		params.put("users", users);
		params.put("rate", 0.6d);
		ProcessInstance instance = runtimeService.startProcessInstanceByKey("anEarlyEnd",params);
		
		Task task = taskService.createTaskQuery().taskAssignee("user1").singleResult();
		taskService.complete(task.getId());
		
		task = taskService.createTaskQuery().taskAssignee("user2").singleResult();
		taskService.complete(task.getId());
	
		System.out.println();
		System.out.println();
		System.out.println();
		
	}
	
	
	
}
