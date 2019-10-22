package com.vlm.ui.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

 	
	@Override
	protected void configure(HttpSecurity http) throws Exception 
	{
		http.sessionManagement().sessionFixation().none();
	 	if (System.getProperty("channel", "http").equals("https"))
		{
			http.requiresChannel().anyRequest().requiresSecure();
		}
        
		http.authorizeRequests()
			.antMatchers(
					"/zkau*",				// <--- for zk ajax (internal)
					"/login*", "/logout",	// <--- for login/logout
					"/assets/**","/api/**", 	// <--- static resources...
					"/swagger-ui.html/**", "/configuration/**","/swagger-resources/**", "/v2/api-docs","/webjars/**"
					)
			.permitAll().anyRequest().authenticated().and()
			.headers().frameOptions().disable().and()
 			.formLogin().successHandler(new AuthenticationSuccessHandler() {
 				@Override
				public void onAuthenticationSuccess(HttpServletRequest request,	HttpServletResponse response, Authentication authentication) throws IOException,	ServletException
				{
 					response.sendRedirect("dashboard.html");
 				}
			}).
 			loginPage("/login.jsp").permitAll().defaultSuccessUrl("/dashboard.html", false).and() // this redirect allways to testVM.zul page
			.logout().permitAll().and()
			.csrf().disable();
	}
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
  
       // auth.eraseCredentials(false).userDetailsService(userService).passwordEncoder(passwordEncoder);;

        auth.inMemoryAuthentication()
                .withUser("user").password("{noop}password").roles("USER");


    }
	
	  
	 	 


}
