package activiti.process;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Test;

/**
 * ɾ������key��ͬ�����̶���
 * @author qiaolin
 *
 */
public class DeleteByKeys {

	@Test
	public void deleteByKeys(){
		String key = "Leave";
		// ��ѯָ��Key���������̶���.
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		List<ProcessDefinition> processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery()
			.processDefinitionKey(key)
			.list();
		
		// ͨ��ѭ����ɾһһɾ��
		for (ProcessDefinition p2 : processDefinition) {
			processEngine.getRepositoryService().deleteDeployment(p2.getDeploymentId(), true);
		}
		
		System.out.println("����ɾ����ϣ�");
	}
}
