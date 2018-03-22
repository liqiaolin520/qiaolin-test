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
 * 分配组任务方式一(直接指定办理人)
 * @author qiaolin
 *
 */
public class TaskAllocation {
	// 获得Activiti核心对象。
	ProcessEngine processEngine;
	
	/**
	 * 初始化核心对象。
	 */
	@Before
	public void init(){
		processEngine = ProcessEngines.getDefaultProcessEngine();
	}
	
	/**
	 *  部署并启动流程。
	 */
	@Test
	public void deployProcess(){
		RepositoryService rs = processEngine.getRepositoryService();
		Deployment deployment = rs.createDeployment().name("分配组任务测试。")
			.addClasspathResource("diagrams/TaskAllocation.bpmn")
			.addClasspathResource("diagrams/TaskAllocation.png")
			.deploy();
		System.out.println("获得流程部署Id:"+deployment.getId());
		System.out.println("获得流程部署Name:"+deployment.getName());
	}
	
	/**
	 *  根据部署的key来启动流程
	 */
	@Test
	public void runProcess(){
		ProcessInstance pi = processEngine.getRuntimeService()
			.startProcessInstanceByKey("TaskAllocation");
		System.out.println("流程启动成功。"+pi.getId());
	}
	
	/**
	 *  查询我的个人任务列表
	 */
	@Test
	public void findMyTaskList(){
		String assignee = "admin";
		List<Task> list = processEngine.getTaskService()
			.createTaskQuery() 		// 创建任务查询对象。
			.taskAssignee(assignee) // 传入任务对应人。
			.list();			    // 获得对应的列表。
		
		System.out.println("任务ID\t任务名称\t任务人\t创建时间\t流程定义Id");
		// 遍历个人任务列表。
		for (Task task : list) {
			System.out.print(task.getId()+"\t");
			System.out.print(task.getName()+"\t");
			System.out.print(task.getAssignee()+"\t");
			System.out.print(task.getCreateTime()+"\t");
			System.out.println(task.getExecutionId()+"\t");
		}
	}
	
	/**
	 *  查询组任务列表。
	 */
	@Test
	public void findGroupList(){
		String candidateUser = "qiaolin";
		List<Task> list = processEngine.getTaskService()
			.createTaskQuery() // 创建任务查询对象
			.taskCandidateUser(candidateUser) // 任务候选人
			.list(); // 获得对应结果集
		
		System.out.println("任务ID\t任务名称\t任务人\t创建时间\t流程定义Id");
		// 遍历组任务列表。
		for (Task task : list) {
			System.out.print(task.getId()+"\t");
			System.out.print(task.getName()+"\t");
			System.out.print(task.getAssignee()+"\t");
			System.out.print(task.getCreateTime()+"\t");
			System.out.println(task.getExecutionId()+"\t");
		}
	}
	
	/**
	 *  根据任务查询成员列表
	 */
	@Test
	public void findGroupUser(){
		String taskId = "85004";
		// 方法一:根据任务Id获得任务成员
		List<IdentityLink> list = processEngine.getTaskService()
			.getIdentityLinksForTask(taskId); 
		
		/* 	方法二:根据流程定义Id来获得任务成员
		List<IdentityLink> list = processEngine.getRuntimeService()
			.getIdentityLinksForProcessInstance(instanceId);
		*/

		System.out.println("userId\taskId\tpiId\t");
		// 遍历出任务组成员
		for (IdentityLink il : list) {
			System.out.print(il.getUserId()+"\t");
			System.out.print(il.getTaskId()+"\t");
			System.out.println(il.getProcessInstanceId());
		}
	}
	
	/**
	 *  查询组任务成员历史列表
	 */
	@Test
	public void findGroupTaskHistoricUser(){
	/*	String taskId = "87508";
		List<HistoricIdentityLink> list = processEngine.getHistoryService()
			.getHistoricIdentityLinksForTask(taskId);*/		
		List<HistoricIdentityLink> list = processEngine.getHistoryService()
			.getHistoricIdentityLinksForProcessInstance("87505");
		
		// 遍历历史组任务成员
		System.out.println("userId\taskId\tpiId\t");
		for (HistoricIdentityLink il : list) {
			System.out.print(il.getUserId()+"\t");
			System.out.print(il.getTaskId()+"\t");
			System.out.println(il.getProcessInstanceId());
		}
	}
	
	/**
	 * 完成任务
	 */
	@Test
	public void completeTask(){
		// 任务Id
		String taskId = "85004";
		processEngine.getTaskService().complete(taskId);
		System.out.println("任务已完成。");
	}
	
	/**
	 *  将组任务分配给个人任务,拾取任务。
	 */
	@Test
	public void claimTask(){
		// 指定任务Id
		String taskId = "87508";
		// 指定分配的办理人
		String userId = "qiaolin";
		processEngine.getTaskService().claim(taskId, userId);
	}
	
	
}
