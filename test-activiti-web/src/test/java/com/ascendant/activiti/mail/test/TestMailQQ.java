package com.ascendant.activiti.mail.test;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

/**
* @author qiaolin
* @version 2017年4月1日
* 测试QQ邮箱。
*/
public class TestMailQQ {
	
	@Rule
	public ActivitiRule ar = new ActivitiRule("activiti.mailQQ.xml");
	
	@Test
	@Deployment(resources="mail/testMail.bpmn")
	public void testQQMail(){
		Map<String,Object> params = new HashMap<>(); 
		params.put("to", "381913904@qq.com");
		params.put("name", "huangyifeng");
		params.put("from", "992004863@qq.com");
		ProcessInstance instance = ar.getRuntimeService().startProcessInstanceByKey("mail",params);
		Assert.assertNotNull(instance);
	}
}
