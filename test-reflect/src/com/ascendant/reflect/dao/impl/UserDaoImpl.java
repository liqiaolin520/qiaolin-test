package com.ascendant.reflect.dao.impl;

import com.ascendant.reflect.dao.UserDao;
import com.ascendant.reflect.entity.User;

/**
 *   
 * @author qiaolin
 * @date 2018年1月17日
 *
 */

public class UserDaoImpl implements UserDao{

	@Override
	public void add() {
		System.out.println("增加User");
	}

	@Override
	public final void update() {
		System.out.println("修改User");
	}

	@Override
	public void delete() {
		System.out.println("删除User");
	}

	@Override
	public User findUser(int id) {
		User user = new User();
		user.setId(id);
		user.setAge(18);
		user.setPassword("123456");
		user.setUserName("admin");
		return user;
	}

}
