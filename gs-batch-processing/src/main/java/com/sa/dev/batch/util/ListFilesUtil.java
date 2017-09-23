package com.sa.dev.batch.util;


import java.io.File;

/**
 * Created by qu04jl on 20-7-2017.
 */
public class ListFilesUtil {
    /**
     * List all the files and folders from a directory
     *
     * @param directoryName to be listed
     * @return list of file File[]
     */
    public static File[] listFilesAndFolders(String directoryName) {
        File directory = new File(directoryName);
        //get all the files from a directory
        return directory.listFiles();

    }


}