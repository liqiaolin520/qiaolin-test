package com.yszt.kafeitu.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;

/**
* @author qiaolin
* @version 2017年3月6日
* 测试用户和组。
*/

public class TestUserAndGroup {
	
	// 使用默认得activiti.cfg.xml作为参数
	@Rule
	public ActivitiRule ar = new ActivitiRule();
	
	/**
	 *  用户管理API测试 
	 */
	@Test
	public void testUser(){
		IdentityService is = ar.getIdentityService();
		// 创建一个用户
		User user = is.newUser("qiaoqiaolin");
		user.setEmail("992004863@qq.com");
		user.setFirstName("li");
		user.setLastName("qiaolin");
		user.setPassword("1234");
		// 将用户保存到数据库中 
		is.saveUser(user);
		// 查询数据库，看用户是否添加成功。
		User user1 = is.createUserQuery().userId("qiaoqiaolin").singleResult();
		assertNotNull(user1);
		// 删除用户
		is.deleteUser("qiaoqiaolin");
		User user2 = is.createUserQuery().userId("qiaoqiaolin").singleResult();
		assertNull(user2);
	}
	
	/**
	 * 组管理测试
	 */
	@Test
	public void testGroup(){
		IdentityService service = ar.getIdentityService();
		// 通过身份服务接口创建一个组
		Group group = service.newGroup("deptleader");
		group.setName("部门领导");
		group.setType("assignment");
		// 将组保存到数据库中
		service.saveGroup(group);
		// 查询数据库中是否有这个组
		Group group1 = service.createGroupQuery().groupId("deptleader").singleResult();
		assertNotNull(group1);
		// 删除该组
		service.deleteGroup("deptleader");
		// 查询该组是否还存在
		Group group2 = service.createGroupQuery().groupId("deptleader").singleResult();
		assertNull(group2);
	}
	
	/**
	 * 组和用户关联
	 */
	@Test
	public void testGroupJoinUser(){
		IdentityService service = ar.getIdentityService();

		// 创建一个组并写入数据库
		/*Group group = service.newGroup("deptleader");
		group.setName("部门领导");
		group.setType("assginment");
		service.saveGroup(group);
		Group group0 = service.newGroup("deptManager");
		group0.setName("部门经理");
		group0.setType("assginment");
		service.saveGroup(group0);*/
		
		// 创建一个用户并写入数据库
		/*User user = service.newUser("qiaolin");
		user.setEmail("992004863@qq.com");
		user.setFirstName("李");
		user.setLastName("巧林");
		user.setPassword("123456");
		service.saveUser(user);
		User user0 = service.newUser("qiaoqiaolin");
		user0.setEmail("1234567@qq.com");
		user0.setFirstName("a");
		user0.setLastName("bb");
		user0.setPassword("123456");
		service.saveUser(user0);*/
		
		// 将用户绑定组
		//service.createMembership("qiaolin", "deptleader");
		/*service.createMembership("qiaoqiaolin", "deptleader");
		service.createMembership("qiaolin", "deptManager");*/
		// 通过组来查询用户
		List<User> users = service.createUserQuery().memberOfGroup("deptleader").list();
		for (User user2 : users) {
			System.out.println(user2.getId()+", "+user2.getFirstName()+user2.getLastName());
		}
		
		// 通过用户来查询组
		List<Group> group3 = service.createGroupQuery().groupMember("qiaoqiaolin").list();
		for (Group group2 : group3) {
			System.out.println(group2.getId()+", "+group2.getName());
		}
		
		/*service.deleteGroup("deptManager");
		service.deleteGroup("deptleader");*/
		service.deleteUser("qiaoqiaolin");
		service.deleteUser("qiaolin");
	}
		
		
	
	
	
	
	
	
	
}
