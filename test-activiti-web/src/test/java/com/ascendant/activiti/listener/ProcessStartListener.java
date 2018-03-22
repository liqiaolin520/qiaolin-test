package com.ascendant.activiti.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * 流程启动监听器.
 * @author qiaolin
 * @version 2017年3月28日
 * 
 */

@SuppressWarnings("serial")
public class ProcessStartListener implements ExecutionListener{

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("ProcessStartListener", true);
		System.out.println(this.getClass().getSimpleName()+" , "+execution.getEventName());
		   
	}

}

