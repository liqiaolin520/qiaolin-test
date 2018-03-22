package activiti.task;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.task.Task;
import org.junit.Test;

/**
 * 查看我的个人任务。
 * 
 * @author qiaolin
 *
 */
public class QueryMyTask {

	@Test
	public void queryMyTaskTest() {
		// 指定任务办理者
		String assignee = "部门主管";
		// 获得流程引擎对象
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		// 查询人物的列表
		List<Task> tasks = processEngine.getTaskService().createTaskQuery() // 创建任务查询对象
				.taskAssignee(assignee) // 指定个人任务办理人
				.orderByTaskCreateTime().desc() // 按照创建时间降序排列
				.list();  // 获得结果列表

		// 遍历结合查看内容
		for (Task task : tasks) {
			System.out.println("taskId" + task.getId() + ",taskName:" + task.getName());
			System.out.println("----------------------------------");
		}

	}
}
