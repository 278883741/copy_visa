package com.aoji.service.impl;

import com.aoji.service.FileService;
import com.aoji.service.KsyunKs3UtilService;
import com.aoji.utils.PathFormat;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author yangsaixing
 * @description
 * @date Created in 下午5:06 2017/11/2
 */
@Service
public class FileServiceImpl  implements FileService {
    @Value("${upload.ks3.resDomain}")
    private String resDomain;

    @Value("${upload.ks3.newResDomain}")
    private String newResDomain;

    @Autowired
    KsyunKs3UtilService ksyunKs3UtilService;

    private static Logger LOGGER = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String yunUpload(MultipartFile file, String localPreFix, String httpPrefix, String buspath, String pathFormat,Boolean privateAccess) {
        String savePath = "";
        try {
            // 获取文件名
            String fileName  = file.getOriginalFilename();

            // 获取文件扩展名
            String extend =  file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            String myFileName = "";
            if(StringUtils.hasText(buspath)  && StringUtils.hasText(pathFormat)) {
                myFileName = PathFormat.parse(pathFormat,fileName,buspath,"")+extend;
            }
            else
            {
                myFileName = fileName;
            }
            // 文件保存全路径
            savePath = (localPreFix.endsWith("/")?localPreFix.substring(0,localPreFix.length()-1):localPreFix) + myFileName;
            byte [] fileData = IOUtils.toByteArray(file.getInputStream());
            //需要上传到对象存储
            ksyunKs3UtilService.putObjectSimple(fileData,savePath,privateAccess);

        } catch (Exception e){
            LOGGER.error("",e);
        }
        return savePath;

    }

    /**
     * 获取文件名称[不含后缀名]
     *
     * @param
     * @return String
     */
    public static String getFilePrefix(String fileName) {
        int splitIndex = fileName.lastIndexOf(".");
        return fileName.substring(0, splitIndex).replaceAll("\\s*", "");
    }

    /**
     * 获取加密后的金山云文件地址
     * @param fileUrl 原始文件url
     * @return
     */
    @Override
    public String getPrivateUrl(String fileUrl) {
        if(fileUrl.contains(this.resDomain)){
            fileUrl = fileUrl.replace(this.resDomain,"");
        }
        if(fileUrl.contains(this.newResDomain)){
            fileUrl = fileUrl.replace(this.newResDomain,"");
        }
        return ksyunKs3UtilService.generatePrivateUrl("",fileUrl);
    }
}
