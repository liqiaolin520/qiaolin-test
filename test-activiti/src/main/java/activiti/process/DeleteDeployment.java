package activiti.process;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.junit.Test;

/**
 * ɾ������
 * @author qiaolin
 *
 */
public class DeleteDeployment {
	
	@Test
	public void deleteDeployment(){
		// ��ú��Ķ���
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		// ɾ��������Ϣ
		String deploymentId = "75001";
		// ��ȡ�ֿ�������
		RepositoryService repositoryService = processEngine.getRepositoryService();
		// ��ͨɾ��,�����ǰ����������ִ�е�����,����׳��쳣
		//repositoryService.deleteDeployment(deploymentId);
		// ����ɾ��,��ɾ���͵�ǰ������ص�������Ϣ������ִ�е���Ϣ��Ҳ������ʷ��Ϣ
		// �����: repositoryService.deleteDeploymentCaseade(deploymentId)
		repositoryService.deleteDeployment(deploymentId,true);
	}
}
