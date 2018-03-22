package activiti.history;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricProcessInstance;
import org.junit.Test;

/**
 *  根据流程定义的Key来查新历史流程实例
 * @author qiaolin
 *
 */
public class QueryHistoricProcessInstance {
	
	@Test
	public void queryHistoricProcessInstance(){
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		List<HistoricProcessInstance> hpi = processEngine.getHistoryService()
			.createHistoricProcessInstanceQuery() // 创建历史流程实例查询对象
			.processDefinitionKey("myDay") // 根据流程定义的Key作为查询条件
			.orderByProcessInstanceStartTime().desc() // 根据流程开始的时间降序查询
			.list(); // 返回结果集
	
		// 遍历查看结果
		for (HistoricProcessInstance hPInstance : hpi) {
			System.out.println("id"+hPInstance.getId());
			System.out.println("pid"+hPInstance.getProcessDefinitionId());
			System.out.println("开始时间"+hPInstance.getStartTime().toLocaleString());
			System.out.println("结束时间"+(hPInstance.getEndTime()==null?null:hPInstance.getEndTime().toLocaleString()));
			System.out.println("duration"+hPInstance.getDurationInMillis());
			System.out.println("-------------------------");
		}
	
	
	}
}
