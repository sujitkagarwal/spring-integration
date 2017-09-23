/*
 * Copyright (c) 2017 ING Group. All rights reserved.
 * 
 * This software is the confidential and proprietary information of ING Group ("Confidential Information").
 */
package com.sa.dev.batch.mapper;



import com.sa.dev.batch.entity.OverstaptabelRecord;
import com.sa.dev.batch.entity.OverstaptabelRow;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

/**
 * The FieldSetMapper for the header of the overstaptabel.
 */
public class OverstaptabelRecordFieldSetMapper implements FieldSetMapper<OverstaptabelRow> {

	/**
	 * Map the data to the domain object.
	 */
	public OverstaptabelRow mapFieldSet(FieldSet fieldSet) {
		OverstaptabelRecord record = new OverstaptabelRecord();

		record.setRecordCode(fieldSet.readString("recordCode"));
		record.setActiveFlag(fieldSet.readString("activeFlag"));
		record.setStatus(fieldSet.readString("status"));
		record.setBbanOld(fieldSet.readString("bbanOld"));
		record.setBbanNew(fieldSet.readString("bbanNew"));
		record.setBankCodeOld(fieldSet.readString("bankCodeOld"));
		record.setBankCodeNew(fieldSet.readString("bankCodeNew"));
		record.setBicNew(fieldSet.readString("bicNew"));
		record.setName(fieldSet.readString("name"));
		record.setAddress(fieldSet.readString("address"));
		record.setLocation(fieldSet.readString("location"));
		try {
			record.setStartDateDesirable(fieldSet.readDate("startDateDesirable" , "yyyyMMdd"));
		} catch (IllegalArgumentException e) {
			record.setStartDateDesirable(null);
		}
		try {
			record.setExpirationDate(fieldSet.readDate("expirationDate" , "yyyyMMdd"));
		} catch (IllegalArgumentException e) {
			record.setExpirationDate(null);
		}
		record.setDatacenterNumberOld(fieldSet.readString("datacenterNumberOld"));
		record.setCustomerNumberOld(fieldSet.readString("customerNumberOld"));
		record.setDatacenterNumberNew(fieldSet.readString("datacenterNumberNew"));
		record.setCustomerNumberNew(fieldSet.readString("customerNumberNew"));
		try {
			record.setStartDateEffective(fieldSet.readDate("startDateEffective" , "yyyyMMdd"));
		} catch (IllegalArgumentException e) {
			record.setStartDateEffective(null);
		}
		record.setBicOld(fieldSet.readString("bicOld"));
		record.setIbanOld(fieldSet.readString("ibanOld"));
		record.setIbanNew(fieldSet.readString("ibanNew"));

		return record;
	}

}
