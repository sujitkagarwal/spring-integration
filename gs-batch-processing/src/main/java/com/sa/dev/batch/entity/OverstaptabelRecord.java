/*
 * Copyright (c) 2017 ING Group. All rights reserved.
 * 
 * This software is the confidential and proprietary information of ING Group ("Confidential Information").
 */
package com.sa.dev.batch.entity;

import java.util.Date;

/**
 * Domain object for a record of the overstaptabel.
 */
public class OverstaptabelRecord extends OverstaptabelRow {

	/** Active */
	public static final String ACTIVE_FLAG_ACTIVE = "1";
	/** Not yet active, should end up in SDM because they could become active */
	public static final String ACTIVE_FLAG_NOT_YET_ACTIVE = "2";
	/** Not active anymore, these rows should not end up in SDM */
	public static final String ACTIVE_FLAG_INACTIVE = "3";

	// Values from the imported file
	private String activeFlag;
	private String status;
	private String bbanOld;
	private String bbanNew;
	private String bankCodeOld;
	private String bankCodeNew;
	private String bicNew;
	private String name;
	private String address;
	private String location;
	private Date startDateDesirable;
	private Date expirationDate;
	private String datacenterNumberOld;
	private String customerNumberOld;
	private String datacenterNumberNew;
	private String customerNumberNew;
	private Date startDateEffective;
	private String bicOld;
	private String ibanOld;
	private String ibanNew;

	@Override
	public Object[] keyValues() {
		return new Object[] { this.bbanOld, this.bbanNew };
	}

	@Override
	public Object[] values() {
		return new Object[] { this.bbanOld, this.bbanNew, this.status, this.bankCodeOld, this.bankCodeNew, this.bicNew, this.name,
				this.address, this.location, this.startDateDesirable, this.expirationDate, this.datacenterNumberOld, this.customerNumberOld,
				this.datacenterNumberNew, this.customerNumberNew, this.startDateEffective, this.bicOld, this.ibanOld, this.ibanNew };
	}

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBbanOld() {
		return bbanOld;
	}

	public void setBbanOld(String bbanOld) {
		this.bbanOld = bbanOld;
	}

	public String getBbanNew() {
		return bbanNew;
	}

	public void setBbanNew(String bbanNew) {
		this.bbanNew = bbanNew;
	}

	public String getBankCodeOld() {
		return bankCodeOld;
	}

	public void setBankCodeOld(String bankCodeOld) {
		this.bankCodeOld = bankCodeOld;
	}

	public String getBankCodeNew() {
		return bankCodeNew;
	}

	public void setBankCodeNew(String bankCodeNew) {
		this.bankCodeNew = bankCodeNew;
	}

	public String getBicNew() {
		return bicNew;
	}

	public void setBicNew(String bicNew) {
		this.bicNew = bicNew;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getStartDateDesirable() {
		return startDateDesirable;
	}

	public void setStartDateDesirable(Date startDateDesirable) {
		this.startDateDesirable = startDateDesirable;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getDatacenterNumberOld() {
		return datacenterNumberOld;
	}

	public void setDatacenterNumberOld(String datacenterNumberOld) {
		this.datacenterNumberOld = datacenterNumberOld;
	}

	public String getCustomerNumberOld() {
		return customerNumberOld;
	}

	public void setCustomerNumberOld(String customerNumberOld) {
		this.customerNumberOld = customerNumberOld;
	}

	public String getDatacenterNumberNew() {
		return datacenterNumberNew;
	}

	public void setDatacenterNumberNew(String datacenterNumberNew) {
		this.datacenterNumberNew = datacenterNumberNew;
	}

	public String getCustomerNumberNew() {
		return customerNumberNew;
	}

	public void setCustomerNumberNew(String customerNumberNew) {
		this.customerNumberNew = customerNumberNew;
	}

	public Date getStartDateEffective() {
		return startDateEffective;
	}

	public void setStartDateEffective(Date startDateEffective) {
		this.startDateEffective = startDateEffective;
	}

	public String getBicOld() {
		return bicOld;
	}

	public void setBicOld(String bicOld) {
		this.bicOld = bicOld;
	}

	public String getIbanOld() {
		return ibanOld;
	}

	public void setIbanOld(String ibanOld) {
		this.ibanOld = ibanOld;
	}

	public String getIbanNew() {
		return ibanNew;
	}

	public void setIbanNew(String ibanNew) {
		this.ibanNew = ibanNew;
	}

}
