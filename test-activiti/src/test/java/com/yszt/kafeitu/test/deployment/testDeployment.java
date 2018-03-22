package com.yszt.kafeitu.test.deployment;

import static org.junit.Assert.assertNotNull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Before;
import org.junit.Test;

import com.yszt.kafeitu.test1.AbstractTest;

/**
* @author qiaolin
* @version 2017年3月7日
* 测试几种部署流程定义的方式。
*/

public class testDeployment extends AbstractTest{
	
	@Before
	public void init(){
		setUp();
	}
	
	/**
	 * 通过classpath的方式来部署流程定义。。
	 */
	@Test
	public void testClasspathDeployment(){
		// 准备要部署的bpmn流程定义文件和png流程图片文件
		String bpmn = "TestUsers.bpmn";
		String png = "TestUsers.png";

		// 创建部署构建器
		DeploymentBuilder deployment = repositoryService.createDeployment();
		// 添加资源
		deployment.addClasspathResource(bpmn);
		deployment.addClasspathResource(png);
		// 部署
		deployment.deploy();
		// 查询是否部署成功
		ProcessDefinition instance = repositoryService.createProcessDefinitionQuery().processDefinitionKey("test001").singleResult();
		assertNotNull(instance);
		// 获得流程图片
		List<ProcessDefinition> instances = repositoryService.createProcessDefinitionQuery().list();
		for (ProcessDefinition processDefinition : instances) {
			System.out.println(processDefinition.getDiagramResourceName());
		}
	}
	
	/**
	 * 通过输入流的方式来部署流程定义。
	 *   用的比较的广泛。例如通过web来部署
	 *   通过字符串的形式来部署，这个实际意义不是太大。
	 */
	@Test
	public void testInputStreamDeployment(){
		String filepath = "D:/workspase1/ActivitiTest/src/main/resources/Test2.bpmn";
		DeploymentBuilder deployment = repositoryService.createDeployment();
		try {
			deployment.addInputStream("Test2.png", new FileInputStream(filepath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		deployment.deploy();
		
		// 验证流程定义部署成功否
		List<ProcessDefinition> instances = repositoryService.createProcessDefinitionQuery().list();
		for (ProcessDefinition processDefinition : instances) {
			System.out.println(processDefinition.getDiagramResourceName());
		}
	}
	
	/**
	 * 测试Zip打包格式部署流程定义。
	 *  可以部署多个流程定义。
	 * @throws FileNotFoundException 
	 */
	@Test
	public void testZipInputStreamDeployment() throws FileNotFoundException{
		String zipPath = "D:/workspase1/ActivitiTest/src/main/resources/resources.zip";
		DeploymentBuilder deployment = repositoryService.createDeployment();
		InputStream in = new FileInputStream(zipPath);
		deployment.addZipInputStream(new ZipInputStream(in)).deploy();
		// 查询部署的流程定义
		Long count = repositoryService.createProcessDefinitionQuery().count();
		System.out.println(count);
		
		// 查询部署记录
		List<Deployment> deployments = repositoryService.createDeploymentQuery().list();
		System.out.println("-----------------------");
		for (Deployment deployment2 : deployments) {
			System.out.println(deployment2.getId());
			// 验证是否部署成功
			List<ProcessDefinition> dd = repositoryService.createProcessDefinitionQuery().deploymentId(deployment2.getId()).list();
			for (ProcessDefinition pp : dd) {
				System.out.println(pp.getDiagramResourceName());
				System.out.println(pp.getResourceName());
			}
		}
	}
	
	









}
