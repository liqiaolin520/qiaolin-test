package activiti.variable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.task.Task;
import org.junit.Test;

import activiti.po.Person;

/**
 * ��ȡ���̱���
 * 
 * @author qiaolin
 *
 */
public class GetVariables {
		// Activiti���Ĳ�������
		private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		// ����ʱ��������
		private RuntimeService runService = processEngine.getRuntimeService();
		// ��ʷ��������
		private HistoryService hiService = processEngine.getHistoryService();
		// �����������
		private TaskService taskService = processEngine.getTaskService();
		// 
		private RepositoryService rs = processEngine.getRepositoryService();
		
	
	
	
	@Test
	public void getVariables() {
		// ��ȡִ�е�Service
		TaskService taskService = processEngine.getTaskService();
		// ָ��������
		String assigneeUser = "admin";
		// ����ʵ��ID
		String processInstanceId = "39255";
		Task task = taskService.createTaskQuery() // ���������ѯ
				.taskAssignee(assigneeUser) // ָ��������
				.processInstanceId(processInstanceId) // ָ������ʵ��IdS
				.singleResult(); // ��õ�������

		/** 1.��ȡ�����д�Ż����������� */
		String qingJiaRen = (String) taskService.getVariable(task.getId(), "�����");
		Integer qingJiaSum = (Integer) taskService.getVariableLocal(task.getId(), "�������");
		Date date = (Date) taskService.getVariable(task.getId(), "�������");
		System.out.println(qingJiaRen);
		System.out.println(qingJiaSum);
		System.out.println(date.toLocaleString());

		/** 2.��ȡ�����д�ŵ�javaBean���� */
		Person p = (Person) taskService.getVariable(task.getId(), "��Ա��Ϣ");
		System.out.println(p);
	}

	/**
	 * ������������ߡ�
	 */
	@Test
	public void findMyTask() {
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		List<Execution> ex = processEngine.getRuntimeService().createExecutionQuery().list();
		RuntimeService rs = processEngine.getRuntimeService();
		List<String> starts = new ArrayList<String>();
		for (Execution e : ex) {
			starts.add((String) rs.getVariable(e.getId(), "processStarter"));
		}
		for (String string : starts) {
			System.out.println(string);
		}
		/*
		 * List<Task> task =
		 * processEngine.getTaskService().createTaskQuery().list(); for (Task
		 * task2 : task) {
		 * System.out.println(task2.getId()+" "+task2.getAssignee()+" "+task2.
		 * getFormKey()+" "+task2.getName());
		 */
	}

	/**
	 * ��ѯ�ҵ�������
	 */
	@Test
	public void findMyGroupTask() {
		List<Task> task = processEngine.getTaskService().createTaskQuery().taskCandidateUser("haha").list();
		for (Task task2 : task) {
			System.out.println(task2.getAssignee() + " " + task2.getId() + " " + task2.getName() + " "
					+ task2.getProcessInstanceId());
		}
	}

	/**
	 * ��ѯ�Ѿ����������
	 */
	@Test
	public void findMyHictoryTask() {
		List<HistoricVariableInstance> hvi = processEngine.getHistoryService().createHistoricVariableInstanceQuery()
				.variableName("processStarter").list();
		List<String> str = new ArrayList<String>();
		for (HistoricVariableInstance h : hvi) {
			System.out.println(h.getValue() + " " + h.getId() + " " + h.getTaskId() + " " + h.getVariableName() + " "
					+ h.getVariableTypeName() + " " + h.getProcessInstanceId());
			if ("С��".equals(h.getValue())) {
				str.add((String) h.getProcessInstanceId());
			}
		}
		HistoricProcessInstanceQuery hpq = processEngine.getHistoryService().createHistoricProcessInstanceQuery();
		List<HistoricProcessInstance> hpi = new ArrayList<HistoricProcessInstance>();
		for (int i = 0; i < str.size(); i++) {
			System.out.println(hpq.processInstanceId(str.get(i)).singleResult());
			hpi.add(hpq.processInstanceId(str.get(i)).singleResult());
		}
		System.out.println("���*****************************88");
		for (HistoricProcessInstance hp : hpi) {
			System.out.println(hp.getId() + " " + hp.getStartActivityId() + " " + hp.getName() + " "
					+ hp.getBusinessKey() + " " + hp.getDeploymentId());
		}

	}

	@Test
	public void test1() {
		int i = 88;
		System.out.println((char) i);
	}

	/**
	 * ���ȫ���ڵ����Ϣ
	 */
	@Test
	public void gettiaojian() {
		BpmnModel bm = processEngine.getRepositoryService().getBpmnModel("Leave:3:5004");
		if (bm != null) {
			// ��ȡ���̶�������Ԫ��
			Collection<FlowElement> flowElements = bm.getMainProcess().getFlowElements();
			List<SequenceFlow> se = new ArrayList<SequenceFlow>();
			// �ҳ���ʼ�ڵ�������
			for (FlowElement f : flowElements) {
				System.out.println(f.getClass().toString()+" "+f.getName()+" "+f.getName());
				if(f.getClass().toString().equals("class org.activiti.bpmn.model.SequenceFlow")){
					se.add((SequenceFlow)f);
				}
			}
			//bm.getMainProcess().getFlowElement("SequenceFlow");
			for (SequenceFlow s : se) {
				System.out.println(s.getConditionExpression());
			}
		}

	}
	
	
	/** 
     *  
     * @author: Longjun 
     * @Description: ����ʵ����Ż�ȡ��һ������ڵ�ʵ������ 
     * @date:2016��3��18�� ����4:33:36 
     * @param sessionId���̵߳�sessionId��isNeedTransaction��ʾ�Ƿ���Ҫ����procInstId����ʵ��ID 
     */  
	@Test
    public void getTaskDefinitionList(){  
        HistoryService historyService = processEngine.getHistoryService();  
        RepositoryService repositoryService = processEngine.getRepositoryService();  
        RuntimeService runtimeService = processEngine.getRuntimeService();  
        //List<TaskDefinition> taskDefinitionList = new ArrayList<TaskDefinition>();  
        //���̱�ʾ  
        String processDefinitionId = historyService.createHistoricProcessInstanceQuery().processInstanceId("10001").singleResult().getProcessDefinitionId();  
        ProcessDefinitionEntity pde = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);  
        //ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl)repositoryService).getDeployedProcessDefinition(processDefinitionId);  
        //ִ��ʵ��  
        ExecutionEntity execution = (ExecutionEntity) runtimeService.createProcessInstanceQuery().processInstanceId("10001").singleResult();  
        
        //��ǰʵ����ִ�е��ĸ��ڵ�  
        String activitiId = execution.getActivityId();  
        //��õ�ǰ��������нڵ�  
        List<ActivityImpl> activitiList = pde.getActivities();  
        String id = null;  
        for(ActivityImpl activityImpl:activitiList){    
        	
        	id = activityImpl.getId(); 
            if(activitiId.equals(id)){ 
                System.out.println("��ǰ����"+activityImpl.getProperty("name"));  
                //taskDefinitionList = nextTaskDefinition(activityImpl, activityImpl.getId());  
            }   
        }  
    }  



	
	/** 
     * ��ȡ��һ���û������û�����Ϣ  
     * @param String taskId     ����Id��Ϣ  
     * @return  ��һ���û������û�����Ϣ  
     * @throws Exception 
     *//*  
    public Set<Expression> getNextTaskGroup(String taskId) throws Exception {  
          
        ProcessDefinitionEntity processDefinitionEntity = null;  
          
        String id = null;  
          
        TaskDefinition task = null;  
          
        //��ȡ����ʵ��Id��Ϣ   
        String processInstanceId = taskService.createTaskQuery().taskId(taskId).singleResult().getProcessInstanceId();  
          
        //��ȡ���̷���Id��Ϣ   
        String definitionId = runService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult().getProcessDefinitionId();  
          
        processDefinitionEntity = (ProcessDefinitionEntity) ((RepositoryServiceImpl) rs)  
                .getDeployedProcessDefinition(definitionId);  
          
        ExecutionEntity execution = (ExecutionEntity) runService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();  
          
        //��ǰ���̽ڵ�Id��Ϣ   
        String activitiId = execution.getActivityId();    
          
        //��ȡ�������нڵ���Ϣ   
        List<ActivityImpl> activitiList = processDefinitionEntity.getActivities();   
          
        //�������нڵ���Ϣ   
        for(ActivityImpl activityImpl : activitiList){      
            id = activityImpl.getId();     
              
            // �ҵ���ǰ�ڵ���Ϣ  
            if (activitiId.equals(id)) {  
                  
                //��ȡ��һ���ڵ���Ϣ   
                task = nextTaskDefinition(activityImpl, activityImpl.getId(), null, processInstanceId);  
  
                break;  
            }  
        }  
          
        return task.getCandidateGroupIdExpressions();  
    }  
      
    *//**  
     * ��һ������ڵ���Ϣ,  
     *  
     * �����һ���ڵ�Ϊ�û�������ֱ�ӷ���,  
     *  
     * �����һ���ڵ�Ϊ��������, ��ȡ��������Id��Ϣ, ������������Id��Ϣ��execution��ȡ����ʵ����������IdΪkey�ı���ֵ,  
     * ���ݱ���ֵ�ֱ�ִ���������غ���·�е�el���ʽ, ���ҵ�el���ʽͨ������·����û�������Ϣ  
     * @param ActivityImpl activityImpl     ���̽ڵ���Ϣ  
     * @param String activityId             ��ǰ���̽ڵ�Id��Ϣ  
     * @param String elString               ��������˳�����߶��ж�����, ������������˳�����߶��ж�����Ϊ${money>1000}, ��������������ʱ����variables�е�money>1000, �����������˳������Ϣ  
     * @param String processInstanceId      ����ʵ��Id��Ϣ  
     * @return  
     *//*    
    private TaskDefinition nextTaskDefinition(ActivityImpl activityImpl, String activityId, String elString, String processInstanceId){   
              
        PvmActivity ac = null;     
        Object s = null;  
          
        //��������ڵ�Ϊ�û������ҽڵ㲻�ǵ�ǰ�ڵ���Ϣ   
            if("userTask".equals(activityImpl.getProperty("type")) && !activityId.equals(activityImpl.getId())){    
                //��ȡ�ýڵ���һ���ڵ���Ϣ   
                TaskDefinition taskDefinition = ((UserTaskActivityBehavior)activityImpl.getActivityBehavior()).getTaskDefinition();    
                return taskDefinition;    
            }else{    
                //��ȡ�ڵ�����������·��Ϣ   
                List<PvmTransition> outTransitions = activityImpl.getOutgoingTransitions();    
                List<PvmTransition> outTransitionsTemp = null;    
                for(PvmTransition tr : outTransitions){      
                    ac = tr.getDestination(); //��ȡ��·���յ�ڵ�      
                    //���������·Ϊ��������   
                    if("exclusiveGateway".equals(ac.getProperty("type"))){    
                        outTransitionsTemp = ac.getOutgoingTransitions();      
                        //�������·���ж�����Ϊ����Ϣ   
                        if(isEmpty(elString)) {  
                            //��ȡ��������ʱ���õ������ж�������Ϣ   
                            elString = getGatewayCondition(ac.getId(), processInstanceId);  
                        }  
                          
                        //�����������ֻ��һ����·��Ϣ   
                        if(outTransitionsTemp.size() == 1){    
                            return nextTaskDefinition((ActivityImpl)outTransitionsTemp.get(0).getDestination(), activityId, elString, processInstanceId);    
                        }else if(outTransitionsTemp.size() > 1){  //������������ж�����·��Ϣ   
                            for(PvmTransition tr1 : outTransitionsTemp){    
                                s = tr1.getProperty("conditionText");  //��ȡ����������·�ж�������Ϣ   
                                //�ж�el���ʽ�Ƿ����   
                                if(isCondition(ac.getId(), StrUtils.trim(s.toString()), elString)){    
                                    return nextTaskDefinition((ActivityImpl)tr1.getDestination(), activityId, elString, processInstanceId);    
                                }    
                            }    
                        }    
                    }else if("userTask".equals(ac.getProperty("type"))){    
                        return ((UserTaskActivityBehavior)((ActivityImpl)ac).getActivityBehavior()).getTaskDefinition();    
                    }else{    
                    }    
                }     
            return null;    
        }    
    }  
      
    *//** 
     * ��ѯ��������ʱ�������������ж�������Ϣ  
     * @param String gatewayId          ��������Id��Ϣ, ��������ʱ��������·���ж�����keyΪ����Id��Ϣ  
     * @param String processInstanceId  ����ʵ��Id��Ϣ  
     * @return 
     *//*  
    public String getGatewayCondition(String gatewayId, String processInstanceId) {  
        Execution execution = runService.createExecutionQuery().processInstanceId(processInstanceId).singleResult();  
        return runService.getVariable(execution.getId(), gatewayId).toString();  
    }  
      
    *//** 
     * ����key��value�ж�el���ʽ�Ƿ�ͨ����Ϣ  
     * @param String key    el���ʽkey��Ϣ  
     * @param String el     el���ʽ��Ϣ  
     * @param String value  el���ʽ����ֵ��Ϣ  
     * @return 
     *//*  
    public boolean isCondition(String key, String el, String value) {  
        ExpressionFactory factory = new ExpressionFactoryImpl();    
        SimpleContext context = new SimpleContext();    
        context.setVariable(key, factory.createValueExpression(value, String.class));    
        ValueExpression e = factory.createValueExpression(context, el, boolean.class);    
        return (Boolean) e.getValue(context);  
    }  
	
	
	private boolean isEmpty(String str) {
		if(str==null){
			return true;
		}
		return false;
	}*/
	

}
