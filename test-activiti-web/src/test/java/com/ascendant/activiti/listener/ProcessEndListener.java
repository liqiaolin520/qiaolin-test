package com.ascendant.activiti.listener;

import java.io.Serializable;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 *  流程结束监听器。
 * @author qiaolin
 * 
 */
@SuppressWarnings("serial")
public class ProcessEndListener implements ExecutionListener,Serializable{

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("ProcessEndListener", true);
		System.out.println(this.getClass().getSimpleName()+" , "+execution.getEventName());
	}
	
}
