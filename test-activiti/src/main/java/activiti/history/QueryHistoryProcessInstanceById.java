package activiti.history;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricProcessInstance;
import org.junit.Test;

/**
 * 根据流程实例Id查询历史流程实例
 * @author qiaolin
 *
 */
public class QueryHistoryProcessInstanceById {

	@Test
	public void queryHistoryProcessInstanceById(){
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		// 流程实例Id 
		String processInstanceId = "10001";
		HistoricProcessInstance hpi = processEngine.getHistoryService()
			.createHistoricProcessInstanceQuery() // 创建历史流程实例查询
			.processInstanceId(processInstanceId) // 使用流程实例ID查询
			.singleResult();
		System.out.println("流程定义ID"+hpi.getProcessDefinitionId());
		System.out.println("流程实例ID"+hpi.getId());
		System.out.println(hpi.getStartTime()+" "+hpi.getEndTime()+" "+hpi.getDurationInMillis());
	
	}
}
