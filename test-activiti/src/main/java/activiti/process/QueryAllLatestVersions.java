package activiti.process;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Test;

/**
 *  ��ѯ���°�����̶���
 * @author qiaolin
 *
 */
public class QueryAllLatestVersions {

	@Test
	public void queryAllLatestVersions(){
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		List<ProcessDefinition> processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery()
			.orderByDeploymentId().asc().list();
		Map<String,ProcessDefinition> hashMap = new HashMap<String,ProcessDefinition>();
		
		for (ProcessDefinition p2 : processDefinition) {
			hashMap.put(p2.getKey(), p2);
		}
		
		for (ProcessDefinition pr22 : hashMap.values()) {
			System.out.println(pr22.getId());
			System.out.println(pr22.getKey());
			System.out.println(pr22.getResourceName());
			System.out.println(pr22.getDeploymentId());
			System.out.println(pr22.getDescription());
		}
	}
}
