package com.ascendant.activiti.multipleInstance.test;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.Deployment;
import org.junit.Before;
import org.junit.Test;

import com.ascendant.activiti.test.base.AbstractTest;

/**
 * 测试多实例的JavaService任务。
 * @author qiaolin
 * @date 2017年4月7日
 *
 */

public class JavaServiceMultipleInstanceTest extends AbstractTest{
	
	@Before
	public void init(){
		super.setUp();
	}
	
	/**
	 *  测试结果标识JavaService任务因为他是一创建立马完成的任务，所以顺序方式执行与
	 *  并行方式执行都是差不多一样的效果。
	 */
	@Test
	@Deployment(resources="multipleInstance/JavaServiceMultipleInstance.bpmn")
	public void testMultipleInstanceFromJavaService(){
		Map<String,Object> params = new HashMap<>();
		params.put("count", 0);
		params.put("loop", 3);
		ProcessInstance instance = runtimeService
				.startProcessInstanceByKey("multipleInstance",params);

		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
	}
	
	
	
}
