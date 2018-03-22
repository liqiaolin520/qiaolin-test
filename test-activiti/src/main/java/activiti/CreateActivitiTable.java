package activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;

/**
 *  创建Activiti所需要的数据库表。
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
