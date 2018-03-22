package com.ascendant.activiti.util;

import javax.servlet.http.HttpSession;

import org.activiti.engine.identity.User;

/**
* @author qiaolin
* @version 2017年3月13日
* 用户工具类
*/

public class UserUtil {
	private final static String USER = "user";
	
	/**
	 * 将用户保存到Session中
	 * @param session
	 * @param user
	 */
	public static void saveUserToSession(HttpSession session,User user){
		session.setAttribute(USER, user);
	}
	
	/**
	 * 从Session中取出用户
	 */
	public static User getUserFromSession(HttpSession session){
		Object user = session.getAttribute(USER);
		return (User)user;
	}
	
}
