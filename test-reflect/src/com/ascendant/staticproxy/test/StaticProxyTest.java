package com.ascendant.staticproxy.test;

import com.ascendant.reflect.dao.UserDao;
import com.ascendant.reflect.dao.impl.UserDaoImpl;
import com.ascendant.staticproxy.impl.UserDaoImpl2;

/**
 * @author qiaolin
 * @date 2018年1月17日
 *
 */

public class StaticProxyTest {
	public static void main(String[] args) {
		UserDao userDao = new UserDaoImpl();
		
		UserDaoImpl2 userDao2 = new UserDaoImpl2(userDao);
		userDao2.add();
		
		
	}
}
