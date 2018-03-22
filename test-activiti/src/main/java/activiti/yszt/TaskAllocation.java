package activiti.yszt;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.history.HistoricIdentityLink;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.junit.Before;
import org.junit.Test;

/**
 * ����������ʽһ(ֱ��ָ��������)
 * @author qiaolin
 *
 */
public class TaskAllocation {
	// ���Activiti���Ķ���
	ProcessEngine processEngine;
	
	/**
	 * ��ʼ�����Ķ���
	 */
	@Before
	public void init(){
		processEngine = ProcessEngines.getDefaultProcessEngine();
	}
	
	/**
	 *  �����������̡�
	 */
	@Test
	public void deployProcess(){
		RepositoryService rs = processEngine.getRepositoryService();
		Deployment deployment = rs.createDeployment().name("������������ԡ�")
			.addClasspathResource("diagrams/TaskAllocation.bpmn")
			.addClasspathResource("diagrams/TaskAllocation.png")
			.deploy();
		System.out.println("������̲���Id:"+deployment.getId());
		System.out.println("������̲���Name:"+deployment.getName());
	}
	
	/**
	 *  ���ݲ����key����������
	 */
	@Test
	public void runProcess(){
		ProcessInstance pi = processEngine.getRuntimeService()
			.startProcessInstanceByKey("TaskAllocation");
		System.out.println("���������ɹ���"+pi.getId());
	}
	
	/**
	 *  ��ѯ�ҵĸ��������б�
	 */
	@Test
	public void findMyTaskList(){
		String assignee = "admin";
		List<Task> list = processEngine.getTaskService()
			.createTaskQuery() 		// ���������ѯ����
			.taskAssignee(assignee) // ���������Ӧ�ˡ�
			.list();			    // ��ö�Ӧ���б�
		
		System.out.println("����ID\t��������\t������\t����ʱ��\t���̶���Id");
		// �������������б�
		for (Task task : list) {
			System.out.print(task.getId()+"\t");
			System.out.print(task.getName()+"\t");
			System.out.print(task.getAssignee()+"\t");
			System.out.print(task.getCreateTime()+"\t");
			System.out.println(task.getExecutionId()+"\t");
		}
	}
	
	/**
	 *  ��ѯ�������б�
	 */
	@Test
	public void findGroupList(){
		String candidateUser = "qiaolin";
		List<Task> list = processEngine.getTaskService()
			.createTaskQuery() // ���������ѯ����
			.taskCandidateUser(candidateUser) // �����ѡ��
			.list(); // ��ö�Ӧ�����
		
		System.out.println("����ID\t��������\t������\t����ʱ��\t���̶���Id");
		// �����������б�
		for (Task task : list) {
			System.out.print(task.getId()+"\t");
			System.out.print(task.getName()+"\t");
			System.out.print(task.getAssignee()+"\t");
			System.out.print(task.getCreateTime()+"\t");
			System.out.println(task.getExecutionId()+"\t");
		}
	}
	
	/**
	 *  ���������ѯ��Ա�б�
	 */
	@Test
	public void findGroupUser(){
		String taskId = "85004";
		// ����һ:��������Id��������Ա
		List<IdentityLink> list = processEngine.getTaskService()
			.getIdentityLinksForTask(taskId); 
		
		/* 	������:�������̶���Id����������Ա
		List<IdentityLink> list = processEngine.getRuntimeService()
			.getIdentityLinksForProcessInstance(instanceId);
		*/

		System.out.println("userId\taskId\tpiId\t");
		// �������������Ա
		for (IdentityLink il : list) {
			System.out.print(il.getUserId()+"\t");
			System.out.print(il.getTaskId()+"\t");
			System.out.println(il.getProcessInstanceId());
		}
	}
	
	/**
	 *  ��ѯ�������Ա��ʷ�б�
	 */
	@Test
	public void findGroupTaskHistoricUser(){
	/*	String taskId = "87508";
		List<HistoricIdentityLink> list = processEngine.getHistoryService()
			.getHistoricIdentityLinksForTask(taskId);*/		
		List<HistoricIdentityLink> list = processEngine.getHistoryService()
			.getHistoricIdentityLinksForProcessInstance("87505");
		
		// ������ʷ�������Ա
		System.out.println("userId\taskId\tpiId\t");
		for (HistoricIdentityLink il : list) {
			System.out.print(il.getUserId()+"\t");
			System.out.print(il.getTaskId()+"\t");
			System.out.println(il.getProcessInstanceId());
		}
	}
	
	/**
	 * �������
	 */
	@Test
	public void completeTask(){
		// ����Id
		String taskId = "85004";
		processEngine.getTaskService().complete(taskId);
		System.out.println("��������ɡ�");
	}
	
	/**
	 *  ��������������������,ʰȡ����
	 */
	@Test
	public void claimTask(){
		// ָ������Id
		String taskId = "87508";
		// ָ������İ�����
		String userId = "qiaolin";
		processEngine.getTaskService().claim(taskId, userId);
	}
	
	
}
