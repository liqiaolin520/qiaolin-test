package activiti.variable;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricVariableInstance;
import org.junit.Test;

/**
 *  ��ѯĳһ����ִ��ʱ�������е����̱�����
 * @author qiaolin
 *
 */
public class QueryHistoricVariables {

	@Test
	public void queryHistoricVariables(){
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		// ָ������ʵ��Id
		String processInstanceId = "39284";
		List<HistoricVariableInstance> phv = processEngine.getHistoryService()
			.createHistoricVariableInstanceQuery() // �����ʷ����ʵ����ѯ����
			.processInstanceId(processInstanceId) // ��������ʵ��Id
			.orderByVariableName().asc()  // ���ձ�����������������
			.list();  // ��ý������
		
		for (HistoricVariableInstance h1 : phv) {
			System.out.println(h1.getId());
			System.out.println(h1.getTaskId());
			System.out.println(h1.getProcessInstanceId());
			System.out.println(h1.getVariableName()+":"+h1.getValue());
			System.out.println(h1.getVariableTypeName());
			System.out.println(h1.getCreateTime());
			System.out.println(h1.getTime());
			System.out.println("*******************************");
		}
	}
}
