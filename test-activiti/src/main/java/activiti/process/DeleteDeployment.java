package activiti.process;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.junit.Test;

/**
 * 删除流程
 * @author qiaolin
 *
 */
public class DeleteDeployment {
	
	@Test
	public void deleteDeployment(){
		// 获得核心对象
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		// 删除发布信息
		String deploymentId = "75001";
		// 获取仓库服务对象
		RepositoryService repositoryService = processEngine.getRepositoryService();
		// 普通删除,如果当前规则下正在执行的流程,则会抛出异常
		//repositoryService.deleteDeployment(deploymentId);
		// 级联删除,会删除和当前规则相关的所有信息，正在执行的信息，也包括历史信息
		// 相对于: repositoryService.deleteDeploymentCaseade(deploymentId)
		repositoryService.deleteDeployment(deploymentId,true);
	}
}
