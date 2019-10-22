package com.vlm.ui.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class Resources implements WebMvcConfigurer {


	
	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
 	    registry.addResourceHandler("/assets/**").addResourceLocations("/WEB-INF/assets/");
 
	    registry.addResourceHandler("/css/**").addResourceLocations("/WEB-INF/css/");
	    registry.addResourceHandler("/fonts/**").addResourceLocations("/WEB-INF/fonts/");
	    registry.addResourceHandler("/images/**").addResourceLocations("/WEB-INF/images/");
	    registry.addResourceHandler("/js/**").addResourceLocations("/WEB-INF/js/");
	    
	    
 	   registry.addResourceHandler("swagger-ui.html")
       .addResourceLocations("classpath:/META-INF/resources/");

 	   registry.addResourceHandler("/webjars/**")
       .addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
	
 
	
}
