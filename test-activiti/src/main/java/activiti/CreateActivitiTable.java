package activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;

/**
 *  ����Activiti����Ҫ�����ݿ��
 * @author qiaolin
 *
 */
public class CreateActivitiTable {
	
	
	@Test
	public void createTable(){
		ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml").buildProcessEngine();
		System.out.println("processEngine"+processEngine);
	}
}
