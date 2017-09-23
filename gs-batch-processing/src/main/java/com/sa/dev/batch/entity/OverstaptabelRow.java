/*
 * Copyright (c) 2017 ING Group. All rights reserved.
 * 
 * This software is the confidential and proprietary information of ING Group ("Confidential Information").
 */
package com.sa.dev.batch.entity;


/**
 * The domain object for a overstaptabel row.
 * This is the ancestor of the header, record and trailer domain objects.
 */
public abstract class OverstaptabelRow extends BatchItem {

	private String recordCode;

	public String getRecordCode() {
		return recordCode;
	}

	public void setRecordCode(String recordCode) {
		this.recordCode = recordCode;
	}

	@Override
	public Object[] keyValues() {
		// Only required for the record itself, override there again
		return null;
	}

	@Override
	public Object[] values() {
		// Only required for the record itself, override there again
		return null;
	}
}
