package com.ascendant.activiti.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
* @author qiaolin
* @version 2017年3月8日
*
*/

@Configuration
@EnableWebMvc
@EnableSwagger2
@ComponentScan("com.ascendant.activiti.test")
public class SwaggerConfig {

	@Bean
	public Docket platformApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("full-platform").apiInfo(apiInfo())
				.forCodeGeneration(true);
	}

	private ApiInfo apiInfo() { 
		return new ApiInfoBuilder().title("ActivitiTest-WEB-API").description("©2016 Copyright. Powered By base.")
				// .termsOfServiceUrl("")
				.contact(new Contact("base", "", "base@163.com")).license("Apache License Version 2.0")
				.licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE").version("2.0").build();
	}
}
