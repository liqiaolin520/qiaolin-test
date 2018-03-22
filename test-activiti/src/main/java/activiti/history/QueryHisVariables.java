package activiti.history;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricVariableInstance;
import org.junit.Test;

/**
 *  查询历史的流程变量(使用流程变量的名称来查询)
 * @author qiaolin
 *
 */
public class QueryHisVariables {
	
	@Test
	public void getHisVariables(){
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		String variableName = "请假天数";
		List<HistoricVariableInstance> hvi = processEngine.getHistoryService()
			.createHistoricVariableInstanceQuery() // 创建历史变量实例查询
			.variableName(variableName) // 指定流程变量Name。
			.list();  // 获得查询列表
		
		if(hvi!=null && hvi.size()>0){
			for (HistoricVariableInstance h1 : hvi) {
				System.out.println(h1.getVariableName()+" "+h1.getValue());
			}
		}
		
	}
}
