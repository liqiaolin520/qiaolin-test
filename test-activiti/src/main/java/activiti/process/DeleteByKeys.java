package activiti.process;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Test;

/**
 * 删除所有key相同的流程定义
 * @author qiaolin
 *
 */
public class DeleteByKeys {

	@Test
	public void deleteByKeys(){
		String key = "Leave";
		// 查询指定Key的所有流程定义.
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		List<ProcessDefinition> processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery()
			.processDefinitionKey(key)
			.list();
		
		// 通过循环来删一一删除
		for (ProcessDefinition p2 : processDefinition) {
			processEngine.getRepositoryService().deleteDeployment(p2.getDeploymentId(), true);
		}
		
		System.out.println("批量删除完毕！");
	}
}
