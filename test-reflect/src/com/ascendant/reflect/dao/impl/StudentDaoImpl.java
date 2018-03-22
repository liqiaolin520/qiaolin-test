package com.ascendant.reflect.dao.impl;

import com.ascendant.reflect.dao.StudentDao;

/**
 *   
 * @author qiaolin
 * @date 2018年1月17日
 * 
 */

public class StudentDaoImpl implements StudentDao{

	@Override
	public void love() {
		System.out.println("学生爱谈恋啊");
	}

	@Override
	public void study() {
		System.out.println("我得心里只有一件事，那就是学习！");
	}

}
