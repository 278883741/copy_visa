package com.aoji.model.res;

import com.aoji.model.BaseResponse;

import java.util.List;

public class FileRes  {
    private String code;
    public void setCode(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }

    private List<FileTypeRes> data;
    public void setData(List<FileTypeRes> data) {
        this.data = data;
    }
    public List<FileTypeRes> getData() {
        return data;
    }

    private String message;
    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
