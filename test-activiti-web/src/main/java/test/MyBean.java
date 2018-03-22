package test;

import java.io.Serializable;
import java.util.Arrays;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;

/**'
 *  测试EL表达式bean. 用于EL表达式的bean必须实现Serializable接口
 * @author qiaolin
 *
 */
@SuppressWarnings("serial")
class MyBean implements Serializable{
	
	public void print(){
		System.out.println("the method is print()");
	}
	
	public String print(String name){
		System.out.println("print name  = "+name);
		return name += "print(String name)";
	}
	
	/**
	 *  接收内置的引擎对象 task
	 * @param task
	 */
	public void invokeTask(DelegateTask task){
		System.out.println(task);
		task.setVariable("setByTask", "啊啊, "+task.getVariable("name"));
	}
	
	/**
	 * 接收内置对象execution。
	 * @param execution
	 * @return
	 */
	public String printKey(DelegateExecution execution){
		String businessKey = execution.getProcessBusinessKey();
		System.out.println("pricessId is "+execution.getProcessInstanceId()+" , BusinessKey is " +businessKey );
		System.out.println(execution);
		return businessKey;
	}
	
	public static void main(String[] args) {
		Long[] xx = getMaterialIds("2:400，3:500");
		System.out.println(Arrays.asList(xx));
	}
	
	private static Long[] getMaterialIds(String materialIds) {
		String fenHao = ",";
		if(materialIds.indexOf("，")>-1){
			fenHao = "，";
		}
		String maoHao = ":";
		if(materialIds.indexOf("：")>-1){
			maoHao = "：";
		}
		String[] record1 = materialIds.split(fenHao);
		Long[] materialIdsAndWeight = new Long[record1.length*2];
		int index = 0;
		for (String string : record1) {
			String[] record2 = string.split(maoHao);
			materialIdsAndWeight[index++] = Long.parseLong(record2[0]);
			materialIdsAndWeight[index++] = Long.parseLong(record2[1]);
		}
		return materialIdsAndWeight;
	}

}