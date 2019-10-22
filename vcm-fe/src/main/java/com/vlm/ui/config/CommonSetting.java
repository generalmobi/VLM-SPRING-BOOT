package com.vlm.ui.config;

import java.text.SimpleDateFormat;

import org.apache.commons.lang.time.FastDateFormat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
@Configuration
public class CommonSetting {
	
	 @Bean
	    @Primary
	    public ObjectMapper serializingObjectMapper() {
	        ObjectMapper objectMapper = new ObjectMapper();
	        objectMapper.registerModule(new Jdk8Module());
	        SimpleDateFormat df = new SimpleDateFormat(CommonSetting.jacksonDateFormat);
	        objectMapper.setDateFormat(df);
 	        return objectMapper;
	    }	
	 
	 
	public static final String jacksonDateFormat ="yyyy-MM-dd HH:mm:ss"; 
	public static final FastDateFormat jacksonFastDateFormat =FastDateFormat.getInstance(jacksonDateFormat); 

}
