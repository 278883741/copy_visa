package com.aoji.model.res;

import com.aoji.model.BaseResponse;

import java.util.List;

public class FileTypeRes  {
    private Integer paperId;
    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
    }
    public Integer getPaperId() {
        return paperId;
    }

    private String paperName;
    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }
    public String getPaperName() {
        return paperName;
    }

    private boolean needUpload;
    public void setNeedUpload(boolean needUpload) {
        this.needUpload = needUpload;
    }
    public boolean isNeedUpload() {
        return needUpload;
    }

    private List<FileDataRes> fileList;
    public void setFileList(List<FileDataRes> fileList) {
        this.fileList = fileList;
    }
    public List<FileDataRes> getFileList() {
        return fileList;
    }
}
