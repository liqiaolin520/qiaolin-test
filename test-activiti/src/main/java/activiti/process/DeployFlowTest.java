package activiti.process;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

/**
 * Activiti����ͼ����
 *  ������������Դ�ļ���3�з�ʽ
 *  this.getClass().getClassLoader().getResourceAsstream("demo.bpmn");// ��classpath��Ŀ¼���ڼ�ָ�������ļ�
 *  this.getClass().getResourceAsStream("demo.bpmn"); //�ӵ�ǰ���¼�����ָ�����Ƶ��ļ�
 * 	this.getClass().getResourceAsStream("/demo.bpmn"); // ��classPath��Ŀ¼�¼���ָ�����Ƶ��ļ�
 * @author qiaolin
 *
 */
public class DeployFlowTest {

	@Test
	public void deployFlow() {

		// ����ProcessEngine����
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		// ʹ��Activiti���Ķ���ProcessEngine������Դ����
		Deployment deployment = processEngine.getRepositoryService()
				.createDeployment().name("����������ͨ���޹�˾")
				.addClasspathResource("diagrams/YouShiZhiTong.bpmn")
				.addClasspathResource("diagrams/YouShiZhiTong.png").deploy();
		System.out.println("��ò���Id:" + deployment.getId());
		System.out.println("��ò���Name:" + deployment.getName());
		
		//����������
		ProcessInstance pi = processEngine.getRuntimeService().startProcessInstanceByKey("YouShiZhiTong");
		System.out.println(pi.getId());
		System.out.println(pi.getName());
		System.out.println(pi.getParentId());
		System.out.println(pi.getActivityId());
	}
}
