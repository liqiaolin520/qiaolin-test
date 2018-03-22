package com.ascendant.cglib.test;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

/**
 *   cglib测试
 * @author qiaolin
 * @date 2018年1月17日
 *
 */

public class TargetTest {
	public static void main(String[] args) {
		//字节码增强器
		Enhancer en = new Enhancer();
		en.setSuperclass(TargetObject.class);
		// 设置目标对象拦截器，设置了这个当代理对象调用方法的时候她就会去执行他里面的 intercept 方法
		// en.setCallback(new TargetInvocationHandler());
		
		en.setCallbacks(new Callback []{new TargetInvocationHandler(), NoOp.INSTANCE, new TargetRsultFixed()});
		
		// 设置回掉过滤器，调用方法可以设置对不同的方法设置不同的回掉逻辑(拦截器)或者不去执行回掉
		en.setCallbackFilter(new TargetMethodCallbackFilter());
		
		TargetObject target = (TargetObject)en.create();
		
		System.out.println(target);
		System.out.println(target.method1("小明"));
		System.out.println(target.method2(100));
		System.out.println(target.method3(200));
		
	}
}
