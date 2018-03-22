package activiti.process;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

/**
 * 	启动流程实例
 * @author qiaolin
 *
 */
public class FlowStartTest{
	/**
	 * 启动流程
	 */
	@Test
	public void flowStart(){
		String url = "myDay";
	    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		/**
		 * 使用流程定义Key启动流程实例,key对象helloword。bpmn文件中的id属性,
		 * 		对应的act_re_procdef表中的key_
		 * 用key启动时按照最新的流程版本定义启动。
		 */
	    Map<String,Object> variables = new HashMap<String,Object>();
	    variables.put("applyUser", "dd");
	    ProcessInstance processInstance= processEngine.getRuntimeService()
	    		.startProcessInstanceByKey(url, variables);
		// 流程实例Id										   
		System.out.println(processInstance.getId());
		// 流程定义Id
		System.out.println(processInstance.getProcessInstanceId());
/*		RepositoryService repositoryService = processEngine.getRepositoryService();
		ProcessDefinition processDefinition = repositoryService.getProcessDefinition(processInstance.getDeploymentId());
		
		System.out.println(processDefinition.getId());
		System.out.println(processDefinition.getKey());*/
	
	}
}
