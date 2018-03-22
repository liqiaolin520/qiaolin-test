package activiti.task;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.task.Task;
import org.junit.Test;

/**
 * �鿴�ҵĸ�������
 * 
 * @author qiaolin
 *
 */
public class QueryMyTask {

	@Test
	public void queryMyTaskTest() {
		// ָ�����������
		String assignee = "��������";
		// ��������������
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		// ��ѯ������б�
		List<Task> tasks = processEngine.getTaskService().createTaskQuery() // ���������ѯ����
				.taskAssignee(assignee) // ָ���������������
				.orderByTaskCreateTime().desc() // ���մ���ʱ�併������
				.list();  // ��ý���б�

		// ������ϲ鿴����
		for (Task task : tasks) {
			System.out.println("taskId" + task.getId() + ",taskName:" + task.getName());
			System.out.println("----------------------------------");
		}

	}
}
