package com.ascendant.reflect.test;

import java.lang.reflect.Method;
import java.util.Arrays;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 *   Cglib 动态代理
 * @author qiaolin
 * @date 2018年1月17日
 *
 */

public class CgLibProxy implements MethodInterceptor{
	private Object target;// 代理目标
	
	public CgLibProxy(Object target){
		this.target = target;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getProxyInstance(){
		// 1、工具类
		Enhancer en = new Enhancer();
		// 设置父类
		en.setSuperclass(target.getClass());
		// 设置回掉函数
		en.setCallback(this);
		// 创建子类
		return (T)en.create();
	}

	@Override
	public Object intercept(Object paramObject, Method paramMethod, Object[] paramArrayOfObject,
			MethodProxy paramMethodProxy) throws Throwable {
		
		System.out.println("开始 --> "+paramMethod+" 参数 --> "+Arrays.toString(paramArrayOfObject));
		Object result = paramMethod.invoke(target, paramArrayOfObject);
		System.out.println("执行完成 --> "+paramMethod);
		
		return result;
	}
	
	
}
