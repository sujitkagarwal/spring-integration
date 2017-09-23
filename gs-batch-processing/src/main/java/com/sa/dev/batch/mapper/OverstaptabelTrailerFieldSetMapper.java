/*
 * Copyright (c) 2017 ING Group. All rights reserved.
 * 
 * This software is the confidential and proprietary information of ING Group ("Confidential Information").
 */
package com.sa.dev.batch.mapper;


import com.sa.dev.batch.entity.OverstaptabelRow;
import com.sa.dev.batch.entity.OverstaptabelTrailer;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

/**
 * The FieldSetMapper for the trailer of the overstaptabel.
 */
public class OverstaptabelTrailerFieldSetMapper implements FieldSetMapper<OverstaptabelRow> {

	/**
	 * Map the data to the domain object.
	 */
	public OverstaptabelRow mapFieldSet(FieldSet fieldSet) {
		OverstaptabelTrailer trailer = new OverstaptabelTrailer();

		trailer.setRecordCode(fieldSet.readString("recordCode"));
		trailer.setNumberOfRecords(fieldSet.readInt("numberOfRecords"));
		trailer.setChecksumBBANOld(fieldSet.readLong("checksumBBANOld"));
		trailer.setChecksumBBANNew(fieldSet.readLong("checksumBBANNew"));

		return trailer;
	}

}
