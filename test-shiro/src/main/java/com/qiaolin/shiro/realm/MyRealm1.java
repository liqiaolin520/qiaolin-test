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
 * 自定义域，realm
 * Realm：域，Shiro 从从Realm获取安全数据（如用户、角色、权限），就是说SecurityManager
 *	要验证用户身份，那么它需要从Realm获取相应的用户进行比较以确定用户身份是否合法；
 *	也需要从Realm得到用户相应的角色/权限进行验证用户是否能进行操作；可以把Realm看
 *  成DataSource ， 即安全数据源。
 * @author qiaolin
 * @version 2017年4月18日
 * 
 */
public class MyRealm1 implements Realm{
	
	/**
	 * 唯一的realm标识
	 */
	@Override
	public String getName() {
		return "myrealm1";
	}

	/**
	 * 判断realm是否支持此token
	 */
	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof UsernamePasswordToken;
	}

	/**
	 *  通过此方法来获得token的认证信息,认证用户
	 */
	@Override
	public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// 通过token获取身份
		String account = (String) token.getPrincipal();
		// 通过token获取证明
		String password = new String((char[])token.getCredentials());
		// 如果账号不存在
		if(!"zhang".equals(account)){
			throw new UnknownAccountException("用户名不存在！");
		}
		// 如果密码不正确
		if(!"123".equals(password)){
			throw new IncorrectCredentialsException("密码错误。");
		}
		System.out.println("myRealm1");
		// 如果身份认证验证成功，返回一个AuthenticationInfo实现：
		AuthenticationInfo ai = new SimpleAuthenticationInfo(account, password, getName());
		return ai;
	}

}
