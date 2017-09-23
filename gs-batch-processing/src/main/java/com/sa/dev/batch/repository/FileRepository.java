package com.sa.dev.batch.repository;


import com.sa.dev.batch.entity.FileJob;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by qu04jl on 6-9-2017.
 */
@Repository
public interface FileRepository extends CrudRepository<FileJob,Long> {
    FileJob findByName(String name);
}
