package com.ascendant.activiti.el.test;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.ascendant.activiti.test.base.AbstractTest;

/**
* @author qiaolin
* @version 2017年3月27日
*  测试Activiti中EL表达式的用法。
*/
@Ignore
public class TestEL extends AbstractTest {
	
	/**
	 * 初始化核心操作对象,以及七大服务接口。
	 */
	@Before
	public void init(){
		setUp();
	}
	
	/**
	 * 代码测试。
	 */
	@Test
	@Ignore
	@Deployment(resources = "el/elTest.bpmn")
	public void testActivitiEl(){
		// 实例化一个自定义bean对象.
		MyBean myBean = new MyBean();
		// 准备流程需要的数据
		Map<String,Object> params = new HashMap<>();
		params.put("mybean", myBean);
		String name = "qiaoqiaolin";
		params.put("name", name);
		// 设置流程启动人
		identityService.setAuthenticatedUserId("罗斯福");
		// 设置业务Key
		String businessKey = "8888";
		// 启动流程
		ProcessInstance instance = runtimeService.startProcessInstanceByKey("el",businessKey,params);
		// 验证我们猜测结果。
		Assert.assertEquals("罗斯福", runtimeService.getVariable(instance.getId(), "startUserIdForTest"));
		Assert.assertEquals("qiaoqiaolinprint(String name)", runtimeService.getVariable(instance.getId(), "returnValue"));
		Assert.assertEquals(businessKey, runtimeService.getVariable(instance.getId(), "businessKey"));
		// 查询任务。
		Task task = taskService.createTaskQuery().processInstanceId(instance.getId()).singleResult();
		// 根据任务获得任务监听器中表达式产生的流程变量是不是跟我们猜测的一样。
		String obj = (String) taskService.getVariable(task.getId(), "setByTask");
		Assert.assertEquals("啦啦啦啦啦啦, "+name, obj);
	}
	
	/**
	 * 测试利用activiti.cfg.xml配置文件代理bean对象
	 * 
	 *  失败,出现找不到mybean的异常，但是在activiti.cfg.xml中已经引用，
	 *   未知原因。要测试EL表达式在Activiti中的应用请使用SpringActivitiEL类测试。
	 */
	@Test
	@Ignore
	@Deployment(resources = "el/elTest.bpmn")
	public void testActivitiEl1(){
		// 准备流程需要的数据
		Map<String,Object> params = new HashMap<>();
		String name = "qiaoqiaolin";
		params.put("name", name);
		// 设置流程启动人
		identityService.setAuthenticatedUserId("罗斯福");
		// 设置业务Key
		String businessKey = "8888";
		// 启动流程
		ProcessInstance instance = runtimeService.startProcessInstanceByKey("el",businessKey,params);
		// 验证我们猜测结果。
		Assert.assertEquals("罗斯福", runtimeService.getVariable(instance.getId(), "startUserIdForTest"));
		Assert.assertEquals("qiaoqiaolinprint(String name)", runtimeService.getVariable(instance.getId(), "returnValue"));
		Assert.assertEquals(businessKey, runtimeService.getVariable(instance.getId(), "businessKey"));
		// 查询任务。
		Task task = taskService.createTaskQuery().processInstanceId(instance.getId()).singleResult();
		// 根据任务获得任务监听器中表达式产生的流程变量是不是跟我们猜测的一样。
		String obj = (String) taskService.getVariable(task.getId(), "setByTask");
		Assert.assertEquals("啊啊, "+name, obj);
	}
	
	@Test
	@Deployment(resources = "el/elTest.bpmn")
	public void testActivitiElSpring(){
		
	}
	
	
}

/**'
 *  测试EL表达式bean. 用于EL表达式的bean必须实现Serializable接口
 * @author qiaolin
 *
 */
@SuppressWarnings("serial")
class MyBean implements Serializable{
	
	public void print(){
		System.out.println("the method is print()");
	}
	
	public String print(String name){
		System.out.println("print name  = "+name);
		return name += "print(String name)";
	}
	
	/**
	 *  接收内置的引擎对象 task
	 * @param task
	 */
	public void invokeTask(DelegateTask task){
		System.out.println(task);
		task.setVariable("setByTask", "啊啊, "+task.getVariable("name"));
	}
	
	/**
	 * 接收内置对象execution。
	 * @param execution
	 * @return
	 */
	public String printKey(DelegateExecution execution){
		String businessKey = execution.getProcessBusinessKey();
		System.out.println("pricessId is "+execution.getProcessInstanceId()+" , BusinessKey is " +businessKey );
		System.out.println(execution);
		return businessKey;
	}
	
}