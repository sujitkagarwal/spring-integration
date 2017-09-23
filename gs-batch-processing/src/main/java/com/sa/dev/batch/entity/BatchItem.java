/*
 * Copyright (c) 2017 ING Group. All rights reserved.
 * 
 * This software is the confidential and proprietary information of ING Group ("Confidential Information").
 */
package com.sa.dev.batch.entity;

/**
 * Workable item for the item writer to create an update row in the SDM upload.
 */
public abstract class BatchItem {

	// Values required for the SDM upload
	private String action;
	private String sdmStatus;

	/**
	 * @return an array of the key values
	 */
	public abstract Object[] keyValues();
	
	/**
	 * @return an array of values used for the upload record
	 */
	public abstract Object[] values();

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getSdmStatus() {
		return sdmStatus;
	}

	public void setSdmStatus(String sdmStatus) {
		this.sdmStatus = sdmStatus;
	}
	
}
