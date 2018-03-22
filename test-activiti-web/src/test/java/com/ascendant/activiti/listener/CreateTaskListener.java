package com.ascendant.activiti.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.TaskListener;

/**
* @author qiaolin
* @version 2017年3月28日
*
*/

@SuppressWarnings("serial")
public class CreateTaskListener implements TaskListener{
	private Expression content; // 表达式的注入字段必须为Expression类型
	private Expression task;
	
	
	@Override
	public void notify(DelegateTask delegateTask) {
		System.out.println(task.getValue(delegateTask));
		delegateTask.setVariable("setCreateTask", delegateTask.getEventName()+","+content.getValue(delegateTask));
		System.out.println(delegateTask.getEventName()+",分配给 "+delegateTask.getAssignee());
		delegateTask.setAssignee("xiaohuang");
	}

}
