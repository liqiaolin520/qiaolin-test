package activiti;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.junit.Test;

/**
 * 查看流程附件（查看流程图片）
 * @author qiaolin
 *
 */
public class ShowImage {
	
	@Test
	public void showImage() throws Exception{
		// 获得核心对象
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		// 从仓库中招到需要展示的文件
		String deploymentId = "1";
		List<String> names = processEngine.getRepositoryService()
			.getDeploymentResourceNames(deploymentId);
		String imageName = null;
		for (String name : names) {
			System.out.println("name"+name);
			if(name.indexOf(".png")>=0){
				imageName = name;
			}
		}

		System.out.println("imageName:"+imageName);
		if(imageName!=null){
			File file = new File("F:/"+imageName);
			file = new File(file.getParent());
			if(!file.exists()){
				file.mkdirs();
			}
			// 通过部署ID和文件名称的到文件输入流
			InputStream in = processEngine.getRepositoryService()
			.getResourceAsStream(deploymentId, imageName);
			int len = 0;
			byte [] buf = new byte[1024];
			while((len = in.read(buf))!=-1){
				OutputStream fos = new FileOutputStream(file);
				fos.write(buf, 0, len);
			}
			System.out.println("图片已复制到:"+file.getPath());
		}
	}
}
