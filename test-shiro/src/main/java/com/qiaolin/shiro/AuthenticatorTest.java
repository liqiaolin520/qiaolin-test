package com.qiaolin.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

/**
 * 测试认证器
 * @author qiaolin
 * @version 2017年4月20日
 * 
 */
@SuppressWarnings("unused")
public class AuthenticatorTest {

	
	/**
	 *  测试AllSuccessfulStrategy
	 *   也就是配置的多个realm 要全成功。
	 *    测试结果:完美通过
	 */

	@Test
	public void allSuccessfulStrategy(){
		login("classpath:shiro-authenticator-all-success.ini");
		Subject subject = SecurityUtils.getSubject();
		// 得到一个身份的结合，其包含了Realm验证成功的身份信息。
		PrincipalCollection principals =  subject.getPrincipals();
		System.out.println();
		System.out.println();
	}
	
	/**
	 *  测试AllSuccessfulStrategy
	 *  	也就是配置多个realm,有一个不成功,realm验证失效
	 *  	 和想象中的一样，在经过myRealm2时候抛出异常，一个失败，realm验证失败。
	 */
	@Test
	public void allSuccessfulStrategyWithFail(){
		login("classpath:shiro-authenticator-all-fail.ini");
		Subject subject = SecurityUtils.getSubject();
	}
	
	/**
	 * 返回所有认证成功realm的信息.
	 *  modelarRealmAuthenticator默认使用AtLeastOneSuccessfulStrategy策略
	 */
	@Test
	public void atLeastOneSuccessFulStrategy(){
		login("classpath:shiro-authenticator-atLeastOne.ini");
		Subject subject = SecurityUtils.getSubject();
		System.out.println();
		System.out.println();
	}
	
	/** 
	 *  返回认证成功的第一个realm信息。。
	 */
	@Test
	public void firstSuccessfulStrategy(){
		login("classpath:shiro-authenticator-first.ini");
		Subject subject = SecurityUtils.getSubject();
		System.out.println();
		System.out.println();
	}
	
	 
	/**
	 * 通用登录逻辑代码
	 * @param configFilePath 配置文件路径
	 */
	public void login(String configFilePath){
		// 1、获取securityManager工厂,这里使用ini配置文件的初始化方式。
		Factory<SecurityManager> fatory = new IniSecurityManagerFactory(configFilePath);
		// 得到securityManager的实例并绑定给SecurityUtils
		SecurityManager securityManager = fatory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		// 得到Subject及创建用户名和密码身份验证Token（即用户身份和密码）
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
		// 执行登录
		subject.login(token);
	}
	
}
