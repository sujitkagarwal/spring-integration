/*
 * Copyright (c) 2017 ING Group. All rights reserved.
 * 
 * This software is the confidential and proprietary information of ING Group ("Confidential Information").
 */
package com.sa.dev.batch.entity;

import java.util.Date;

/**
 * The domain object for the overstaptabel header.
 */
public class OverstaptabelHeader extends OverstaptabelRow {

	private String fileType;
	private String version;
	private Date creationDate;
	private String fileSequence;
	private String bankCode;
	private String rekencentrum;
	private String testCode;
	private String duplicateCode;

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getFileSequence() {
		return fileSequence;
	}

	public void setFileSequence(String fileSequence) {
		this.fileSequence = fileSequence;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getRekencentrum() {
		return rekencentrum;
	}

	public void setRekencentrum(String rekencentrum) {
		this.rekencentrum = rekencentrum;
	}

	public String getTestCode() {
		return testCode;
	}

	public void setTestCode(String testCode) {
		this.testCode = testCode;
	}

	public String getDuplicateCode() {
		return duplicateCode;
	}

	public void setDuplicateCode(String duplicateCode) {
		this.duplicateCode = duplicateCode;
	}

}
