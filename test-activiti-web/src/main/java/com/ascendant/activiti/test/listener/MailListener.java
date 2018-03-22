package com.ascendant.activiti.test.listener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.activiti.engine.IdentityService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.identity.User;

/**
 *  邮件服务监听器，负责给邮件服务传入表达式中的参数。
 * @author qiaolin
 * @date 2017年4月5日
 *
 */

@SuppressWarnings("serial")
public class MailListener implements ExecutionListener{

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		IdentityService identityService = execution.getEngineServices().getIdentityService();
		String applyUserId = (String) execution.getVariable("applyUserId");
		User user = identityService.createUserQuery().userId(applyUserId).singleResult();
		execution.setVariable("to", user.getEmail());
		execution.setVariable("name", user.getFirstName()+" "+user.getLastName());
		execution.setVariable("from", "992004863@qq.com");
		String string = (String) execution.getVariable("endDate");
		Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(string);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		execution.setVariable("timeout", calendar.getTime());
	}
	
	
	
}
