package activiti.history;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricActivityInstance;
import org.junit.Test;

/**
 *  ��ѯ������ʷ�(ĳһ����ִ�еĲ���)
 * @author qiaolin
 *
 */
public class QueryHistoricActivitiInstance {

	@Test
	public void queryHistoricActivitiInstance(){
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		String processInstanceId = "39255";  //  ����id
		List<HistoricActivityInstance> hai = processEngine.getHistoryService()
			.createHistoricActivityInstanceQuery() // ������ʷ�ʵ����ѯ
			.processInstanceId(processInstanceId)  // ʹ������ʵ��Id��ѯ
			.orderByHistoricActivityInstanceEndTime().asc() // ����ʵ���Ľ���ʱ����������
			.list(); // ���ؽ������
		
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
