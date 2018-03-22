package indi.qiaolin.beans;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SpringContextHolder implements ApplicationContextAware,DisposableBean{
	private static ApplicationContext applicationContext;
	
	public static <T> T getBean(Class<T> cls){
		return applicationContext.getBean(cls);
	}
	
	public static Object getBean(String beanName){
		return applicationContext.getBean(beanName);
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextHolder.applicationContext = applicationContext;
	}

	@Override
	public void destroy() throws Exception {
		applicationContext = null;
		log.info("SpringContextHolder Ïú»Ù!");
	}

}
