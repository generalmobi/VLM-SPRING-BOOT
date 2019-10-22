package com.vlm.ui.dto;

import java.util.Date;

public class RegistrationEvent
{
	private String chasisNumber;
	private Date validFrom;
	private Date validTill;
	private int ownerNumber;
	public String getChasisNumber() {
		return chasisNumber;
	}
	public void setChasisNumber(String chasisNumber) {
		this.chasisNumber = chasisNumber;
	}
	public Date getValidFrom() {
		return validFrom;
	}
	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}
	public Date getValidTill() {
		return validTill;
	}
	public void setValidTill(Date validTill) {
		this.validTill = validTill;
	}
	public int getOwnerNumber() {
		return ownerNumber;
	}
	public void setOwnerNumber(int ownerNumber) {
		this.ownerNumber = ownerNumber;
	}
	
	
}
