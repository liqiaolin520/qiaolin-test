package activiti.task;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricTaskInstance;
import org.junit.Test;

/**
 *  根据办理人查询历史任务
 * @author qiaolin
 *
 */
public class QueryHistoryTask {
	
	@Test
	public void queryHistoryTask(){
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		// 历史任务办理人
		String taskAssignee = "部门经理";
		// 通过流程实例ID查询流程实例
		List<HistoricTaskInstance> instance = processEngine.getHistoryService()
			.createHistoricTaskInstanceQuery()  // 创建历史任务查询
			.taskAssignee(taskAssignee)  // 指定办理人查询历史任务
			.list();  // 获得结果列表。
		System.out.println("任务Id\t流程实例Id\t任务的办理人\t执行对象Id\t开始时间\t结束时间");
		if(instance!=null && instance.size()>0){
			for (HistoricTaskInstance hi : instance) {
				System.out.print(hi.getId()+"\t");
				System.out.print(hi.getProcessInstanceId()+"\t");
				System.out.print(hi.getAssignee()+"\t");
				System.out.print(hi.getExecutionId()+"\t");
				System.out.print(hi.getStartTime().toLocaleString()+"\t"+(hi.getEndTime()==null?"null":hi.getEndTime().toLocaleString()));
				System.out.println();
			}
		
		
		}
	
	}
}
