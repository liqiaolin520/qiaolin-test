package com.qiaolin.shiro.authorization;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;

/**
 * 测试基类
 * @author qiaolin
 * @version 2017年4月20日
 * 
 */
public class AbstractTest {
	
	/**
	 * 登录
	 * @param configFilePath 初始化配置文件路径
	 * @param username 用户名
	 * @param password 用户密码
	 */
	protected void login(String configFilePath,String username,String password){
		// 1、通过ini配置文件方式工厂来获取SecurityManager
		Factory<SecurityManager> factory = new IniSecurityManagerFactory(configFilePath);
		SecurityManager securityManager = factory.getInstance();
		// 2、将SecurityManager注入到SecurityUtils中
		SecurityUtils.setSecurityManager(securityManager);
		// 3、通过SecurityUtils来获取subject主体
		Subject subject = SecurityUtils.getSubject();
		// 4、准备一个用于登录的token对象
		UsernamePasswordToken user = new UsernamePasswordToken(username,password);
		// 5、执行登录
		subject.login(user);
	}
	
	/**
	 *  利于子类测试便于获取subject
	 * @return
	 */
	protected Subject getSubject(){
		return SecurityUtils.getSubject();
	}
	
	/**'
	 *  退出登录时解绑subject到线程，否则会影响下一次的测试。
	 */
	@After
	public void colse(){
		ThreadContext.unbindSubject();
	}
	
	
	
}
