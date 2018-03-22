package activiti.process;

import java.io.InputStream;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;

/**
 * Zip压缩包模式部署
 * @author qiaolin
 *
 */
public class DeployFlowZip {

	@Test
	public void deployZip(){
		// 获得上传文件的数日流程
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("xxx.zip");
		ZipInputStream zipIn = new ZipInputStream(in);
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		// 获取仓库服务,从类路径下完成部署
		Deployment deployment = processEngine.getRepositoryService()
		.createDeployment().name("请假流程") // 部署规则的显示别名
		.addZipInputStream(zipIn)  // 使用zip的输入流完成部署
		.deploy();  // 完成发布
		
		System.out.println(deployment.getId()+"   "+deployment.getName());
		
		
	}
}
