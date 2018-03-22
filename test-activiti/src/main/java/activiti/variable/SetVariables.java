package activiti.variable;

import java.util.Date;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.junit.Test;

import activiti.po.Person;

/**
 * �������̱���
 * @author qiaolin
 *
 */
public class SetVariables {

	@Test
	public void setVariables(){
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		// ��ȡ�����Service
		TaskService taskService = processEngine.getTaskService();
		// ָ��������
		String assigneeUser = "admin";
		// ����ʵ��Id
		String processInstanceId = "39284";
		Task task = taskService.createTaskQuery()  // ���������ѯ
			.taskAssignee(assigneeUser)  // ָ��������
			.processInstanceId(processInstanceId)  // ָ������ʵ��ID
			.singleResult();
		/**�����д�Ż�����������*/
		
		taskService.setVariable(task.getId(), "�����", "����2��"); // ʹ�����̱���
		taskService.setVariableLocal(task.getId(), "�������",6);
		taskService.setVariable(task.getId(), "�������", new Date());
		
		/**�����д��javabean����,ǰ��:��javabean����ʵ��Serializeble�ӿ�s*/
		Person p = new Person();
		p.setId(1);
		p.setName("������");
		taskService.setVariable(task.getId(), "��Ա��Ϣ", p);
		
		
	}
}
