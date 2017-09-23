package com.sa.dev.batch.processor;

import com.sa.dev.batch.entity.OverstaptabelHeader;
import com.sa.dev.batch.entity.OverstaptabelRecord;
import com.sa.dev.batch.entity.OverstaptabelRow;
import com.sa.dev.batch.entity.OverstaptabelTrailer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

/**
 * Created by qu04jl on 4-9-2017.
 */
@Slf4j
public class OverstaptabelRowProcessor implements ItemProcessor<OverstaptabelRow, OverstaptabelRow> {

    @Override
    public OverstaptabelRow process(OverstaptabelRow r) throws Exception {
        log.info("Process the Item");
        if (r instanceof OverstaptabelHeader) {
            OverstaptabelHeader header = (OverstaptabelHeader) r;
            log.info("RecordCode: " + header.getRecordCode());
            log.info("FileType: " + header.getFileType());
            log.info("Version: " + header.getVersion());
            log.info("CreationDate: " + header.getCreationDate());
            log.info("FileSequence: " + header.getFileSequence());
            log.info("BankCode: " + header.getBankCode());
            log.info("Rekencentrum: " + header.getRekencentrum());
            log.info("TestCode: " + header.getTestCode());
            log.info("DuplicateCode: " + header.getDuplicateCode());
        } else if (r instanceof OverstaptabelRecord) {
            OverstaptabelRecord record = (OverstaptabelRecord) r;
            log.info("RecordCode: " + record.getRecordCode());
            log.info("ActiveFlag: " + record.getActiveFlag());
            log.info("Status: " + record.getStatus());
            log.info("BbanOld: " + record.getBbanOld());
            log.info("BbanNew: " + record.getBbanNew());
            log.info("BankCodeOld: " + record.getBankCodeOld());
            log.info("BankCodeNew: " + record.getBankCodeNew());
            log.info("BicNew: " + record.getBicNew());
            log.info("Name: " + record.getName());
            log.info("Address: " + record.getAddress());
            log.info("Location: " + record.getLocation());
            log.info("StartDateDesirable: " + record.getStartDateDesirable());
            log.info("ExpirationDate: " + record.getExpirationDate());
            log.info("DatacenterNumberOld: " + record.getDatacenterNumberOld());
            log.info("CustomerNumberOld: " + record.getCustomerNumberOld());
            log.info("DatacenterNumberNew: " + record.getDatacenterNumberNew());
            log.info("CustomerNumberNew: " + record.getCustomerNumberNew());
            log.info("StartDateEffective: " + record.getStartDateEffective());
            log.info("BicOld: " + record.getBicOld());
            log.info("IbanOld: " + record.getIbanOld());
            log.info("IbanNew: " + record.getIbanNew());
        } else if (r instanceof OverstaptabelTrailer) {
            OverstaptabelTrailer trailer = (OverstaptabelTrailer) r;
            log.info("RecordCode: " + trailer.getRecordCode());
            log.info("NumberOfRecords: " + trailer.getNumberOfRecords());
            log.info("ChecksumBBANOld: " + trailer.getChecksumBBANOld());
            log.info("ChecksumBBANNew: " + trailer.getChecksumBBANNew());
        }
        return r;
    }
}

