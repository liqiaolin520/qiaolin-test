package com.ascendant.activiti.test.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author qiaolin
 * @version 2017年3月8日
 * 
 */
@Api(value = "部署测试", description = "用户测试部署的各种功能")
@RestController
@RequestMapping("deployment")
public class DeploymentController {
 
	@Autowired
	private RepositoryService repositoryService;

	@ApiOperation(value = "查看已部署的流程", notes = "用户查看已经部署的流程")
	@RequestMapping(value = "processList", method = RequestMethod.GET)
	public ModelAndView processList() {
		List<ProcessDefinition> instances = repositoryService.createProcessDefinitionQuery().list();
		ModelAndView mv = new ModelAndView("process-list");
		mv.addObject("list", instances);
		return mv;
	}

	@ApiOperation(value = "部署流程定义", notes = "通过WEB部署流程定义,支持Zip、Bar、bpmn、bpmn20.xml类型")
	@RequestMapping(value = "deploy", method = RequestMethod.POST)
	public ModelAndView deploy(@RequestParam(value = "file") MultipartFile file, ModelAndView mv) {
		String filename = file.getOriginalFilename();
		// 获得文件后缀名
		String extension = FilenameUtils.getExtension(filename);
		// 获得部署引擎
		DeploymentBuilder deploy = repositoryService.createDeployment();
		// 得到上传文件的输入流
		InputStream in = null;
		try {
			in = file.getInputStream();
			if ("zip".equals(extension) || "bar".equals(extension)) {
				deploy.addZipInputStream(new ZipInputStream(in));
			} else {
				deploy.addInputStream(filename, in);
			}
			// 进行部署
			deploy.deploy();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("获得文件输入流失败");
		}
		mv.setViewName("redirect:/deployment/processList");
		return mv;
	}

	@ApiOperation(value="查看xml资源文件",notes="需要参数:流程定义Id(pdid),资源名称(name)")
	@RequestMapping(value = "readResource",method = RequestMethod.GET)
	public void readResource(@RequestParam("pdid") String pdid,@RequestParam("name")String name,HttpServletResponse response){
		// 根据流程定义Id获得到流程信息
		ProcessDefinition instance = repositoryService.createProcessDefinitionQuery().processDefinitionId(pdid).singleResult();
		// 获得文件IO流
		InputStream in = repositoryService.getResourceAsStream(instance.getDeploymentId(), name);
		// 通过response响应给用户
		byte [] buf = new byte[1024];
		int len = -1;
		try {
			while((len = in.read(buf))>0){
				response.getOutputStream().write(buf,0,len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@ApiOperation(value="查看图片资源",notes="参数:流程定义Id(pdid),图片名称")
	@RequestMapping(value = "readPng",method=RequestMethod.GET)
	public void readPng(String pdid,String fileName,HttpServletResponse response){
		ProcessDefinition instance = repositoryService.createProcessDefinitionQuery().processDefinitionId(pdid).singleResult();
		InputStream in = repositoryService.getResourceAsStream(instance.getDeploymentId(), fileName);
		byte [] buf = new byte[1024];
		int len = -1;
		try {
			while((len = in.read(buf))>0){
				response.getOutputStream().write(buf,0,len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@ApiOperation(value="删除已部署流程",notes="参数:[deploymentId(部署Id)]")
	@RequestMapping(value="deleteDeploy",method=RequestMethod.DELETE)
	public ModelAndView deleteDeploy(@RequestParam("deploymentId")String dId,ModelAndView mv){
		// 根据流程部署Id删除已部署的流程,true表示,连同正在运行的实例，或者已经结束的实例一起删除。
		repositoryService.deleteDeployment(dId,true);
		mv.setViewName("redirect:/deployment/processList");
		return mv;
	}
	
	
	
}
