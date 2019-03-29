package com.aoji.model;

/**
 * @author yangsaixing
 * @description
 * @date Created in 下午4:13 2018/10/15
 */
public class Ks3UploadConfig {
    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件路径
     */
    private String filePath;
    /**
     * 本地前缀
     */
    private String localPreFix;

    /**
     *元数据的内容类型
     */
    private String contentType;

    /**
     * 用来设置业务子目录
     */
    private String  buspath;

    /**
     * 路径格式
     */
    private String pathFormat;

    /**
     * 是否私密访问，true-私密访问；false-公开访问
     */
    private Boolean privateAccess;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Boolean getPrivateAccess() {
        return privateAccess;
    }

    public void setPrivateAccess(Boolean privateAccess) {
        this.privateAccess = privateAccess;
    }

    public String getLocalPreFix() {
        return localPreFix;
    }

    public void setLocalPreFix(String localPreFix) {
        this.localPreFix = localPreFix;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getBuspath() {
        return buspath;
    }

    public void setBuspath(String buspath) {
        this.buspath = buspath;
    }

    public String getPathFormat() {
        return pathFormat;
    }

    public void setPathFormat(String pathFormat) {
        this.pathFormat = pathFormat;
    }
}
