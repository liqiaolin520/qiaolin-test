package activiti.process;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

/**
 * ��ѯ����״̬(�ж���������ִ��,�����Ѿ�������)
 * @author qiaolin
 *
 */
public class QueryProcessState {

	@Test
	public void queryProcessState(){
		// ��ú��Ķ���ProcessEngine
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		// ����ʵ����ʵ��Id
		String processInstanceId = "3997";
		// ͨ������ʵ����Id��ѯ������ʵ��
		ProcessInstance pi = processEngine.getRuntimeService().createProcessInstanceQuery()
			.processInstanceId(processInstanceId)
			.singleResult();
		// �ж����̵�״̬
		if(pi!=null){
			System.out.println("����ʵ����״̬:"+pi.getActivityId());
			System.out.println("��ǰ������������");
		}else{
			System.out.println("�����ѽ���");
		}
		
	}
}
