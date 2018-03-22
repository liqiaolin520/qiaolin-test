package com.ascendant.activiti.mail.test;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.Deployment;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.ascendant.activiti.test.base.AbstractTest;

/**
 * 测试通过Activiti发送邮件任务。
* @author qiaolin
* @version 2017年4月1日
* 测试成功.....2017年4月1日10:46:03
*/

public class TestMail extends AbstractTest{

	@Before
	@Ignore
	public void init(){
		super.setUp();
	}
	
	/**
	 * 发送邮件。
	 */
	@Test
	@Ignore
	@Deployment(resources="mail/testMail.bpmn")
	public void testSendMail(){
		// 准备流程定义中表达式需要的参数。
		Map<String,Object> params = new HashMap<>();
		params.put("to", "tom@localhost");
		params.put("name", "tom");
		params.put("from", "qiao@localhost");
		// 启动流程。
		ProcessInstance instance = runtimeService.startProcessInstanceByKey("mail",params);
		Assert.assertNotNull(instance);
	}
	
}
