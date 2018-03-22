package com.ascendant.activiti.listener;

import java.io.Serializable;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
* @author qiaolin
* @version 2017年3月29日
* 任务分配监听器。
*/

@SuppressWarnings("serial")
public class TaskAssigneeListener implements TaskListener,Serializable{

	@Override
	public void notify(DelegateTask delegateTask) {
		System.out.println("任务分配给了 :"+delegateTask.getAssignee());
	}

}
