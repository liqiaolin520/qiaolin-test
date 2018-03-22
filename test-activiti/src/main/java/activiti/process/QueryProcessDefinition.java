package activiti.process;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Test;

/**
 * �鿴���̶��� ��Ӧ���ݿ�� ������ acr_re_procdef
 * 	id:{key}:{version}:{���ֵ}
 * 	name:��Ӧ�����ļ�peocess�ڵ��name����
 * 	key:��Ӧ�����ļ�process�ڵ��id����
 *  version:����ʱ�Զ����ɵġ�����ǵ�һ������������,versionĬ�ϴ�һ��ʼ,������淢����ͬkey������,��+1
 * @author qiaolin
 *
 */
public class QueryProcessDefinition {
	
	@Test
	public void queryProcess(){
		// ��ú��Ĳ�������
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		// ��ȡ�ֿ�������ʹ�ð汾����������
		List<ProcessDefinition> prList = processEngine.getRepositoryService()
			.createProcessDefinitionQuery()
		    // ��Ӳ�ѯ����
			// .processDefinitionName(processDefinitionName)
			// .processDefinitionId(processDefinitionId)
			// .processDefinitionKey(processDefinitionKey)
			//  ����
			.orderByProcessDefinitionVersion().asc()
			// ��ѯ�Ľ����
			// .count() // ���ؽ����������
			// .listPage(firstResult,maxResults) // ��ҳ��ѯ
			// .singleResult() // Ψһ�Ľ����
			.list();  // �ܵĽ��������
			
		// �������ϣ��鿴����
		for (ProcessDefinition pd : prList) {
			System.out.println("id:"+pd.getId());
			System.out.println("name:"+pd.getName());
			System.out.println("key:"+pd.getKey());
			System.out.println("version:"+pd.getVersion());
			System.out.println("resourceName:"+pd.getResourceName());
			System.out.println("--------------");
		}
			
	}	
}
