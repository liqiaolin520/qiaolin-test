package activiti.task;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricTaskInstance;
import org.junit.Test;

/**
 *  ���ݰ����˲�ѯ��ʷ����
 * @author qiaolin
 *
 */
public class QueryHistoryTask {
	
	@Test
	public void queryHistoryTask(){
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		// ��ʷ���������
		String taskAssignee = "���ž���";
		// ͨ������ʵ��ID��ѯ����ʵ��
		List<HistoricTaskInstance> instance = processEngine.getHistoryService()
			.createHistoricTaskInstanceQuery()  // ������ʷ�����ѯ
			.taskAssignee(taskAssignee)  // ָ�������˲�ѯ��ʷ����
			.list();  // ��ý���б�
		System.out.println("����Id\t����ʵ��Id\t����İ�����\tִ�ж���Id\t��ʼʱ��\t����ʱ��");
		if(instance!=null && instance.size()>0){
			for (HistoricTaskInstance hi : instance) {
				System.out.print(hi.getId()+"\t");
				System.out.print(hi.getProcessInstanceId()+"\t");
				System.out.print(hi.getAssignee()+"\t");
				System.out.print(hi.getExecutionId()+"\t");
				System.out.print(hi.getStartTime().toLocaleString()+"\t"+(hi.getEndTime()==null?"null":hi.getEndTime().toLocaleString()));
				System.out.println();
			}
		
		
		}
	
	}
}
