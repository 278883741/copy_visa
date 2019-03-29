package com.aoji.service.impl;

import com.aoji.service.KsyunKs3UtilService;
import com.ksyun.ks3.dto.CannedAccessControlList;
import com.ksyun.ks3.dto.ObjectMetadata;
import com.ksyun.ks3.http.HttpClientConfig;
import com.ksyun.ks3.service.Ks3;
import com.ksyun.ks3.service.Ks3Client;
import com.ksyun.ks3.service.Ks3ClientConfig;
import com.ksyun.ks3.service.request.PutObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;

@Service
public class KsyunKs3UtilServiceImpl implements KsyunKs3UtilService {

    private static Logger LOGGER = LoggerFactory.getLogger(KsyunKs3UtilServiceImpl.class);


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
    public String putObjectSimple(byte[] data, String path,boolean privateAccess) {
        if(path.startsWith("/")) {
            path = path.substring(1,path.length());
        }

        return  putObjectSimple(data,path,this.bucketName,null,true,privateAccess);
    }

    private String putObjectSimple(byte[] data, String path, String bucketName, String contentType, boolean usecdn,boolean privateAccess) {
        try
        {
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentLength(data.length);
            meta.setContentEncoding("UTF-8");
            if(StringUtils.hasText(contentType))
            {
                meta.setContentType(contentType);
            }

            PutObjectRequest request = new PutObjectRequest(bucketName,
                    path, new ByteArrayInputStream(data),meta);

            //上传文件的读写权限
            if(privateAccess){
                request.setCannedAcl(CannedAccessControlList.Private);
            }else{
                request.setCannedAcl(CannedAccessControlList.PublicRead);
            }

            client.putObject(request);

            if(usecdn)
            {
                return  getHttpCDNReqUrl(path);
            }
            else
            {
                return  getHttpReqUrl(path);
            }

        }
        catch (Exception e)
        {
            throw  new RuntimeException(e);
        }
    }

    @Override
    public String getHttpCDNReqUrl(String path) {
        return   Ks3ClientConfig.PROTOCOL.http.toString() + "://"+
                this.resDomain + (path.startsWith("/")?path:"/"+path);
    }

    @Override
    public String getHttpReqUrl(String path) {
        return   Ks3ClientConfig.PROTOCOL.http.toString() + "://"+
                this.endpoint + "/" + bucketName + (path.startsWith("/")?path:"/"+path);
    }

    /**
     * 生成私密访问地址
     * @param bucket 比如：http://test-bucket.kss.ksyun.com/2015/10/19/image.jpg
     *               该URL中的{bucket}是test-bucket,{endpoint}是kssws.ks-cdn.com,{key}是2015/10/19/image.jpg
     * @param key
     * @return
     */
    @Override
    public  String generatePrivateUrl(String bucket,String key){
        /*ResponseHeaderOverrides overrides = new ResponseHeaderOverrides();
        System.out.println("第二种生成的url："+client.generatePresignedUrl(bucket,key,1000,overrides));*/

        //生成一个在86400秒后过期的外链
        String  url=client.generatePresignedUrl(this.bucketName,key,86400);
        return url;
    }
}
