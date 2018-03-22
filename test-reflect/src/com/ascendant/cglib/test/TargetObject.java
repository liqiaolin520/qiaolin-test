package com.ascendant.cglib.test;

/**
 *   目标对象 
 *  没有实现接口,需要cglib来代理
 * @author qiaolin
 * @date 2018年1月17日
 *
 */

public class TargetObject {
	
	public String method1(String paramName){
		return paramName;
	}
	
	public int method2(int count){
		return count;
	}
	
	public int method3(int count){
		return count;
	}

	public String toString() {
		return "Target -> "+getClass();
	}
}

