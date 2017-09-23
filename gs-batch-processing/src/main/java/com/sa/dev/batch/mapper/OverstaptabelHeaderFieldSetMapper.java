package com.sa.dev.batch.mapper;

import com.sa.dev.batch.entity.OverstaptabelHeader;
import com.sa.dev.batch.entity.OverstaptabelRow;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

/**
 * Created by qu04jl on 4-9-2017.
 */
public class OverstaptabelHeaderFieldSetMapper implements FieldSetMapper<OverstaptabelRow> {

    /**
     * Map the data to the domain object.
     */
    public OverstaptabelRow mapFieldSet(FieldSet fieldSet) {
        OverstaptabelHeader header = new OverstaptabelHeader();

        header.setRecordCode(fieldSet.readString("recordCode"));
        header.setFileType(fieldSet.readString("fileType"));
        header.setVersion(fieldSet.readString("version"));
        header.setCreationDate(fieldSet.readDate("creationDate", "yyyyMMdd"));
        header.setFileSequence(fieldSet.readString("fileSequence"));
        header.setBankCode(fieldSet.readString("bankCode"));
        header.setRekencentrum(fieldSet.readString("rekencentrum"));
        header.setTestCode(fieldSet.readString("testCode"));
        header.setDuplicateCode(fieldSet.readString("duplicateCode"));
        return header;
    }

}

