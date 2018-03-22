package activiti.process;

import java.io.InputStream;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;

/**
 * Zipѹ����ģʽ����
 * @author qiaolin
 *
 */
public class DeployFlowZip {

	@Test
	public void deployZip(){
		// ����ϴ��ļ�����������
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("xxx.zip");
		ZipInputStream zipIn = new ZipInputStream(in);
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		// ��ȡ�ֿ����,����·������ɲ���
		Deployment deployment = processEngine.getRepositoryService()
		.createDeployment().name("�������") // ����������ʾ����
		.addZipInputStream(zipIn)  // ʹ��zip����������ɲ���
		.deploy();  // ��ɷ���
		
		System.out.println(deployment.getId()+"   "+deployment.getName());
		
		
	}
}
