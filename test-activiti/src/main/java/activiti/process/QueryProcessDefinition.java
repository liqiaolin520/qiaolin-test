package activiti.process;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Test;

/**
 * 查看流程定义 对应数据库表 ——》 acr_re_procdef
 * 	id:{key}:{version}:{随机值}
 * 	name:对应流程文件peocess节点的name属性
 * 	key:对应流程文件process节点的id属性
 *  version:发布时自动生成的。如果是第一发布的流程呢,version默认从一开始,如果后面发布相同key的流程,则+1
 * @author qiaolin
 *
 */
public class QueryProcessDefinition {
	
	@Test
	public void queryProcess(){
		// 获得核心操作对象。
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		// 获取仓库服务对象，使用版本的升序排序
		List<ProcessDefinition> prList = processEngine.getRepositoryService()
			.createProcessDefinitionQuery()
		    // 添加查询条件
			// .processDefinitionName(processDefinitionName)
			// .processDefinitionId(processDefinitionId)
			// .processDefinitionKey(processDefinitionKey)
			//  排序
			.orderByProcessDefinitionVersion().asc()
			// 查询的结果集
			// .count() // 返回结果集的数量
			// .listPage(firstResult,maxResults) // 分页查询
			// .singleResult() // 唯一的结果集
			.list();  // 总的结果集数量
			
		// 遍历集合，查看内容
		for (ProcessDefinition pd : prList) {
			System.out.println("id:"+pd.getId());
			System.out.println("name:"+pd.getName());
			System.out.println("key:"+pd.getKey());
			System.out.println("version:"+pd.getVersion());
			System.out.println("resourceName:"+pd.getResourceName());
			System.out.println("--------------");
		}
			
	}	
}
