package activiti.yszt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.junit.Test;

/**
 *  测试请假流程。
 * @author qiaolin
 *
 */
public class LeaveTest {
	// 核心操作对象
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	// 运行操作对象
	private RuntimeService runService = processEngine.getRuntimeService();
	// 历史操作对象
	private HistoryService hiService = processEngine.getHistoryService();
	// 任务操作对象
	private TaskService taskService = processEngine.getTaskService();
	// 仓库操作对象
	private RepositoryService rs = processEngine.getRepositoryService();
	
	
	
	
	/**
	 * 部署流程
	 */
	@Test 
	public void deployFlow(){
		processEngine.getRepositoryService()
			.createDeployment()
			.name("测试请假流程11")
			.addClasspathResource("diagrams/Leave.bpmn")
			.addClasspathResource("diagrams/Leave.png")
			.deploy();
	}	
	
	/**
	 * 启动流程并给流程变量
	 */
	@Test
	public void startProcess(){
		Map<String,Object> variables = new HashMap<String,Object>();
		variables.put("user", "qiaolin");
		variables.put("deptZhuGuans", "hehe,admin,haha");
		variables.put("deptManagers", "liuzhiqin,liuzhidan,liujiba");
		processEngine.getRuntimeService().startProcessInstanceByKey("Leave","笑话", variables);
	}
	
	/**
	 * 获取任务
	 */
	@Test
	public void huoqu(){
		RuntimeService rs = processEngine.getRuntimeService();
		TaskService ts = processEngine.getTaskService();
		//List<IdentityLink> ik = ts.getIdentityLinksForTask("182504");
		/*for (IdentityLink i : ik) {
			System.out.println(i.getGroupId()+" "+i.getTaskId()+" "+i.getType()+" "+i.getUserId()+" "+i.getProcessInstanceId());
		}
		ts.claim("182504", "hehe");*/
		List<Task> task = ts.createTaskQuery().taskAssignee("李师师").list();
		Execution ex = rs.createExecutionQuery().executionId(task.get(0).getExecutionId()).singleResult();
		ExecutionEntity  ee = (ExecutionEntity)ex;
		
		
		System.out.println(23);
		
		
	}
	
	/**
	 * 完成任务
	 */
	@Test
	public void commit(){
		Map<String,Object> variables = new HashMap<String,Object>();
		variables.put("result1", "同意");
		processEngine.getTaskService().complete("182504",variables);
	}
	
	/**
	 * 调整申请
	 */
	@Test
	public void adjustApply(){
		Map<String,Object> variables = new HashMap<String,Object>();
		variables.put("cmd", "重新申请");
		processEngine.getTaskService().complete("180004",variables);
	}
	
	
	@Test
	public void findMyFlow() {
		String processStarter = "6";
		RuntimeService runService = processEngine.getRuntimeService();
		List<Execution> ex = runService.createExecutionQuery().variableValueEquals("processStarter","6 ").list();
		List<ExecutionEntity> processInstance = new ArrayList<ExecutionEntity>();
		for (int i = 0; i < ex.size(); i++) {
			Execution e = ex.get(i);
			System.out.println(e.getId());
			if (processStarter.equals((String) runService.getVariable(e.getId(), "processStarter"))) {
				processInstance
						.add((ExecutionEntity)runService.createProcessInstanceQuery().processInstanceId(e.getId()).singleResult());
			}
		}
		for (ExecutionEntity e : processInstance) {
			System.out.println(e.getActivityId()+" "+e.getBusinessKey()+" "+e.getId()+" "+e.getProcessDefinitionId() );
		}
	}

	
	
	
	
	/**
	 * 根据流程启动者查询历史请假。
	 * 
	 * @param ename
	 *            流程启动者
	 * @return 如果流程启动者启动的请假流程已经关闭，则会返回所有关闭的历史请假流程，反之返回null。
	 */
	@Test
	public void findMyHictoryTask() {
		String ename = "6";
		HistoryService hiService = processEngine.getHistoryService();
		List<HistoricVariableInstance> hvi = hiService.createHistoricVariableInstanceQuery()
				.variableName("processStarter").list();
		List<String> str = new ArrayList<String>();
		for (HistoricVariableInstance h : hvi) {
			if (ename.equals(h.getValue())) {
				str.add((String) h.getProcessInstanceId());
			}
		}
		HistoricProcessInstanceQuery hpq = hiService.createHistoricProcessInstanceQuery();
		List<HistoricProcessInstance> hpi = new ArrayList<HistoricProcessInstance>();
		for (int i = 0; i < str.size(); i++) {
			if (hpq.processInstanceId(str.get(i)).singleResult().getEndTime() != null) {
				hpi.add(hpq.processInstanceId(str.get(i)).singleResult());
			}
		}
		
		for (HistoricProcessInstance h : hpi) {
			System.out.println(h);
		}
		
		
	}
	

	/**
	 * 根据流程启动者查询历史请假。
	 * 
	 * @param ename
	 *            流程启动者
	 * @return 如果流程启动者启动的请假流程已经关闭，则会返回所有关闭的历史请假流程，反之返回null。
	 */
	@Test
	public void findMyHictoryTask1() {
		String ename = "6";
		HistoryService hiService = processEngine.getHistoryService();
		List<HistoricProcessInstance> hi = hiService.createHistoricProcessInstanceQuery().variableValueEquals("processStarter", "6").list();
		
		
		List<HistoricVariableInstance> hvi = hiService.createHistoricVariableInstanceQuery()
				.variableName("processStarter").list();
		List<String> str = new ArrayList<String>();
		for (HistoricVariableInstance h : hvi) {
			if (ename.equals(h.getValue())) {
				str.add((String) h.getProcessInstanceId());
			}
		}
		HistoricProcessInstanceQuery hpq = hiService.createHistoricProcessInstanceQuery();
		List<HistoricProcessInstance> hpi = new ArrayList<HistoricProcessInstance>();
		for (int i = 0; i < str.size(); i++) {
			if (hpq.processInstanceId(str.get(i)).singleResult().getEndTime() != null) {
				hpi.add(hpq.processInstanceId(str.get(i)).singleResult());
			}
		}
		
		for (HistoricProcessInstance h : hpi) {
			System.out.println(h);
		}
	}
	
	@Test
	public void method() {
		// TODO Auto-generated method stub
		List<Task> task = taskService.createTaskQuery().taskCandidateGroup("李巧林").list();
		List<Task> task2 =  taskService.createTaskQuery().taskCandidateUser("李巧林").list();
		System.out.println("ssss");
		System.out.println("ssss");
		System.out.println("ssss");
		System.out.println("ssss");
	}
	
	@Test
	public void showGroupCandidate(){
		List<IdentityLink> Candidate = taskService.getIdentityLinksForTask("80052");
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
	}
	
	
	@Test
	public void showHistoryTask(){
		HistoricProcessInstanceQuery hpq = hiService.createHistoricProcessInstanceQuery();
		List<HistoricTaskInstance> hti = hiService.createHistoricTaskInstanceQuery().taskAssignee("李巧林").taskDeleteReason("completed").list();
		HistoricProcessInstance hpi =  hpq.processInstanceId("80001").singleResult();
		HistoricProcessInstance hpi1 =  hpq.processInstanceId("80001").singleResult();
		HistoricProcessInstance hpi2 =  hpq.processInstanceId("80001").singleResult();
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
	}
	
	/**
	 * 查询指定流程变量
	 */
	@Test
	public void showLingDao(){
		Map<String,Object> map = runService.getVariables("82551");
		Map<String, Object> param = new HashMap<String,Object>();
		Set<Entry<String, Object>> key = map.entrySet();
		for (Entry<String, Object> entry : key) {
			if(entry.getKey().indexOf("意见")!=-1){
				param.put(entry.getKey(), entry.getValue());
			}
		}
		map.clear();
		Set<String> key1 = param.keySet();
		for (String string : key1) {
			System.out.println(param.get(string));
		}
	}
	
	
	@Test
	public void showHistoryLeave(){
		HistoricProcessInstanceQuery hpiQ = hiService.createHistoricProcessInstanceQuery();
		List<HistoricProcessInstance> hpi = hpiQ.variableValueEquals("processStarter", "李巧林").finished().list();
		
		for (HistoricProcessInstance h : hpi) {
			List<HistoricVariableInstance> hvi = hiService.createHistoricVariableInstanceQuery().processInstanceId(h.getId()).list();
			Map<String,Object> params = new HashMap<String,Object>();
			for (HistoricVariableInstance hvi2 : hvi) {
				if(hvi2.getVariableName().indexOf("意见")>-1){
					params.put(hvi2.getVariableName(), hvi2.getValue());
				}
			}
			System.out.println();
		}
		System.out.println();
		System.out.println();
		System.out.println();
		
		
	}
	
	
}
