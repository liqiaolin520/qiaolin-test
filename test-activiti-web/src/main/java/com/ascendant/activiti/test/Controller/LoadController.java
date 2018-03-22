package com.ascendant.activiti.test.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
* @author qiaolin
* @version 2017年3月13日
* 加载Jsp页面
*/

@Api(value="用于加载Jsp页面")
@Controller
@RequestMapping("main")
public class LoadController {
	
	@ApiOperation("加载welcome页面")
	@RequestMapping("welcome")
	public String welcome(){
		return "main/welcome";
	}
	
	@ApiOperation("加载index页面")
	@RequestMapping("index")
	public String index(){
		return "main/index";
	}
	
	
	
	
	
	
}
