package activiti.history;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricVariableInstance;
import org.junit.Test;

/**
 *  ��ѯ��ʷ�����̱���(ʹ�����̱�������������ѯ)
 * @author qiaolin
 *
 */
public class QueryHisVariables {
	
	@Test
	public void getHisVariables(){
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		String variableName = "�������";
		List<HistoricVariableInstance> hvi = processEngine.getHistoryService()
			.createHistoricVariableInstanceQuery() // ������ʷ����ʵ����ѯ
			.variableName(variableName) // ָ�����̱���Name��
			.list();  // ��ò�ѯ�б�
		
		if(hvi!=null && hvi.size()>0){
			for (HistoricVariableInstance h1 : hvi) {
				System.out.println(h1.getVariableName()+" "+h1.getValue());
			}
		}
		
	}
}
