package activiti.history;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricProcessInstance;
import org.junit.Test;

/**
 * ��������ʵ��Id��ѯ��ʷ����ʵ��
 * @author qiaolin
 *
 */
public class QueryHistoryProcessInstanceById {

	@Test
	public void queryHistoryProcessInstanceById(){
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		// ����ʵ��Id 
		String processInstanceId = "10001";
		HistoricProcessInstance hpi = processEngine.getHistoryService()
			.createHistoricProcessInstanceQuery() // ������ʷ����ʵ����ѯ
			.processInstanceId(processInstanceId) // ʹ������ʵ��ID��ѯ
			.singleResult();
		System.out.println("���̶���ID"+hpi.getProcessDefinitionId());
		System.out.println("����ʵ��ID"+hpi.getId());
		System.out.println(hpi.getStartTime()+" "+hpi.getEndTime()+" "+hpi.getDurationInMillis());
	
	}
}
