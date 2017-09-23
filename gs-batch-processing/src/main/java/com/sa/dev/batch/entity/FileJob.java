package com.sa.dev.batch.entity;

import javax.persistence.*;

/**
 * Created by qu04jl on 6-9-2017.
 */
@Entity
@Table(name = "File")
public class FileJob {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "read_count")
    private Integer readCount;
    @Column(name = "write_count")
    private Integer writeCount;
    @Column(name = "skip_count")
    private Integer skipCount;
    @Column(name = "commit_count")
    private Integer commitCount;
    @Column(name = "status")
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public Integer getWriteCount() {
        return writeCount;
    }

    public void setWriteCount(Integer writeCount) {
        this.writeCount = writeCount;
    }

    public Integer getSkipCount() {
        return skipCount;
    }

    public void setSkipCount(Integer skipCount) {
        this.skipCount = skipCount;
    }

    public Integer getCommitCount() {
        return commitCount;
    }

    public void setCommitCount(Integer commitCount) {
        this.commitCount = commitCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
