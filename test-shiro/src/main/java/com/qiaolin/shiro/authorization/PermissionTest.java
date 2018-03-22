package com.qiaolin.shiro.authorization;

import org.apache.shiro.authz.UnauthorizedException;
import org.junit.Test;

import junit.framework.Assert;

/**
 * 测试某个用户权限
 * @author qiaolin
 * @date 2017年4月21日
 * 
 */
public class PermissionTest extends AbstractTest{

	
	@Test
	public void testIsPermitted(){
		login("classpath:authorization/shiro-permission.ini", "zhang", "123");
		// 判断用户是否拥有user:create的权限
		Assert.assertTrue(getSubject().isPermitted("user:create"));
		// 判断用户是否拥有user:update的权限
		Assert.assertTrue(getSubject().isPermitted("user:delete"));
		// 判断用户没有这个权限
		Assert.assertFalse(getSubject().isPermitted("user:view"));
	}
	
	/**
	 * 当测试抛出UnAuthorizedException异常时测试通过。
	 */
	@Test(expected=UnauthorizedException.class)
	public void testCheckPermission(){
		login("classpath:authorization/shiro-permission.ini", "zhang", "123");
		// 断言拥有user:create权限
		getSubject().checkPermission("user:create");
		// 断言拥有 user:delete and user:update
		getSubject().checkPermissions("user:delete","user:update");
		// 断言拥有user:view权限，失败抛UnauthorizedException
		getSubject().checkPermissions("user:view");
	}
	
	/**
	 * 测试字符串通配符权限.
	 */
	@Test    //wildcard
	public void testWildcardPermission(){
		login("classpath:authorization/shiro-permission-wildcard.ini", "qiao", "123");
		// 断言用户有system:user 的update权限
		getSubject().checkPermission("system:user:delete");
		// 单个资源多个权限
		getSubject().checkPermissions("system:user:delete","system:user:update");
		// 单个资源全部权限
		getSubject().checkPermissions("system:user:create,delete,update,view");
		getSubject().checkPermission("system:user:*");
		getSubject().checkPermission("system:user");
		
	}
	
	/**
	 * 所有资源全部权限
	 */
	@Test
	public void testAllResourcePermission(){
		login("classpath:authorization/shiro-permission-wildcard.ini", "aya", "123");
		// 断言用户拥有view的资源 
		getSubject().checkPermission("user:view");
		// 断言用户拥有system:view的权限
		getSubject().checkPermission("system:view");
	}
	
	/**
	 *  实例级别的权限。
	 *   单个实例单个权限与多个权限与所有权限
	 */
	@Test
	public void testInstancePermission(){
		login("classpath:authorization/shiro-permission-wildcard.ini", "instance2", "123");
		// 断言user的1实例view的权限
		getSubject().checkPermission("user:view:1");
		// 断言user的1实例的update,delete
		getSubject().checkPermissions("user:update:1","user:delete:1");
		
	}
	
	/**
	 *  实例级别的权限。
	 *   单个实例单个权限与多个权限与所有权限
	 */
	@Test
	public void testInstance2Permission(){
		login("classpath:authorization/shiro-permission-wildcard.ini", "instance3", "123");
		// 断言user的1实例view的权限
		getSubject().checkPermissions("user:auth:1","user:auth:2");
	}
	
	/**
	 *  实例级别的权限。
	 *  资源user的所有实例拥有所有权限
	 */
	@Test
	public void testInstance3Permission(){
		login("classpath:authorization/shiro-permission-wildcard.ini", "instance4", "123");
		// 断言user的1实例view的权限
		getSubject().checkPermissions("user:auth:1","user:auth:2");
		getSubject().checkPermissions("user:aaa:1","user:bbs:2");
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
