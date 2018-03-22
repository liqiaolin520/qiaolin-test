package activiti.task;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricTaskInstance;
import org.junit.Test;

/**
 *  ��ѯĳһ����ִ�о�����������ڵ�
 * @author qiaolin
 *
 */
public class QueryHistoricTask {

	@Test
	public void queryHistoricTask(){
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		// ��������ʵ��Id
		String processInstanceId = "39255";
		List<HistoricTaskInstance> hti = processEngine.getHistoryService()
			.createHistoricTaskInstanceQuery()    // ������ʷ����ʵ����ѯ
			.processInstanceId(processInstanceId)  // ʹ������ʵ��Id��ѯ
			.orderByHistoricTaskInstanceStartTime().asc()  // ����ִ������ڵ����ʼʱ�������ѯ��
			.list();  // ���ؽ����
		
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
