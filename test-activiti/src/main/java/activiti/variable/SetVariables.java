package activiti.variable;

import java.util.Date;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.junit.Test;

import activiti.po.Person;

/**
 * 设置流程变量
 * @author qiaolin
 *
 */
public class SetVariables {

	@Test
	public void setVariables(){
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		// 获取任务的Service
		TaskService taskService = processEngine.getTaskService();
		// 指定办理人
		String assigneeUser = "admin";
		// 流程实例Id
		String processInstanceId = "39284";
		Task task = taskService.createTaskQuery()  // 创建任务查询
			.taskAssignee(assigneeUser)  // 指定办理人
			.processInstanceId(processInstanceId)  // 指定流程实例ID
			.singleResult();
		/**变量中存放基本数据类型*/
		
		taskService.setVariable(task.getId(), "请假人", "王二2狗"); // 使用流程变量
		taskService.setVariableLocal(task.getId(), "请假天数",6);
		taskService.setVariable(task.getId(), "请假日期", new Date());
		
		/**变量中存放javabean对象,前提:让javabean对象实现Serializeble接口s*/
		Person p = new Person();
		p.setId(1);
		p.setName("少年郎");
		taskService.setVariable(task.getId(), "人员信息", p);
		
		
	}
}
