package com.ascendant.activiti.form.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.form.StartFormData;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.ascendant.activiti.test.base.AbstractTest;

/**
* @author qiaolin
* @version 2017年3月9日
* 测试Leave请假流程 动态表单方式。
*/

public class LeaveTest extends AbstractTest {
	
	/**
	 * 初始化7大服务接口。
	 */
	@Before
	@Ignore
	public void init(){
		setUp();
	}
	
	/**
	 * 创建测试需要用的用户和组。
	 */ 
	@Test
	public void createUserAndGroup(){
		// 创建测试组
		Group group = identityService.newGroup("deptLeader");
		group.setName("部门领导");
		group.setType("assignment");
		identityService.saveGroup(group);
		Group group1 = identityService.newGroup("hr");
		group1.setName("人事");
		group1.setType("assignment");
		identityService.saveGroup(group1);
		// 创建测试用户
		User user = identityService.newUser("misszhang");
		user.setEmail("222@qq.com");
		user.setLastName("撒");
		user.setFirstName("做");
		user.setPassword("1234563");
		identityService.saveUser(user);
		User user1 = identityService.newUser("qiaolin");
		user1.setEmail("992004863@qq.com");
		user1.setLastName("巧林");
		user1.setFirstName("李");
		user1.setPassword("123456");
		identityService.saveUser(user1);
		User user2 = identityService.newUser("huangergou");
		user2.setEmail("huangergou@qq.com");
		user2.setLastName("二狗");
		user2.setFirstName("黄");
		user2.setPassword("123456");
		identityService.saveUser(user2);
		// 将用户授权到测试组中
		identityService.createMembership("qiaolin", "deptLeader");
		identityService.createMembership("misszhang", "hr");
	}
	
	/**
	 * 测试动态表单请假流程正常执行。
	 */
	@Test
	@Ignore
	@Deployment(resources = "processResource/Leave.bpmn")
	public void testLeave(){
		// 设置当前用户
		String currentUserId = "qiaoqiaolin";
		identityService.setAuthenticatedUserId(currentUserId);
		//启动流程
		ProcessDefinition instance = repositoryService.createProcessDefinitionQuery().processDefinitionKey("leave").singleResult();
		// 时间格式化
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar date = Calendar.getInstance();
		// 创建Map,用于传入流程需要的参数
		Map<String,String> variable = new HashMap<>();
		variable.put("startDate", sdf.format(date.getTime()));
		date.add(Calendar.DAY_OF_MONTH, 2);
		variable.put("endDate", sdf.format(date.getTime()));
		variable.put("reason", "感冒发烧流鼻涕!");
		// 使用FormService对象来启动
		ProcessInstance pInstance = formService.submitStartFormData(instance.getId(), variable);
		// 验证流程实例是否启动成功
		assertNotNull(pInstance);
		// 部门领导审批
		Task task = taskService.createTaskQuery().taskCandidateGroup("deptLeader").singleResult();
		variable.clear();
		variable.put("deptLeaderApprove", "true");
		formService.submitTaskFormData(task.getId(), variable);
		// 人事审批
		task = taskService.createTaskQuery().taskCandidateGroup("hr").singleResult();
		variable.clear();
		variable.put("hrApprove", "true");
		formService.submitTaskFormData(task.getId(), variable);
		// 销假
		task = taskService.createTaskQuery().taskAssignee(currentUserId).singleResult();
		variable.clear();
		variable.put("reportBackDate", sdf.format(date.getTime()));
		formService.submitTaskFormData(task.getId(), variable);
		// 验证流程是否已结束
		HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().finished().singleResult();
		assertNotNull(historicProcessInstance);
		// 读取历史流程变量
		Map<String, Object> params = getVariable(pInstance);
		// 验证执行结果
		assertEquals("ok", params.get("result"));
	}
	
	/**
	 * 测试动态表单请假流程批准被拒绝。
	 */
	@Test
	@Ignore
	@Deployment(resources = "processResource/Leave.bpmn")
	public void testRefuseLeave(){
		String user = "qiaoqiaolin";
		// 设置当前启动用户,通过这个方式设置后会对应流程中的inititor属性
		identityService.setAuthenticatedUserId(user);
		// 查询部署的流程定义
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().processDefinitionKey("leave").singleResult();
		// 准备流程中动态表单需要的参数
		Map<String,String> params = new HashMap<>();
		// 准备时间对象与时间格式化对象
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar date = Calendar.getInstance();
		params.put("startDate", sdf.format(date.getTime()));
		// 使日期增加两天
		date.add(Calendar.DAY_OF_MONTH, 2);
		params.put("endDate", sdf.format(date.getTime()));
		params.put("reason", "今天天气很好，不想上班。");
		//启动流程
		ProcessInstance pInstance = formService.submitStartFormData(pd.getId(), params);
		assertNotNull(pInstance);
		// 领导拒绝。
		Task task = taskService.createTaskQuery().taskCandidateGroup("deptLeader").singleResult();
		params.clear();
		params.put("deptLeaderApprove", "false");
		formService.submitTaskFormData(task.getId(), params);
		// 结束请假.
		task = taskService.createTaskQuery().taskAssignee(user).singleResult();
		params.clear();
		params.put("reApply", "false");
		formService.submitTaskFormData(task.getId(), params);
		// 验证流程是否已经结束
		HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().finished().singleResult();
		assertNotNull(historicProcessInstance);
		// 查询历史流程变量
		Map<String,Object> vars = getVariable(pInstance);
		// 根据流程变量判断流程是否按照我们的意愿线路来执行。
		assertEquals("canceled", vars.get("result"));
	}
	
	/**
	 * 部署流程定义
	 */
	@Test
	@Ignore
	public void deployProcessDefinition(){
		InputStream in = null;
		try {
			in = new FileInputStream("D:/workspase1/ActivitiTest_WEB/src/main/resources/processResource/Leave.zip");
			ZipInputStream zipInputStream = new ZipInputStream(in);
			repositoryService.createDeployment().addZipInputStream(zipInputStream)
				.name("测试").deploy();
			;
			
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	@Test
	@Ignore
	public void getFormData(){
		StartFormData data = formService.getStartFormData("leave:6:382508");
		System.out.println();
		System.out.println();
		System.out.println();
	}
	
	
	
	
	/**
	 *  通过流程实例获得历史流程变量。
	 * @param instance 流程实例,用于获得流程的Id
	 * @return 历史流程变量键值对Map集合。
	 */
	private Map<String,Object> getVariable(ProcessInstance instance){
		Map<String, Object> params = new HashMap<>();		 
		List<HistoricDetail> varInstances =  historyService.createHistoricDetailQuery().processInstanceId(instance.getId()).list();
		List<HistoricVariableInstance> vars = historyService.createHistoricVariableInstanceQuery().processInstanceId(instance.getId()).list();
		/*for (HistoricDetail historicDetail : varInstances) {
			if(historicDetail instanceof HistoricFormProperty){
				// 表单中的字段
				HistoricFormProperty var = (HistoricFormProperty)historicDetail;
				params.put(var.getPropertyId(), var.getPropertyValue());
				System.out.println("task Id: "+var.getTaskId()+" - "+var.getPropertyId()+" - "+var.getPropertyValue());
			}else if(historicDetail instanceof HistoricVariableUpdate){
				HistoricVariableUpdate var = (HistoricVariableUpdate)historicDetail;
				params.put(var.getVariableName(), var.getValue());
				System.out.println("task Id: "+var.getTaskId()+" - "+var.getVariableName()+" - "+var.getValue());
			}else{
				System.out.println(11);
			}
		}*/
		for (HistoricVariableInstance hvi : vars) {
			params.put(hvi.getVariableName(), hvi.getValue());
		}
		return params;
	}
	
	
	
	
	
	
	
	
}
