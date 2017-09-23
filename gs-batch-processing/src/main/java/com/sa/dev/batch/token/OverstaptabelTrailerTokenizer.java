/*
 * Copyright (c) 2017 ING Group. All rights reserved.
 * 
 * This software is the confidential and proprietary information of ING Group ("Confidential Information").
 */
package com.sa.dev.batch.token;

import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.Range;

/**
 * The tokenizer for the overstaptabel header.
 */
public class OverstaptabelTrailerTokenizer extends FixedLengthTokenizer {

	private static final String RECORD_CODE = "recordCode";
	private static final String FILLER = "filler";

	String[] trailerNames = { RECORD_CODE,    "numberOfRecords",   "checksumBBANOld", "checksumBBANNew",  FILLER };
	Range[] trailerRanges = { new Range(1, 2), new Range(3, 8), new Range(9, 18),      new Range(19, 28), new Range(29, 250) };

	public OverstaptabelTrailerTokenizer() {
		this.setNames(trailerNames);
		this.setColumns(trailerRanges);
	}
}
