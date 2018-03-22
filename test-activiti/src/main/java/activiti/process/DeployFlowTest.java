package activiti.process;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

/**
 * Activiti流程图部署
 *  输入流加载资源文件的3中方式
 *  this.getClass().getClassLoader().getResourceAsstream("demo.bpmn");// 从classpath根目录下在家指定名称文件
 *  this.getClass().getResourceAsStream("demo.bpmn"); //从当前包下加载在指定名称的文件
 * 	this.getClass().getResourceAsStream("/demo.bpmn"); // 从classPath根目录下加载指定名称的文件
 * @author qiaolin
 *
 */
public class DeployFlowTest {

	@Test
	public void deployFlow() {

		// 产生ProcessEngine对象
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		// 使用Activiti核心对象ProcessEngine产生资源服务。
		Deployment deployment = processEngine.getRepositoryService()
				.createDeployment().name("湖南优势智通有限公司")
				.addClasspathResource("diagrams/YouShiZhiTong.bpmn")
				.addClasspathResource("diagrams/YouShiZhiTong.png").deploy();
		System.out.println("获得部署Id:" + deployment.getId());
		System.out.println("获得部署Name:" + deployment.getName());
		
		//启动该流程
		ProcessInstance pi = processEngine.getRuntimeService().startProcessInstanceByKey("YouShiZhiTong");
		System.out.println(pi.getId());
		System.out.println(pi.getName());
		System.out.println(pi.getParentId());
		System.out.println(pi.getActivityId());
	}
}
