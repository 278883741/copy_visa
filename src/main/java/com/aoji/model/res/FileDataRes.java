package com.aoji.model.res;

import com.aoji.model.BaseResponse;

public class FileDataRes {
    private Integer fileId;
    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }
    public Integer getFileId() {
        return fileId;
    }

    private String fileName;
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getFileName() {
        return fileName;
    }

    private String fileUrl;
    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
    public String getFileUrl() {
        return fileUrl;
    }

    private String fileCreateDate;
    public void setFileCreateDate(String fileCreateDate) {
        this.fileCreateDate = fileCreateDate;
    }
    public String getFileCreateDate() {
        return fileCreateDate;
    }
}
