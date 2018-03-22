package activiti.task;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricTaskInstance;
import org.junit.Test;

/**
 *  查询某一流程执行经历过的任务节点
 * @author qiaolin
 *
 */
public class QueryHistoricTask {

	@Test
	public void queryHistoricTask(){
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		// 定义流程实例Id
		String processInstanceId = "39255";
		List<HistoricTaskInstance> hti = processEngine.getHistoryService()
			.createHistoricTaskInstanceQuery()    // 创建历史任务实例查询
			.processInstanceId(processInstanceId)  // 使用流程实例Id查询
			.orderByHistoricTaskInstanceStartTime().asc()  // 根据执行任务节点的起始时间升序查询。
			.list();  // 返回结果集
		
		for (HistoricTaskInstance h1 : hti) {
			System.out.println("taskId:"+h1.getId());
			System.out.println("name:"+h1.getName());
			System.out.println("pdId"+h1.getProcessDefinitionId());
			System.out.println("pid:"+h1.getProcessInstanceId());
			System.out.println("assignee:"+h1.getAssignee());
			System.out.println("startTime:"+h1.getStartTime());
			System.out.println("endTime:"+h1.getEndTime());
			System.out.println("duration:"+h1.getDurationInMillis());
			System.out.println("********************************************************");
		}
	}
}
