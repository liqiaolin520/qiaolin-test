package com.ascendant.staticproxy.impl;

import com.ascendant.reflect.dao.UserDao;
import com.ascendant.reflect.entity.User;

/**
 *   静态代理类
 * @author qiaolin
 * @date 2018年1月17日
 *
 */

public class UserDaoImpl2 implements UserDao{
	private UserDao target; // 代理目标
	
	/**
	 * 构造方法入参代理目标
	 * @param target
	 */
	public UserDaoImpl2(UserDao target){
		this.target = target;
	}
	
	
	@Override
	public void add() {
		System.out.println("开始...");
		target.add();
		System.out.println("结束....");
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User findUser(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
