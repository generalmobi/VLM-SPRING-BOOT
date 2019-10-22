package com.vlm.ui.support.entities;

import java.util.Date;

public class Manufacturer 
{
	private String id;
	private String name;
	private Date dateofEstablishment;
	private String country;
	private String legalEntity;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getLegalEntity() {
		return legalEntity;
	}
	public void setLegalEntity(String legalEntity) {
		this.legalEntity = legalEntity;
	}
	public Date getDateofEstablishment() {
		return dateofEstablishment;
	}
	public void setDateofEstablishment(Date dateofEstablishment) {
		this.dateofEstablishment = dateofEstablishment;
	}
	
	
	
}
