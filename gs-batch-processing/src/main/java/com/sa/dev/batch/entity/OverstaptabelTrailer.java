/*
 * Copyright (c) 2017 ING Group. All rights reserved.
 * 
 * This software is the confidential and proprietary information of ING Group ("Confidential Information").
 */
package com.sa.dev.batch.entity;

/**
 * Domain object for the footer of the overstaptabel.
 */
public class OverstaptabelTrailer extends OverstaptabelRow {

	private int numberOfRecords;
	private long checksumBBANOld;
	private long checksumBBANNew;

	public int getNumberOfRecords() {
		return numberOfRecords;
	}

	public void setNumberOfRecords(int numberOfRecords) {
		this.numberOfRecords = numberOfRecords;
	}

	public long getChecksumBBANOld() {
		return checksumBBANOld;
	}

	public void setChecksumBBANOld(long checksumBBANOld) {
		this.checksumBBANOld = checksumBBANOld;
	}

	public long getChecksumBBANNew() {
		return checksumBBANNew;
	}

	public void setChecksumBBANNew(long checksumBBANNew) {
		this.checksumBBANNew = checksumBBANNew;
	}

}
