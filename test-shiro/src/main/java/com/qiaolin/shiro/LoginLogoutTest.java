package com.qiaolin.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.Test;

import junit.framework.Assert;

/**
 * 测试Shiro权限框架的登录与登出
 * @author qiaolin
 * @version 2017年4月18日
 * 
 */

public class LoginLogoutTest {

	@Test
	public void testHelloWrod(){
		// 1、获得SecurityManager工厂，此处使用的是ini文件初始化SecurityManager
		Factory<SecurityManager> factory =  new IniSecurityManagerFactory("classpath:shiro-jdbc-realm.ini"); 
		// 2、等到SecurityManager实例，并绑定给SecurityUtils						
		SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		// 3、得到Subject及创建用户名/密码身份验证Token(即用户身份/凭证)
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
		// 4、登录，即身份验证
		try {
			subject.login(token);
		} catch (AuthenticationException e) {
			// 5、抛出异常则表示身份验证失败。
			System.err.println(e.getMessage());
			 
		}  
		// 断言用户已经登陆。
		Assert.assertEquals(true, subject.isAuthenticated());
		subject.checkPermission("aa:user:create");
		
		
		// 6、登出
		subject.logout();
		// 断言用户已经退出
		Assert.assertEquals(false, subject.isAuthenticated());
	}
	
	@Test
	public void aa(){
		 ThreadContext.unbindSubject();
	}
	
}
