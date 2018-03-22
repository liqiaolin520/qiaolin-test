package com.ascendant.activiti.test.form;

import org.activiti.engine.form.AbstractFormType;

/**
* @author qiaolin
* @version 2017年3月15日
* 自定义表单类型(javascript)
*/

@SuppressWarnings("serial")
public class JavascriptFormType extends AbstractFormType{

	@Override
	public String getName() {
		return "javascript"; // 这里对应的是表单中的type属性
 	}			 

	@Override
	public Object convertFormValueToModelValue(String propertyValue) {
		return propertyValue;   // 把表单中的内容转换成对象
	}

	@Override
	public String convertModelValueToFormValue(Object modelValue) {
		return (String) modelValue;   // 将Java对象转换成字符型
	}
	
}
