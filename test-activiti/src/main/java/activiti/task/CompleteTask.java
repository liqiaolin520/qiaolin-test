package activiti.task;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.junit.Test;

/**
 * 完成我的个人任务。
 * @author qiaolin
 *
 */
public class CompleteTask {

	@Test
	public void completeTask(){
		// 任务Id
		String taskId = "12505";
		// 获取流程引擎对象
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		// 完成任务
		processEngine.getTaskService().complete(taskId);
		System.out.println("Id为"+taskId+"的任务已完成！");
	}
}
