package com.sa.dev.batch.service.impl;

import com.sa.dev.batch.entity.FileJob;
import com.sa.dev.batch.repository.FileRepository;
import com.sa.dev.batch.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by qu04jl on 6-9-2017.
 */
@Service
@Transactional
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;

    @Override
    public FileJob createFile(FileJob fileJob)throws Exception {
        return fileRepository.save(fileJob);
    }

    @Override
    public FileJob findByName(String name) {
        return fileRepository.findByName(name);
    }
}
