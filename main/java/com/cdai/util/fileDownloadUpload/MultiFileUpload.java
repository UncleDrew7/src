package com.cdai.util.fileDownloadUpload;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by apple on 03/09/2017.
 */
public class MultiFileUpload {

    private List<MultipartFile> multiUploadedFileList;

    public List<MultipartFile> getMultiUploadedFileList() {
        return multiUploadedFileList;
    }

    public void setMultiUploadedFileList(List<MultipartFile>
                                                 multiUploadedFileList) {
        this.multiUploadedFileList = multiUploadedFileList;
    }

}
