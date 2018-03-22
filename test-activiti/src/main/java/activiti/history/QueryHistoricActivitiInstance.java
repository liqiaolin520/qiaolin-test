package activiti.history;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricActivityInstance;
import org.junit.Test;

/**
 *  查询流程历史活动(某一流程执行的步骤)
 * @author qiaolin
 *
 */
public class QueryHistoricActivitiInstance {

	@Test
	public void queryHistoricActivitiInstance(){
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		String processInstanceId = "39255";  //  流程id
		List<HistoricActivityInstance> hai = processEngine.getHistoryService()
			.createHistoricActivityInstanceQuery() // 创建历史活动实例查询
			.processInstanceId(processInstanceId)  // 使用流程实例Id查询
			.orderByHistoricActivityInstanceEndTime().asc() // 根据实例的结束时间升序排序
			.list(); // 返回结果集。
		
		for (HistoricActivityInstance h1 : hai) {
			System.out.println("activitiId:"+h1.getActivityId());
			System.out.println("name:"+h1.getActivityName());
			System.out.println("type:"+h1.getActivityType());
			System.out.println("pid:"+h1.getProcessInstanceId());
			System.out.println("assignee:"+h1.getAssignee());
			System.out.println("startTime:"+h1.getStartTime());
			System.out.println("endTime:"+h1.getEndTime());
			System.out.println("duration:"+h1.getDurationInMillis());
			System.out.println("********************************************************");
		}
	}
}
