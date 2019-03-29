package com.aoji.service.impl;

import com.aoji.model.Ks3UploadConfig;
import com.ksyun.ks3.dto.CannedAccessControlList;
import com.ksyun.ks3.dto.ObjectMetadata;
import com.ksyun.ks3.http.HttpClientConfig;
import com.ksyun.ks3.service.Ks3;
import com.ksyun.ks3.service.Ks3Client;
import com.ksyun.ks3.service.Ks3ClientConfig;
import com.ksyun.ks3.service.request.PutObjectRequest;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * @author yangsaixing
 * @description
 * @date Created in 下午5:11 2018/10/15
 */
public class KsYunUpload  extends UploadAbstract{
    //如果使用自定义域名，设置endpoint为自定义域名，同时设置domainMode为true
    @Value("${upload.ks3.resDomain}")
    private String  resDomain;
    @Value("${upload.ks3.endpoint}")
    private String  endpoint;
    @Value("${upload.ks3.domainMode}")
    private boolean domainMode;

    /**
     *true表示以   endpoint/{bucket}/{key}的方式访问
     *false表示以  {bucket}.endpoint/{key}的方式访问
     */
    @Value("${upload.ks3.pathstyle.access}")
    private boolean  pathStyleAccess;
    @Value("${upload.ks3.bruck_name}")
    private String  bucketName;//存储单元名称

    @Value("${upload.ks3.accessKeyid}")
    private String accessKeyid;
    @Value("${upload.ks3.accessKeySecret}")
    private String accessKeySecret;

    private Ks3 client;//对称存储操作实体

    /**
     * 初始化
     */
    @PostConstruct
    public void init()
    {
        Ks3ClientConfig config = new Ks3ClientConfig();
        config.setEndpoint(endpoint);
        config.setDomainMode(domainMode);
        config.setProtocol(Ks3ClientConfig.PROTOCOL.http);
        config.setPathStyleAccess(pathStyleAccess);

        HttpClientConfig hconfig = new HttpClientConfig();
        //在HttpClientConfig中可以设置httpclient的相关属性，比如代理，超时，重试等。
        config.setHttpClientConfig(hconfig);

        client = new Ks3Client(accessKeyid,accessKeySecret,config);
    }
    @Override
    String uploadFile(MultipartFile file, Ks3UploadConfig config) {
        if(config==null){
            config=new Ks3UploadConfig();
        }
        String localPreFix=config.getLocalPreFix();
        String savePath = (localPreFix.endsWith("/")?localPreFix.substring(0,localPreFix.length()-1):localPreFix) + config.getFileName();
        try {
            byte [] fileData = IOUtils.toByteArray(file.getInputStream());
           /* if(path.startsWith("/")) {
                path = path.substring(1,path.length());
            }
*/
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentLength(fileData.length);
            meta.setContentEncoding("UTF-8");
            if(StringUtils.hasText(config.getContentType()))
            {
                meta.setContentType(config.getContentType());
            }

            PutObjectRequest request = new PutObjectRequest(bucketName,
                    config.getFilePath(), new ByteArrayInputStream(fileData),meta);

            //上传文件的读写权限
            if(config.getPrivateAccess()){
                request.setCannedAcl(CannedAccessControlList.Private);
            }else{
                request.setCannedAcl(CannedAccessControlList.PublicRead);
            }

            client.putObject(request);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return savePath;
    }
}
