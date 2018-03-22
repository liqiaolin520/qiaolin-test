package com.ascendant.reflect.invocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 *   
 * @author qiaolin
 * @date 2018年1月17日
 *
 */

public class MyInvocationHandler implements InvocationHandler{
	
	private Object target; // 要代理的对象
	
	/**
	 *  构造方法传入要代理的对象
	 * @param target 要代理的对象
	 */
	public MyInvocationHandler(Object target) {
		this.target = target;
	}
 
	/**
	 * set方法入参要代理的对象
	 * @param target 要代理的对象
	 */  
	public void setTarget(Object target) {
		this.target = target;
	}

	/**
	 *  代理对象真正来执行被代理的对象的方法的地方
	 */
	@Override 
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
	    System.out.println("执行 --> "+method + " 参数 --> "+Arrays.toString(args));
	     Object result = method.invoke(target, args);
	    System.out.println("日志 -->"+method.getName() + "完成");
	    return result;
	}

}
