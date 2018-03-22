package activiti.process;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

/**
 * 	��������ʵ��
 * @author qiaolin
 *
 */
public class FlowStartTest{
	/**
	 * ��������
	 */
	@Test
	public void flowStart(){
		String url = "myDay";
	    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		/**
		 * ʹ�����̶���Key��������ʵ��,key����helloword��bpmn�ļ��е�id����,
		 * 		��Ӧ��act_re_procdef���е�key_
		 * ��key����ʱ�������µ����̰汾����������
		 */
	    Map<String,Object> variables = new HashMap<String,Object>();
	    variables.put("applyUser", "dd");
	    ProcessInstance processInstance= processEngine.getRuntimeService()
	    		.startProcessInstanceByKey(url, variables);
		// ����ʵ��Id										   
		System.out.println(processInstance.getId());
		// ���̶���Id
		System.out.println(processInstance.getProcessInstanceId());
/*		RepositoryService repositoryService = processEngine.getRepositoryService();
		ProcessDefinition processDefinition = repositoryService.getProcessDefinition(processInstance.getDeploymentId());
		
		System.out.println(processDefinition.getId());
		System.out.println(processDefinition.getKey());*/
	
	}
}
