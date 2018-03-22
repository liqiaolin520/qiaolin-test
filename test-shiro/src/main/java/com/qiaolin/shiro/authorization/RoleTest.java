package com.qiaolin.shiro.authorization;

import java.util.Arrays;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.junit.Test;

import junit.framework.Assert;

/**
 *
 * @author qiaolin
 * @version 2017年4月20日
 * 
 */
public class RoleTest extends AbstractTest {

	@Test
	public void testRole(){
		// 登录
		login("classpath:authorization/shiro-role.ini", "zhang", "123");
		// 断言登录的用户拥有这个角色
		Assert.assertTrue(getSubject().hasRole("role1"));
		// 断言登录的用户拥有集合中的全部角色
		Assert.assertTrue(getSubject().hasAllRoles(Arrays.asList("role1","role2")));
		// 以此判断集合中的角色当前登录的用户是否拥有，返回一个boolean的数据
		boolean[] b = getSubject().hasRoles(Arrays.asList("role1","role2","role3"));
		// 断言我们理想中的结果
		Assert.assertTrue(b[0]);
		Assert.assertTrue(b[1]);
		Assert.assertFalse(b[2]);
	}
	
	/**
	 * 使用subject的检查角色，
	 * 	expected:当测试程序抛出某个异常时，测试通过。
	 */
	@Test(expected=UnauthorizedException.class)
	public void testCheckRole(){
		login("classpath:authorization/shiro-role.ini", "zhang", "123");
		// 断言这个用户拥有role1这个角色
		getSubject().checkRole("role1");
		// 断言这个用户拥有这一群角色，这里会抛出UnanthorizadException异常
		getSubject().checkRoles("role1","role3");
		
	}
	
	
	
	
	
}
