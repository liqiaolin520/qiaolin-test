package activiti.variable;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricVariableInstance;
import org.junit.Test;

/**
 *  查询某一流程执行时设置所有的流程变量。
 * @author qiaolin
 *
 */
public class QueryHistoricVariables {

	@Test
	public void queryHistoricVariables(){
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		// 指定流程实例Id
		String processInstanceId = "39284";
		List<HistoricVariableInstance> phv = processEngine.getHistoryService()
			.createHistoricVariableInstanceQuery() // 获得历史变量实例查询对象
			.processInstanceId(processInstanceId) // 按照流程实例Id
			.orderByVariableName().asc()  // 按照变量的名称升序排序
			.list();  // 获得结果集。
		
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
