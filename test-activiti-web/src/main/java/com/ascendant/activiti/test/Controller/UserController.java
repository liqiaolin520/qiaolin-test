package com.ascendant.activiti.test.Controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ascendant.activiti.util.UserUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 *	用户操作控制器
 * @author qiaolin
 * @version 2017年3月13日
 *	
 */
@Api(value="用户操作控制器")
@RestController
@RequestMapping("user")
public class UserController {
	Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private IdentityService service;
	
	
	@ApiOperation(value="用户登陆")
	@RequestMapping("login")
	public ModelAndView login(String username,String password,HttpSession session){
		ModelAndView mv = new ModelAndView("redirect:/main/index");
		// 检查用户账户是否正确
		Boolean chackPassword = service.checkPassword(username, password);
		if(chackPassword){
			// 查询到用户，并保存到seesion中
			User user = service.createUserQuery().userId(username).singleResult();
			UserUtil.saveUserToSession(session, user);
			// 获取用户的角色,并存入session
			List<Group> groups = service.createGroupQuery().groupMember(user.getId()).list();
			session.setAttribute("gourps", groups);
			String [] groupNames = new String[groups.size()];
			for (int i = 0; i < groups.size(); i++) {
				groupNames[i] = groups.get(i).getName();
			}
			session.setAttribute("groupNames", groupNames);
		}else{
			mv.setViewName("redirect:/login.jsp");
			mv.addObject("error",true);
			return mv; 
		}
		return mv;
	}
	
	
	@ApiOperation(value="退出登陆")
	@RequestMapping(value="logout")
	public ModelAndView logout(HttpSession session){
		session.removeAttribute("user");
		return new ModelAndView("redirect:/login.jsp");
	}
	
	
	
	
	
	
	
	
	
}
