package activiti.task;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.junit.Test;

/**
 * ����ҵĸ�������
 * @author qiaolin
 *
 */
public class CompleteTask {

	@Test
	public void completeTask(){
		// ����Id
		String taskId = "12505";
		// ��ȡ�����������
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		// �������
		processEngine.getTaskService().complete(taskId);
		System.out.println("IdΪ"+taskId+"����������ɣ�");
	}
}
