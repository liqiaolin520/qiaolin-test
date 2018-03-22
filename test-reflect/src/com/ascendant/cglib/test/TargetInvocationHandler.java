package com.ascendant.cglib.test;

import java.lang.reflect.Method;
import java.util.Arrays;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 目标对象拦截器
 *  Cglib会回掉MethodInterceptor 接口方法拦截，来实现你得业务逻辑
 *   类似JDK中的 invocationHandler接口
 * @author qiaolin
 * @date 2018年1月17日
 *
 */

public class TargetInvocationHandler implements MethodInterceptor{

	/**
	 * 代理对象调用方法时回来执行这个方法
	 *  Object为由CGLib动态生成的代理类实例，Method为上文中实体类所调用的被代理的方法引用，
	 *  Object[]为参数值列表，MethodProxy为生成的代理类对方法的代理引用。
	 * @param obj 目标对象
	 * @param method 调用的方法对象
	 * @param args 方法的参数
	 * @param proxy 代理对象
	 * @result 执行方法后的返回值
	 */
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		System.out.println("调用前 --> " + method + " 参数 --> "+Arrays.toString(args));
		Object result = proxy.invokeSuper(obj, args);
		System.out.println("调用后 --> " + method); 
		
		return result;
	}
  
}
