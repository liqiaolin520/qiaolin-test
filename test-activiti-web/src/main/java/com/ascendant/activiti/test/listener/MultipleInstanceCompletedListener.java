package com.ascendant.activiti.test.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * 多实例用户任务下监听器，用于监听批准结果，统计审批的同意的数量。
 * @author qiaolin
 * @date 2017年4月7日
 *
 */

@SuppressWarnings("serial")
public class MultipleInstanceCompletedListener implements TaskListener{

	@Override
	public void notify(DelegateTask delegateTask) {
		String approve = (String) delegateTask.getVariable("approve");
		if("true".equals(approve)){
			String data = (String)delegateTask.getVariable("approvedCounter");
			delegateTask.setVariable("approvedCounter", Long.parseLong(data)+1);
		}
	}

}
