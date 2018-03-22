package com.ascendant.reflect.test;

import java.lang.reflect.Proxy;

import com.ascendant.reflect.dao.UserDao;
import com.ascendant.reflect.dao.impl.UserDaoImpl;
import com.ascendant.reflect.entity.User;
import com.ascendant.reflect.invocation.MyInvocationHandler;

/**
 *    测试 动态代理 
 * @author qiaolin
 * @date 2018年1月17日
 *
 */

public class MyTest {
	public static void main(String[] args) {
		// 创建对象
		UserDao userDao = new UserDaoImpl();

		// 准备代理对象,InvocationHandler的子类
		MyInvocationHandler handler = new MyInvocationHandler(userDao);
		
		// 开始代理
		UserDao proxy = (UserDao)Proxy.newProxyInstance(userDao.getClass().getClassLoader(), userDao.getClass().getInterfaces(), handler);
		
		proxy.add();
		
		proxy.delete();
		
		proxy.update();
		
		User user = proxy.findUser(20);
		System.out.println(user);
		
		System.out.println(userDao);
		System.out.println(userDao.getClass());
		System.out.println(proxy);
		System.out.println(proxy.getClass());
		System.out.println(userDao == proxy);
		
		
		// 测试目标对象没有实现接口
		// 是否可以代理
		Love love = new Love();
//		MyInvocationHandler myHandler = new MyInvocationHandler(love);
//		Love loveOne = (Love)Proxy.newProxyInstance(love.getClass().getClassLoader(), love.getClass().getInterfaces(), myHandler);
//		loveOne.loveAnimal();
		// 结论，不可以，大概他是弄了个匿名对象啥的...
		// 一定要接口，代理类的引用(类型)要是接口    还有代理对象调用的方法也必须是在接口中定义的
		// 没有接口可以使用Cglib
		Love love1 = new CgLibProxy(love).getProxyInstance();
		love1.loveAnimal();
	}
}
