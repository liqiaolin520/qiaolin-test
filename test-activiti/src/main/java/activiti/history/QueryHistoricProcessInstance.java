package activiti.history;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricProcessInstance;
import org.junit.Test;

/**
 *  �������̶����Key��������ʷ����ʵ��
 * @author qiaolin
 *
 */
public class QueryHistoricProcessInstance {
	
	@Test
	public void queryHistoricProcessInstance(){
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		List<HistoricProcessInstance> hpi = processEngine.getHistoryService()
			.createHistoricProcessInstanceQuery() // ������ʷ����ʵ����ѯ����
			.processDefinitionKey("myDay") // �������̶����Key��Ϊ��ѯ����
			.orderByProcessInstanceStartTime().desc() // �������̿�ʼ��ʱ�併���ѯ
			.list(); // ���ؽ����
	
		// �����鿴���
		for (HistoricProcessInstance hPInstance : hpi) {
			System.out.println("id"+hPInstance.getId());
			System.out.println("pid"+hPInstance.getProcessDefinitionId());
			System.out.println("��ʼʱ��"+hPInstance.getStartTime().toLocaleString());
			System.out.println("����ʱ��"+(hPInstance.getEndTime()==null?null:hPInstance.getEndTime().toLocaleString()));
			System.out.println("duration"+hPInstance.getDurationInMillis());
			System.out.println("-------------------------");
		}
	
	
	}
}
