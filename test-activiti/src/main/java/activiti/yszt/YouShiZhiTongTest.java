package activiti.yszt;

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
 *  优势智通请假审批流程练习。
 * @author qiaolin
 *
 */
public class YouShiZhiTongTest {
	// 产生ProcessEngine对象
	ProcessEngine processEngine;
	
	@Before
	public void init(){
		processEngine = ProcessEngines.getDefaultProcessEngine();
	}
	
	/**
	 *  部署并启动该流程。
	 */
	@Test@Ignore
	public void deployAndStart(){
		// 使用Activiti核心对象ProcessEngine产生资源服务。
		Deployment deployment = processEngine.getRepositoryService()
				.createDeployment().name("湖南优势智通有限公司")
				.addClasspathResource("diagrams/YouShiZhiTong.bpmn")
				.addClasspathResource("diagrams/YouShiZhiTong.png").deploy();
		System.out.println("获得部署Id:" + deployment.getId());
		System.out.println("获得部署Name:" + deployment.getName());
		
		//启动该流程
		ProcessInstance pi = processEngine.getRuntimeService().startProcessInstanceByKey("YouShiZhiTong");
		System.out.println(pi.getId());
		System.out.println(pi.getActivityId());
	}
	
	/**
	 *  查询我的个人任务。
	 */
	@Test@Ignore
	public void queryMyTask(){
		// 定义任务所属的人。
		String assignee = "admin";
		List<Task> ti = processEngine.getTaskService().createTaskQuery() // 获得任务查询对象
			.taskAssignee(assignee)  // 按照任务对应的人查询
			.orderByTaskCreateTime().asc().list(); // 按照任务的创建时间升序排序并返回结果集。
		
		/**循环遍历出对应的任务信息*/
		for (Task task : ti) {
			System.out.println(task.getId());
			System.out.println(task.getName());
			System.out.println(task.getAssignee());
			System.out.println(task.getExecutionId());
			System.out.println(task.getCreateTime());
		}
	}
	
	/**
	 * 根据任务Id完成任务
	 */
	@Test
	public void completeTask(){
		String taskId = "60008";
		// 可以从页面是上获取重要/不重要的选项，设置流程变量
		Map<String,Object> variables = new HashMap<String,Object>(); 
		variables.put("message", "很重要");
		processEngine.getTaskService()
			.complete(taskId,variables);
		System.out.println("任务完成。");
	}
	
}
