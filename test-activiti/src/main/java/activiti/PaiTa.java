package activiti;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 *  ������ϵ��
 * @author qiaolin
 *
 */
public class PaiTa {
	// ����ProcessEngine����
		ProcessEngine processEngine;
		
		@Before
		public void init(){
			processEngine = ProcessEngines.getDefaultProcessEngine();
		}
		
		/**
		 *  �������������̡�
		 */
		@Test
		public void deployAndStart(){
			// ʹ��Activiti���Ķ���ProcessEngine������Դ����
			Deployment deployment = processEngine.getRepositoryService()
					.createDeployment().name("����")
					.addClasspathResource("diagrams/paita.bpmn")
					.addClasspathResource("diagrams/paita.png").deploy();
			System.out.println("��ò���Id:" + deployment.getId());
			System.out.println("��ò���Name:" + deployment.getName());
			
			//����������
			ProcessInstance pi = processEngine.getRuntimeService().startProcessInstanceByKey("paita");
			System.out.println(pi.getId());
			System.out.println(pi.getActivityId());
		}
		
		/**
		 *  ��ѯ�ҵĸ�������
		 */
		@Test 
		public void queryMyTask(){
			// ���������������ˡ�
			String assignee = "fozzie";
			List<Task> ti = processEngine.getTaskService().createTaskQuery() // ��������ѯ����
				.taskAssignee(assignee)  // ���������Ӧ���˲�ѯ
				.orderByTaskCreateTime().asc().list(); // ��������Ĵ���ʱ���������򲢷��ؽ������
			
			/**ѭ����������Ӧ��������Ϣ*/
			for (Task task : ti) {
				System.out.println(task.getId());
				System.out.println(task.getName());
				System.out.println(task.getAssignee());
				System.out.println(task.getExecutionId());
				System.out.println(task.getCreateTime());
			}
		}
		
		/**
		 * ��������Id�������
		 */
		@Test 
		public void completeTask(){
			String taskId = "77508";
			// ���Դ�ҳ�����ϻ�ȡ���������д��ֵ���������̱����������̱����������������ߡ�
			Map<String,Object> variables = new HashMap<String,Object>(); 
			variables.put("money", 200);
			processEngine.getTaskService()
				.complete(taskId,variables);
			System.out.println("������ɡ�");
		}
		
}
