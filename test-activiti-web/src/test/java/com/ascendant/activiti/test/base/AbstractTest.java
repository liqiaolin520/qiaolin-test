package com.ascendant.activiti.test.base;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;

/**
* @author qiaolin
* @version 2017年3月6日
*	测试抽象类,用于准备服务接口
*/
public abstract class AbstractTest {
	
	@Rule
	public ActivitiRule ar = new ActivitiRule();
	protected RepositoryService repositoryService;
	protected RuntimeService runtimeService;
	protected TaskService taskService;
	protected HistoryService historyService;
	protected IdentityService identityService;
	protected ManagementService managementService;
	protected FormService formService;
	
	public void setUp(){ 
		repositoryService = ar.getRepositoryService();
		runtimeService = ar.getRuntimeService();
		taskService = ar.getTaskService();
		historyService = ar.getHistoryService();
		identityService = ar.getIdentityService();
		managementService = ar.getManagementService();
		formService = ar.getFormService();
	}
	
}
