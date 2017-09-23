package com.sa.dev.batch.service;

import com.sa.dev.batch.entity.FileJob;

/**
 * Created by qu04jl on 6-9-2017.
 */
public interface FileService {

    FileJob createFile(FileJob fileJob) throws Exception;
    FileJob findByName(String name);
}
