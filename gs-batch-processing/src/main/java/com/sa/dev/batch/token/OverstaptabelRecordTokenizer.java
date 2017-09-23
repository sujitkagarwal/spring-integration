/*
 * Copyright (c) 2017 ING Group. All rights reserved.
 * 
 * This software is the confidential and proprietary information of ING Group ("Confidential Information").
 */
package com.sa.dev.batch.token;

import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.Range;

/**
 * The tokenizer for the overstaptabel record.
 */
public class OverstaptabelRecordTokenizer extends FixedLengthTokenizer {

	private static final String RECORD_CODE = "recordCode";
	private static final String FILLER = "filler";

	private String[] recordNames = { RECORD_CODE,    "activeFlag",    "status",        "bbanOld",        "bbanNew",         "bankCodeOld",     "bankCodeNew",     "bicNew",          "name",            "address",          "location",          "startDateDesirable", "expirationDate",    "datacenterNumberOld", "customerNumberOld", "datacenterNumberNew", "customerNumberNew", "startDateEffective", "bicOld",            "ibanOld",           "ibanNew",            FILLER };
	private Range[] recordRanges = { new Range(1, 2), new Range(3, 3), new Range(4, 5), new Range(6, 15), new Range(16, 25), new Range(26, 28), new Range(29, 31), new Range(32, 42), new Range(43, 77), new Range(78, 112), new Range(113, 147), new Range(148, 155),  new Range(156, 163), new Range(164, 165),   new Range(166, 170), new Range(171, 172),   new Range(173, 177), new Range(178, 185),  new Range(186, 196), new Range(197, 214), new Range(215, 232), new Range(233, 250) };

	public OverstaptabelRecordTokenizer() {
		this.setNames(recordNames);
		this.setColumns(recordRanges);
	}
}
