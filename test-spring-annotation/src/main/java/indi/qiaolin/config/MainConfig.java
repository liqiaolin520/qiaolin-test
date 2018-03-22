package indi.qiaolin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import indi.qiaolin.beans.Cat;
import indi.qiaolin.beans.SpringContextHolder;


@Configuration()
@ComponentScan("indi.qiaolin.beans.scan")
@Import({SpringContextHolder.class})
public class MainConfig {
 
	
	@Bean
	public Cat car(){
		return new Cat();
	}
}
