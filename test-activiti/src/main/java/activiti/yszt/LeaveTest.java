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
 *  ����������̡�
 * @author qiaolin
 *
 */
public class LeaveTest {
	// ���Ĳ�������
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	// ���в�������
	private RuntimeService runService = processEngine.getRuntimeService();
	// ��ʷ��������
	private HistoryService hiService = processEngine.getHistoryService();
	// �����������
	private TaskService taskService = processEngine.getTaskService();
	// �ֿ��������
	private RepositoryService rs = processEngine.getRepositoryService();
	
	
	
	
	/**
	 * ��������
	 */
	@Test 
	public void deployFlow(){
		processEngine.getRepositoryService()
			.createDeployment()
			.name("�����������11")
			.addClasspathResource("diagrams/Leave.bpmn")
			.addClasspathResource("diagrams/Leave.png")
			.deploy();
	}	
	
	/**
	 * �������̲������̱���
	 */
	@Test
	public void startProcess(){
		Map<String,Object> variables = new HashMap<String,Object>();
		variables.put("user", "qiaolin");
		variables.put("deptZhuGuans", "hehe,admin,haha");
		variables.put("deptManagers", "liuzhiqin,liuzhidan,liujiba");
		processEngine.getRuntimeService().startProcessInstanceByKey("Leave","Ц��", variables);
	}
	
	/**
	 * ��ȡ����
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
		List<Task> task = ts.createTaskQuery().taskAssignee("��ʦʦ").list();
		Execution ex = rs.createExecutionQuery().executionId(task.get(0).getExecutionId()).singleResult();
		ExecutionEntity  ee = (ExecutionEntity)ex;
		
		
		System.out.println(23);
		
		
	}
	
	/**
	 * �������
	 */
	@Test
	public void commit(){
		Map<String,Object> variables = new HashMap<String,Object>();
		variables.put("result1", "ͬ��");
		processEngine.getTaskService().complete("182504",variables);
	}
	
	/**
	 * ��������
	 */
	@Test
	public void adjustApply(){
		Map<String,Object> variables = new HashMap<String,Object>();
		variables.put("cmd", "��������");
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
	 * �������������߲�ѯ��ʷ��١�
	 * 
	 * @param ename
	 *            ����������
	 * @return ���������������������������Ѿ��رգ���᷵�����йرյ���ʷ������̣���֮����null��
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
	 * �������������߲�ѯ��ʷ��١�
	 * 
	 * @param ename
	 *            ����������
	 * @return ���������������������������Ѿ��رգ���᷵�����йرյ���ʷ������̣���֮����null��
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
		List<Task> task = taskService.createTaskQuery().taskCandidateGroup("������").list();
		List<Task> task2 =  taskService.createTaskQuery().taskCandidateUser("������").list();
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
		List<HistoricTaskInstance> hti = hiService.createHistoricTaskInstanceQuery().taskAssignee("������").taskDeleteReason("completed").list();
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
	 * ��ѯָ�����̱���
	 */
	@Test
	public void showLingDao(){
		Map<String,Object> map = runService.getVariables("82551");
		Map<String, Object> param = new HashMap<String,Object>();
		Set<Entry<String, Object>> key = map.entrySet();
		for (Entry<String, Object> entry : key) {
			if(entry.getKey().indexOf("���")!=-1){
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
		List<HistoricProcessInstance> hpi = hpiQ.variableValueEquals("processStarter", "������").finished().list();
		
		for (HistoricProcessInstance h : hpi) {
			List<HistoricVariableInstance> hvi = hiService.createHistoricVariableInstanceQuery().processInstanceId(h.getId()).list();
			Map<String,Object> params = new HashMap<String,Object>();
			for (HistoricVariableInstance hvi2 : hvi) {
				if(hvi2.getVariableName().indexOf("���")>-1){
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
