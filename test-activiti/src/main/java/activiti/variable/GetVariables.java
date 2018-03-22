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
 * 获取流程变量
 * 
 * @author qiaolin
 *
 */
public class GetVariables {
		// Activiti核心操作对象
		private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		// 运行时操作对象
		private RuntimeService runService = processEngine.getRuntimeService();
		// 历史操作对象
		private HistoryService hiService = processEngine.getHistoryService();
		// 任务操作对象
		private TaskService taskService = processEngine.getTaskService();
		// 
		private RepositoryService rs = processEngine.getRepositoryService();
		
	
	
	
	@Test
	public void getVariables() {
		// 获取执行的Service
		TaskService taskService = processEngine.getTaskService();
		// 指定办理人
		String assigneeUser = "admin";
		// 流程实例ID
		String processInstanceId = "39255";
		Task task = taskService.createTaskQuery() // 创建任务查询
				.taskAssignee(assigneeUser) // 指定办理人
				.processInstanceId(processInstanceId) // 指定流程实例IdS
				.singleResult(); // 获得单个数据

		/** 1.获取变量中存放基本数据类型 */
		String qingJiaRen = (String) taskService.getVariable(task.getId(), "请假人");
		Integer qingJiaSum = (Integer) taskService.getVariableLocal(task.getId(), "请假天数");
		Date date = (Date) taskService.getVariable(task.getId(), "请假日期");
		System.out.println(qingJiaRen);
		System.out.println(qingJiaSum);
		System.out.println(date.toLocaleString());

		/** 2.获取变量中存放的javaBean对象 */
		Person p = (Person) taskService.getVariable(task.getId(), "人员信息");
		System.out.println(p);
	}

	/**
	 * 获得流程启动者。
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
	 * 查询我的组任务。
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
	 * 查询已经结束的请假
	 */
	@Test
	public void findMyHictoryTask() {
		List<HistoricVariableInstance> hvi = processEngine.getHistoryService().createHistoricVariableInstanceQuery()
				.variableName("processStarter").list();
		List<String> str = new ArrayList<String>();
		for (HistoricVariableInstance h : hvi) {
			System.out.println(h.getValue() + " " + h.getId() + " " + h.getTaskId() + " " + h.getVariableName() + " "
					+ h.getVariableTypeName() + " " + h.getProcessInstanceId());
			if ("小明".equals(h.getValue())) {
				str.add((String) h.getProcessInstanceId());
			}
		}
		HistoricProcessInstanceQuery hpq = processEngine.getHistoryService().createHistoricProcessInstanceQuery();
		List<HistoricProcessInstance> hpi = new ArrayList<HistoricProcessInstance>();
		for (int i = 0; i < str.size(); i++) {
			System.out.println(hpq.processInstanceId(str.get(i)).singleResult());
			hpi.add(hpq.processInstanceId(str.get(i)).singleResult());
		}
		System.out.println("结果*****************************88");
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
	 * 获得全部节点的信息
	 */
	@Test
	public void gettiaojian() {
		BpmnModel bm = processEngine.getRepositoryService().getBpmnModel("Leave:3:5004");
		if (bm != null) {
			// 获取流程定义所有元素
			Collection<FlowElement> flowElements = bm.getMainProcess().getFlowElements();
			List<SequenceFlow> se = new ArrayList<SequenceFlow>();
			// 找出开始节点后的走向
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
     * @Description: 根据实例编号获取下一个任务节点实例集合 
     * @date:2016年3月18日 下午4:33:36 
     * @param sessionId是线程的sessionId；isNeedTransaction表示是否需要事务；procInstId流程实例ID 
     */  
	@Test
    public void getTaskDefinitionList(){  
        HistoryService historyService = processEngine.getHistoryService();  
        RepositoryService repositoryService = processEngine.getRepositoryService();  
        RuntimeService runtimeService = processEngine.getRuntimeService();  
        //List<TaskDefinition> taskDefinitionList = new ArrayList<TaskDefinition>();  
        //流程标示  
        String processDefinitionId = historyService.createHistoricProcessInstanceQuery().processInstanceId("10001").singleResult().getProcessDefinitionId();  
        ProcessDefinitionEntity pde = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);  
        //ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl)repositoryService).getDeployedProcessDefinition(processDefinitionId);  
        //执行实例  
        ExecutionEntity execution = (ExecutionEntity) runtimeService.createProcessInstanceQuery().processInstanceId("10001").singleResult();  
        
        //当前实例的执行到哪个节点  
        String activitiId = execution.getActivityId();  
        //获得当前任务的所有节点  
        List<ActivityImpl> activitiList = pde.getActivities();  
        String id = null;  
        for(ActivityImpl activityImpl:activitiList){    
        	
        	id = activityImpl.getId(); 
            if(activitiId.equals(id)){ 
                System.out.println("当前任务："+activityImpl.getProperty("name"));  
                //taskDefinitionList = nextTaskDefinition(activityImpl, activityImpl.getId());  
            }   
        }  
    }  



	
	/** 
     * 获取下一个用户任务用户组信息  
     * @param String taskId     任务Id信息  
     * @return  下一个用户任务用户组信息  
     * @throws Exception 
     *//*  
    public Set<Expression> getNextTaskGroup(String taskId) throws Exception {  
          
        ProcessDefinitionEntity processDefinitionEntity = null;  
          
        String id = null;  
          
        TaskDefinition task = null;  
          
        //获取流程实例Id信息   
        String processInstanceId = taskService.createTaskQuery().taskId(taskId).singleResult().getProcessInstanceId();  
          
        //获取流程发布Id信息   
        String definitionId = runService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult().getProcessDefinitionId();  
          
        processDefinitionEntity = (ProcessDefinitionEntity) ((RepositoryServiceImpl) rs)  
                .getDeployedProcessDefinition(definitionId);  
          
        ExecutionEntity execution = (ExecutionEntity) runService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();  
          
        //当前流程节点Id信息   
        String activitiId = execution.getActivityId();    
          
        //获取流程所有节点信息   
        List<ActivityImpl> activitiList = processDefinitionEntity.getActivities();   
          
        //遍历所有节点信息   
        for(ActivityImpl activityImpl : activitiList){      
            id = activityImpl.getId();     
              
            // 找到当前节点信息  
            if (activitiId.equals(id)) {  
                  
                //获取下一个节点信息   
                task = nextTaskDefinition(activityImpl, activityImpl.getId(), null, processInstanceId);  
  
                break;  
            }  
        }  
          
        return task.getCandidateGroupIdExpressions();  
    }  
      
    *//**  
     * 下一个任务节点信息,  
     *  
     * 如果下一个节点为用户任务则直接返回,  
     *  
     * 如果下一个节点为排他网关, 获取排他网关Id信息, 根据排他网关Id信息和execution获取流程实例排他网关Id为key的变量值,  
     * 根据变量值分别执行排他网关后线路中的el表达式, 并找到el表达式通过的线路后的用户任务信息  
     * @param ActivityImpl activityImpl     流程节点信息  
     * @param String activityId             当前流程节点Id信息  
     * @param String elString               排他网关顺序流线段判断条件, 例如排他网关顺序留线段判断条件为${money>1000}, 若满足流程启动时设置variables中的money>1000, 则流程流向该顺序流信息  
     * @param String processInstanceId      流程实例Id信息  
     * @return  
     *//*    
    private TaskDefinition nextTaskDefinition(ActivityImpl activityImpl, String activityId, String elString, String processInstanceId){   
              
        PvmActivity ac = null;     
        Object s = null;  
          
        //如果遍历节点为用户任务并且节点不是当前节点信息   
            if("userTask".equals(activityImpl.getProperty("type")) && !activityId.equals(activityImpl.getId())){    
                //获取该节点下一个节点信息   
                TaskDefinition taskDefinition = ((UserTaskActivityBehavior)activityImpl.getActivityBehavior()).getTaskDefinition();    
                return taskDefinition;    
            }else{    
                //获取节点所有流向线路信息   
                List<PvmTransition> outTransitions = activityImpl.getOutgoingTransitions();    
                List<PvmTransition> outTransitionsTemp = null;    
                for(PvmTransition tr : outTransitions){      
                    ac = tr.getDestination(); //获取线路的终点节点      
                    //如果流向线路为排他网关   
                    if("exclusiveGateway".equals(ac.getProperty("type"))){    
                        outTransitionsTemp = ac.getOutgoingTransitions();      
                        //如果网关路线判断条件为空信息   
                        if(isEmpty(elString)) {  
                            //获取流程启动时设置的网关判断条件信息   
                            elString = getGatewayCondition(ac.getId(), processInstanceId);  
                        }  
                          
                        //如果排他网关只有一条线路信息   
                        if(outTransitionsTemp.size() == 1){    
                            return nextTaskDefinition((ActivityImpl)outTransitionsTemp.get(0).getDestination(), activityId, elString, processInstanceId);    
                        }else if(outTransitionsTemp.size() > 1){  //如果排他网关有多条线路信息   
                            for(PvmTransition tr1 : outTransitionsTemp){    
                                s = tr1.getProperty("conditionText");  //获取排他网关线路判断条件信息   
                                //判断el表达式是否成立   
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
     * 查询流程启动时设置排他网关判断条件信息  
     * @param String gatewayId          排他网关Id信息, 流程启动时设置网关路线判断条件key为网关Id信息  
     * @param String processInstanceId  流程实例Id信息  
     * @return 
     *//*  
    public String getGatewayCondition(String gatewayId, String processInstanceId) {  
        Execution execution = runService.createExecutionQuery().processInstanceId(processInstanceId).singleResult();  
        return runService.getVariable(execution.getId(), gatewayId).toString();  
    }  
      
    *//** 
     * 根据key和value判断el表达式是否通过信息  
     * @param String key    el表达式key信息  
     * @param String el     el表达式信息  
     * @param String value  el表达式传入值信息  
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
