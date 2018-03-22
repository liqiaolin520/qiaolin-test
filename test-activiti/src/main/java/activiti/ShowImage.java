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
 * �鿴���̸������鿴����ͼƬ��
 * @author qiaolin
 *
 */
public class ShowImage {
	
	@Test
	public void showImage() throws Exception{
		// ��ú��Ķ���
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		// �Ӳֿ����е���Ҫչʾ���ļ�
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
			// ͨ������ID���ļ����Ƶĵ��ļ�������
			InputStream in = processEngine.getRepositoryService()
			.getResourceAsStream(deploymentId, imageName);
			int len = 0;
			byte [] buf = new byte[1024];
			while((len = in.read(buf))!=-1){
				OutputStream fos = new FileOutputStream(file);
				fos.write(buf, 0, len);
			}
			System.out.println("ͼƬ�Ѹ��Ƶ�:"+file.getPath());
		}
	}
}
