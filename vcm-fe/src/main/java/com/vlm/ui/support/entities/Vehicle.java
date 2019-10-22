package com.vlm.ui.support.entities;

public class Vehicle
{
	private String id;
	private String name;
	private Manufacturer manifacturer;
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
	public Manufacturer getManifacturer() {
		return manifacturer;
	}
	public void setManifacturer(Manufacturer manifacturer) {
		this.manifacturer = manifacturer;
	}

	
}
