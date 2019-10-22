package com.vlm.ui.support;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModel;

public abstract class PagingSupport <T> extends PropertyVmSupport{
	protected int pageSize = 20;

	public abstract ListModel<T> getRows();

	public abstract Long getTotalSize();

	protected int activePage;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getActivePage() {
		return activePage;
	}

	@NotifyChange("rows")
	public void setActivePage(int activePage) {
		this.activePage = activePage;
	}

	private WebApplicationContext applicationContext;

	public PagingSupport() {
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
	
    public String  isGranted(String c_authority) {
        return  String.valueOf(SecurityUtil.isGranted(c_authority));
    }

}
