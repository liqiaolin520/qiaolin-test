package com.ascendant.reflect.dao;

import com.ascendant.reflect.entity.User;

/**
 *   
 * @author qiaolin
 * @date 2018年1月17日
 *
 */

public interface UserDao {
	
	void add();
	
	void update();
	
	void delete();
	
	User findUser(int id);
}
