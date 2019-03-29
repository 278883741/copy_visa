package com.aoji.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author yangsaixing
 * @description
 * @date Created in 下午5:05 2017/11/2
 */
public interface FileService {

     /**
      * 上传文件
      * @param file 文件流
      * @param localPreFix  前缀
      * @param httpPrefix
      * @param buspath
      * @param pathFormat
      * @param privateAccess 是否私密上传：true-private 私密；false-公开
      * @return
      */
     String yunUpload( MultipartFile file, String localPreFix, String httpPrefix, String buspath,String pathFormat,Boolean privateAccess);

     String getPrivateUrl(String fileUrl);
}
