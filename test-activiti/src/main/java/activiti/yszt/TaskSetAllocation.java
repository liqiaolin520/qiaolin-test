package activiti.yszt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricIdentityLink;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 *  ��������䵽��������ʽ��:ʹ�����̱�����
 * @author qiaolin
 *
 */
public class TaskSetAllocation {
	ProcessEngine processEngine;
	
	/**
	 * ��ʼ�����Ĳ�������
	 */
	@Before
	public void init(){
		processEngine = ProcessEngines.getDefaultProcessEngine();
	}
	
	/**
	 * ���̲��������������̱���(������������)
	 */
	@Test@Ignore
	public void DeployFlowOrFlowStart(){
		/*processEngine.getRepositoryService()
			.createDeployment()
			.addClasspathResource("diagrams/Love.bpmn")
			.addClasspathResource("diagrams/Love.png")
			.name("�������̱�������������")
			.deploy();*/
		// ��������
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userIds", "qiaolin,admin,kermit");
		processEngine.getRuntimeService().startProcessInstanceByKey("mylove",map);
		System.out.println("�����������");
	}
	
	/**
	 * ��ѯ�ҵĸ�������
	 */
	@Test@Ignore
	public void findMyTask(){
		String assigneeId = "qiaolin";
		List<Task> task = processEngine.getTaskService()
			.createTaskQuery()
			.taskAssignee(assigneeId)
			.list();
		
		for (Task task2 : task) {
			System.out.println(task2.getId());
			System.out.println(task2.getAssignee());
			System.out.println(task2.getName());
			System.out.println(task2.getExecutionId());
			System.out.println(task2.getProcessInstanceId());
		}
	}
	
	/**
	 * ��ѯ�������б�
	 */
	@Test@Ignore
	public void findGroupTask(){
		String candidateUser = "qiaolin";
		List<Task> task = processEngine.getTaskService()
			.createTaskQuery()
			.taskCandidateUser(candidateUser)
			.list();
		
		for (Task task2 : task) {
			System.out.println(task2.getId());
			System.out.println(task2.getName());
			System.out.println(task2.getAssignee());
			System.out.println(task2.getExecutionId());
			System.out.println(task2.getTaskDefinitionKey());
			System.out.println("------------------");
		}
	}
	
	/**
	 * ��ѯ�������Ա
	 */
	@Test 
	public void findGroupUser(){
		String taskId = "80052";
		List<IdentityLink> il = processEngine.getTaskService()
			.getIdentityLinksForTask(taskId);
		
		for (IdentityLink i : il) {
			System.out.println(i.getTaskId());
			System.out.println(i.getUserId());
			System.out.println(i.getType());
			System.out.println("������������������������������������������������������");
		}
	}
	
	/**
	 * ��ѯ��������ʷ��Ա��
	 */
	@Test@Ignore
	public void findTaskHistoricUser(){
		List<HistoricIdentityLink> hil = processEngine.getHistoryService()
			.getHistoricIdentityLinksForTask("97505");
		
		for (HistoricIdentityLink h1 : hil) {
			System.out.println(h1.getGroupId());
			System.out.println(h1.getTaskId());
			System.out.println(h1.getType());
			System.out.println(h1.getUserId());
			System.out.println("����������������������������������������������");
		}
	}
	
	/**
	 * �������
	 */
	@Test
	public void compleTask(){
		String taskId = "97505";
		processEngine.getTaskService().complete(taskId);
		System.out.println("������ɣ�");
	}
	
	/**
	 * ʰȡ���񡣽�������������������
	 */
	@Test@Ignore
	public void claim(){
		String taskId = "102505";
		String userId = "qiaolin";
		processEngine.getTaskService()
			.claim(taskId, userId);
		System.out.println("ʰȡ������ɡ�");
	}
	
	/**
	 * ������ĸ����������·���������֮��
	 */
	@Test@Ignore
	public void setAssigneeTask(){
		String taskId = "102505";
		processEngine.getTaskService().setAssignee(taskId, null);
	}
	
	/**
	 *  ���������м�����ӳ�Ա(��ѡ���������)
	 */
	@Test@Ignore
	public void addUser(){
		String taskId = "102505";
		String userId = "fozzie";
		processEngine.getTaskService().addCandidateUser(taskId, userId);
		System.out.println("���OK");
	}

	/**
	 * 	ɾ���������еĳ�Ա��
	 */
	@Test@Ignore
	public void deleteUser(){
		String taskId = "102505";
		String userId = "qiaolin";
		processEngine.getTaskService().deleteCandidateUser(taskId, userId);
	}

}
