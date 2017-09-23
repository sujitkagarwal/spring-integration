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
public class OverstaptabelHeaderTokenizer extends FixedLengthTokenizer {

	private static final String RECORD_CODE = "recordCode";
	private static final String FILLER = "filler";

	private String[] headerNames = { RECORD_CODE,    "fileType",       "version",         "creationDate",    "fileSequence",    "bankCode",        "rekencentrum",    "testCode",        "duplicateCode",    FILLER };
	private Range[] headerRanges = { new Range(1, 2), new Range(3, 12), new Range(13, 15), new Range(16, 23), new Range(24, 25), new Range(26, 28), new Range(29, 30), new Range(31, 31), new Range(32, 32), new Range(33, 250) };

	public OverstaptabelHeaderTokenizer() {
		this.setNames(headerNames);
		this.setColumns(headerRanges);
	}
}
