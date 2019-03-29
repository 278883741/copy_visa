package com.aoji.service.impl;

import com.aoji.model.Ks3UploadConfig;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author yangsaixing
 * @description
 * @date Created in 下午4:11 2018/10/15
 */
public abstract class UploadAbstract {

    /**
     *
     * @param file
     * @param config
     * @return
     */
    abstract String uploadFile(MultipartFile file, Ks3UploadConfig config);
}
