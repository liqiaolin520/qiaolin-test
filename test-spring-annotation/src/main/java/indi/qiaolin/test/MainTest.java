package indi.qiaolin.test;

import org.junit.After;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import indi.qiaolin.beans.Cat;
import indi.qiaolin.beans.SpringContextHolder;
import indi.qiaolin.config.MainConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainTest {
	private AnnotationConfigApplicationContext applicationContext;
		
	@Test
	public void test01(){
		applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
		print(applicationContext);
		
		log.debug("»ñµÃµ½Bean -> {}",SpringContextHolder.getBean(Cat.class));
	}
	
	
	
	
	private void print(ApplicationContext applicationContext){
		String applicationName = applicationContext.getApplicationName();
		String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
		log.debug("applicationName - > {}", applicationName);
		for (String string : beanDefinitionNames) {
			log.debug("  bean name -> " + string);
		}
	}
	
	
	@After
	public void after(){
		applicationContext.close();
	}
}
