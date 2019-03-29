package com.aoji.model;

public class ContractFile {
    //合同附件类型编号
    private String FileTypeId;
    //合同附件类型
    private String FileType;
    //合同附件名称
    private String FileName;
    //合同附件路径
    private String FileUrl;

    public String getFileTypeId() {
        return FileTypeId;
    }

    public void setFileTypeId(String fileTypeId) {
        FileTypeId = fileTypeId;
    }

    public String getFileType() {
        return FileType;
    }

    public void setFileType(String fileType) {
        FileType = fileType;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public String getFileUrl() {
        return FileUrl;
    }

    public void setFileUrl(String fileUrl) {
        FileUrl = fileUrl;
    }
}
