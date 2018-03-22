package com.qiaolin.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
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
public class MyRealm2 implements Realm{

	@Override
	public String getName() {
		return "myrealm2";
	}

	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof UsernamePasswordToken;
	}

	@Override
	public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal();
		String password = new String((char[])token.getCredentials());
		
		if(!"wang".endsWith(username)){
			throw new UnknownAccountException("用户名不存在");
		}
		
		System.out.println("myRealm2");
		return new SimpleAuthenticationInfo(username, password, getName());
	}

}
