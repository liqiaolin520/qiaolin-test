package com.ascendant.cglib.test;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.CallbackFilter;

/**
 *  回掉过滤类
 * @author qiaolin
 * @date 2018年1月17日
 *
 */

public class TargetMethodCallbackFilter implements CallbackFilter{

	/**
	 * 过滤方法
	 * 返回值对应的是Enhancer对象setCallbacks的数组 下标
	 *  返回的值为数字，代表了Callback数组中的索引位置，要到用的Callback
	 */
	public int accept(Method method) {
		if(method.getName().equals("method1")){
			System.out.println("filter method1 == 0");
			return 0;
		}else if(method.getName().equals("method2")){
			System.out.println("filter method2 = 1");
			return 1;
		}else if (method.getName().equals("method3")) {
			System.out.println("filter method3 == 2");
			return 2;
		}
		
		return 0;
	}

}
