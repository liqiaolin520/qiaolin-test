package activiti.process;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

/**
 * 查询流程状态(判断流程正在执行,还是已经结束。)
 * @author qiaolin
 *
 */
public class QueryProcessState {

	@Test
	public void queryProcessState(){
		// 获得核心对象ProcessEngine
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		// 定义实流程实例Id
		String processInstanceId = "3997";
		// 通过流程实例的Id查询到流程实例
		ProcessInstance pi = processEngine.getRuntimeService().createProcessInstanceQuery()
			.processInstanceId(processInstanceId)
			.singleResult();
		// 判断流程的状态
		if(pi!=null){
			System.out.println("流程实例的状态:"+pi.getActivityId());
			System.out.println("当前流程正在运行");
		}else{
			System.out.println("流程已结束");
		}
		
	}
}
