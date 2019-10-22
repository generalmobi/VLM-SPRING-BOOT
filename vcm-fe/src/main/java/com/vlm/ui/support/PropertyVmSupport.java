package com.vlm.ui.support;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.zkoss.zk.ui.Executions;

public class PropertyVmSupport {


	private WebApplicationContext applicationContext;
    public String  isGranted(String c_authority) {
        return  String.valueOf(SecurityUtil.isGranted(c_authority));
    }
	public PropertyVmSupport() {
		this.autowire();
	}

	private void autowire() {
		this.getApplicationContext().getAutowireCapableBeanFactory()
				.autowireBean(this);
	}

	public ApplicationContext getApplicationContext() {
		if (applicationContext == null) {
			applicationContext = WebApplicationContextUtils
					.getRequiredWebApplicationContext(getServletContext());
		}
		return applicationContext;
	}

	public ServletContext getServletContext() {
		return Executions.getCurrent().getDesktop().getWebApp()
				.getServletContext();
	}

	public void setApplicationContext(WebApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}


}
