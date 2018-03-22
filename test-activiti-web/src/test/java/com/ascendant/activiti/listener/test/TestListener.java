package com.ascendant.activiti.listener.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.ascendant.activiti.listener.ProcessEndListener;
import com.ascendant.activiti.listener.TaskAssigneeListener;
import com.ascendant.activiti.test.base.AbstractTest;

/**
* @author qiaolin
* @version 2017年3月29日
* 使用原始的方法测试监听器在工作流中的应用。
*/

public class TestListener extends AbstractTest{

	@Before
	@Ignore
	public void init(){
		super.setUp();
	}
	
	@Test
	@Ignore
	@Deployment(resources="listener/TestListener.bpmn")
	public void testActivitiListener(){
		Map<String,Object> params = new HashMap<>();
		/**
		 *  创建流程结束监听器与任务分配监听器作为参数 传入
		 *		写到数据库的对象必须实现Serializable接口。
		 */
		params.put("endListener",new ProcessEndListener());
		params.put("assignmentListener", new TaskAssigneeListener());
		params.put("name", "qiaoqiaolin");
		ProcessInstance instance = runtimeService.startProcessInstanceByKey("listener",params);
		// 检查是否执行了启动监听器。
		String pId = instance.getId();
		Assert.assertTrue((boolean)runtimeService.getVariable(pId, "ProcessStartListener"));
		// 检查任务创建监听器是否已经执行。
		Task task = taskService.createTaskQuery().taskAssignee("xiaohuang").singleResult();
		Assert.assertNotNull(task);
		String setCreateTask = (String)taskService.getVariable(task.getId(), "setCreateTask");
		Assert.assertEquals("create,Hello，qiaoqiaolin", setCreateTask);
		// 完成任务
		taskService.complete(task.getId()); 
		// 查询历史流程变量，看流程结束监听器是否已被执行
		List<HistoricVariableInstance> list = historyService.createHistoricVariableInstanceQuery().processInstanceId(instance.getId()).list();
		boolean b = false;
		for (HistoricVariableInstance hvi : list) {
			if("ProcessEndListener".equals(hvi.getVariableName())){
				b = true; 
			}
		}
		Assert.assertTrue(b);
	}
	
	
	
	
}
