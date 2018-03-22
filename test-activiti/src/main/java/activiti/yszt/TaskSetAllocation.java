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
 *  组任务分配到个人任务方式二:使用流程变量。
 * @author qiaolin
 *
 */
public class TaskSetAllocation {
	ProcessEngine processEngine;
	
	/**
	 * 初始化核心操作对象。
	 */
	@Before
	public void init(){
		processEngine = ProcessEngines.getDefaultProcessEngine();
	}
	
	/**
	 * 流程部署并启动传入流程变量(组任务所属人)
	 */
	@Test@Ignore
	public void DeployFlowOrFlowStart(){
		/*processEngine.getRepositoryService()
			.createDeployment()
			.addClasspathResource("diagrams/Love.bpmn")
			.addClasspathResource("diagrams/Love.png")
			.name("测试流程变量设置组任务")
			.deploy();*/
		// 启动流程
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userIds", "qiaolin,admin,kermit");
		processEngine.getRuntimeService().startProcessInstanceByKey("mylove",map);
		System.out.println("启动流程完成");
	}
	
	/**
	 * 查询我的个人任务
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
	 * 查询组任务列表
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
	 * 查询组任务成员
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
			System.out.println("－－－－－－－－－－－－－－－－－－－－－－－－－－－");
		}
	}
	
	/**
	 * 查询组任务历史成员。
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
			System.out.println("－－－－－－－－－－－－－－－－－－－－－－－");
		}
	}
	
	/**
	 * 完成任务。
	 */
	@Test
	public void compleTask(){
		String taskId = "97505";
		processEngine.getTaskService().complete(taskId);
		System.out.println("任务完成！");
	}
	
	/**
	 * 拾取任务。将组任务分配给个人任务。
	 */
	@Test@Ignore
	public void claim(){
		String taskId = "102505";
		String userId = "qiaolin";
		processEngine.getTaskService()
			.claim(taskId, userId);
		System.out.println("拾取任务完成。");
	}
	
	/**
	 * 将分配的个人任务重新返回组任务之中
	 */
	@Test@Ignore
	public void setAssigneeTask(){
		String taskId = "102505";
		processEngine.getTaskService().setAssignee(taskId, null);
	}
	
	/**
	 *  像组任务中继续添加成员(候选者与参与者)
	 */
	@Test@Ignore
	public void addUser(){
		String taskId = "102505";
		String userId = "fozzie";
		processEngine.getTaskService().addCandidateUser(taskId, userId);
		System.out.println("添加OK");
	}

	/**
	 * 	删除组任务中的成员。
	 */
	@Test@Ignore
	public void deleteUser(){
		String taskId = "102505";
		String userId = "qiaolin";
		processEngine.getTaskService().deleteCandidateUser(taskId, userId);
	}

}
