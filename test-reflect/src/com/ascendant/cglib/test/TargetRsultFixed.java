package com.ascendant.cglib.test;

import net.sf.cglib.proxy.FixedValue;

/**
 *   目标对象返回锁定，无论被代理对象返回什么值，都返回固定值
 * @author qiaolin
 * @date 2018年1月17日
 *
 */

public class TargetRsultFixed implements FixedValue{
	
	/**
	 * 该类实现FixedValue接口，同时锁定返回值为888
	 */
	@Override
	public Object loadObject() throws Exception {
		System.out.println("锁定结果！");
		Object obj = 888;
		return obj;
	}

}
