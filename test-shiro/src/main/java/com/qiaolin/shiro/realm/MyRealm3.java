package com.qiaolin.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.Realm;

/**
 *
 * @author qiaolin
 * @version 2017年4月20日
 * 
 */
public class MyRealm3 implements Realm {

	@Override
	public String getName() {
		return "myRealm3";
	}

	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof UsernamePasswordToken;
	}

	/**
	 * 认证策略
	 */
	@Override
	public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// 获得用户名
		String username = (String) token.getPrincipal();
		String password = new String((char[])token.getCredentials());
		
		if(!"zhang".endsWith(username)){
			throw new UnknownAccountException("用户名错误++++++myRealm3");
		}
		
		if(!"123".endsWith(password)){
			throw new IncorrectCredentialsException("密码错误++++++myRealm3");
		}
		
		return new SimpleAuthenticationInfo(username+"@qq.com", password, getName());
	}

}
