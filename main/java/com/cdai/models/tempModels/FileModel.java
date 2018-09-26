package com.cdai.models.tempModels;

import org.springframework.web.multipart.MultipartFile;
/**
 * Created by apple on 03/09/2017.
 */
public class FileModel {
    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
