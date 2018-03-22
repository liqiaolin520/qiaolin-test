package com.yszt.kafeitu.test1;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
* @author qiaolin
* @version 2017年3月6日
*
*/
public class UserAndGroupInUserTaskTest extends AbstractTest {
	
	/**
	 * 初始化，创建用户和组
	 */
	@Before
	public void setUp() {
		super.setUp();
		// 创建测试组
		Group group = identityService.newGroup("deptleader");
		group.setName("部门领导");
		group.setType("assignment");
		identityService.saveGroup(group);
		// 创建测试用户
		User user = identityService.newUser("qiaolin");
		user.setEmail("992004863@qq.com");
		user.setLastName("巧林");
		user.setFirstName("李");
		user.setPassword("123456");
		identityService.saveUser(user);
		// 将用户授权到测试组中
		identityService.createMembership("qiaolin", "deptleader");
	}
	
	
	@Test
	@Deployment(resources="UserAndGroupInTask.bpmn")
	public void testUserAndGroupInTask(){
		// 启动该流程
		ProcessInstance instance = runtimeService.startProcessInstanceByKey("userAndGroupInTask");
		assertNotNull(instance);
		System.out.println(instance);
		Task task = taskService.createTaskQuery().taskCandidateUser("qiaolin").singleResult();
		taskService.claim(task.getId(), "qiaolin");
		taskService.complete(task.getId());
	} 
	 
	/**
	 * 测试用户组中包含多个用户在用户任务中的应用
	 * 同理: activiti:candidateUsers 也是跟这个用户组一样的，谁先获得
	 *  任务的处理权其他人就失去了任务接收的能力。
	 */
	@Test
	@Deployment(resources="UserAndGroupInTask.bpmn")
	public void testGroupAndUsers(){
		// 在原始初始化的用户增加用户
		User jack = identityService.newUser("jackChen");
		jack.setEmail("www.jack@qq.com");
		jack.setLastName("chen");
		jack.setFirstName("jack");
		jack.setPassword("12345");
		identityService.saveUser(jack);
		identityService.createMembership("jackChen", "deptleader");
		
		ProcessInstance instance = runtimeService.startProcessInstanceByKey("userAndGroupInTask");
		assertNotNull(instance);
		
		// 查询 qiaolin的任务
		Task qiaolin = taskService.createTaskQuery().taskCandidateUser("qiaolin").singleResult();
		assertNotNull(qiaolin);
		
		// 查询jackChen的任务
		Task jackTask = taskService.createTaskQuery().taskCandidateUser("jackChen").singleResult();
		assertNotNull(jackTask);
		
		// jackChen接收任务
		taskService.claim(jackTask.getId(), "jackChen");
		
		// 再次查询qiaolin的任务
		qiaolin = taskService.createTaskQuery().taskCandidateUser("qiaolin").singleResult();
		assertNull(qiaolin);
	}
	
	
	
	
	
	/**
	 * 方法执行完，清除用户和组
	 */
	@After
	public void clare(){
		identityService.deleteMembership("qiaolin", "deptleader");
		identityService.deleteGroup("deptleader");
		identityService.deleteUser("qiaolin");
	}
	
	
	
}
