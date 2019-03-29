package com.aoji.utils;

import com.ksyun.ks3.dto.*;
import com.ksyun.ks3.exception.serviceside.NotFoundException;
import com.ksyun.ks3.http.HttpClientConfig;
import com.ksyun.ks3.service.Ks3;
import com.ksyun.ks3.service.Ks3Client;
import com.ksyun.ks3.service.Ks3ClientConfig;
import com.ksyun.ks3.service.request.HeadObjectRequest;
import com.ksyun.ks3.service.request.PutObjectRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 金山云存储使用工具包
 * Created by xiaomengyun on 2017/7/24.
 */
public class KsyunKs3Util
{
    private static Logger LOGGER = LoggerFactory.getLogger(KsyunKs3Util.class);

    //如果使用自定义域名，设置endpoint为自定义域名，同时设置domainMode为true
    private static final String resDomain = "upload-cdn.globeedu.com";
    private static final String  endpoint = "ks3-cn-beijing.ksyun.com";
    private static final boolean domainMode = false;

    /**
     *true表示以   endpoint/{bucket}/{key}的方式访问
     *false表示以  {bucket}.endpoint/{key}的方式访问
     */
    private static final boolean  pathStyleAccess = true;
    private static final String  bucketName = "aojicrp";//存储单元名称

    private static final String accessKeyid = "AKLT4O_q0wzkSR6qRlJaH8a_5w";
    private static final String accessKeySecret = "OH+fjD1uwiRjwkY9Ff4GDpeMgyqHBi9c7qh3TFzyOVcBoNNvTZxRfBTG99/dc16Hew==";

    private static Ks3 client;//对称存储操作实体

    private KsyunKs3Util()
    {

    }

    private static  class InstanceHolder
    {
        private static  KsyunKs3Util instance = new KsyunKs3Util();
    }

    public static  KsyunKs3Util getInstance()
    {
        return  InstanceHolder.instance;
    }


    /**
     * 初始化
     */
    static
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

    /**
     * 生成私密访问地址
     * @param bucket 比如：http://test-bucket.kss.ksyun.com/2015/10/19/image.jpg
     *               该URL中的{bucket}是test-bucket,{endpoint}是kssws.ks-cdn.com,{key}是2015/10/19/image.jpg
     * @param key
     * @return
     */
    public static String generatePrivateUrl(String bucket,String key){

        /*ResponseHeaderOverrides overrides = new ResponseHeaderOverrides();
        System.out.println("第二种生成的url："+client.generatePresignedUrl(bucket,key,1000,overrides));*/

        //生成一个在1000秒后过期的外链
        String  url=client.generatePresignedUrl(bucket,key,1000);
        return url;

    }

    /**
     * 销毁
     */
    public void destory()
    {
        client = null;
    }

    /**
     * 上传对象
     * @param data  上传文件流
     * @param path  文件存放路径
     * @param bucketName  云上的存储单元
     */
    public static String putObjectSimple(byte[] data,String path,String bucketName,String contentType,boolean usecdn)
    {
        try
        {
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentLength(data.length);
            meta.setContentEncoding("UTF-8");
            if(StringUtils.isNotBlank(contentType))
            {
                meta.setContentType(contentType);
            }

            PutObjectRequest request = new PutObjectRequest(bucketName,
                    path, new ByteArrayInputStream(data),meta);

            //上传一个公开文件
            request.setCannedAcl(CannedAccessControlList.PublicRead);

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

    /**
     * 上传对象
     * @param data 上传文件流
     * @param path 文件存放路径
     */
    public static String putObjectSimple(byte[] data,String path)
    {
        if(path.startsWith("/"))
        {
            path = path.substring(1,path.length());
        }

        return  putObjectSimple(data,path, bucketName,null,true);
    }

    /**
     * 上传对象
     * @param data 上传文件流
     * @param path 文件存放路径
     */
    public static String putObjectSimple(byte[] data,String path,String contentType,boolean usecdn)
    {
        if(path.startsWith("/"))
        {
            path = path.substring(1,path.length());
        }

        return  putObjectSimple(data,path, bucketName,contentType,usecdn);
    }


    /**
     * 判断一个object是否存在
     */
    public static boolean objectExists(String filename)
    {
        return  objectExists(filename,bucketName);
    }


    /**
     * 判断一个object是否存在
     */
    public static boolean objectExists(String filename,String bucketName)
    {
        try
        {
            HeadObjectRequest request = new HeadObjectRequest(bucketName,
                    filename);
            client.headObject(request);
            return true;
        } catch (NotFoundException e) {
            return false;
        }
    }

    /**
     * 罗列出对象列表
     * @param prepath
     * @return
     */
    public ObjectListing listObjectsWithPrefix(String prepath)
    {
        return listObjectsWithPrefix(this.bucketName,prepath);
    }

    /**
     * 罗列出对象列表
     * @param prepath
     * @return
     */
    public ObjectListing listObjectsWithPrefix(String bucketName,String prepath)
    {
        ObjectListing list = client.listObjects(bucketName,prepath);
        return list;
    }


    /**
     * 将<bucket名称>这个存储空间下的<object key>删除
     */
    public void deleteObject(String bucketName,String httprequrl)
    {
        client.deleteObject(bucketName,getFileKey(httprequrl));
    }

    /**
     * 将<bucket名称>这个存储空间下的<object key>删除
     */
    public void deleteObject(String httprequrl)
    {
        client.deleteObject(this.bucketName,getFileKey(httprequrl));
    }


    /**
     * 批量删除
     * @return
     */
    public boolean deleteObjects(String [] httpReqUrl,String bucketName)
    {
        String [] fileKeys = new String[httpReqUrl.length];
        for(int i = 0;i < httpReqUrl.length;i++)
        {
            fileKeys[i] = getFileKey(httpReqUrl[i]);
        }

        DeleteMultipleObjectsResult result = client.deleteObjects(fileKeys,bucketName);

        List<String> list =  new CopyOnWriteArrayList<>(result.getDeleted());
        for(String filekey : fileKeys)
        {
            list.remove(filekey);
        }
        return list.size() == 0;
    }


    /**
     * 删除
     * @return
     */
    public boolean deleteObjects(String [] httpReqUrl)
    {
        return deleteObjects(httpReqUrl,this.bucketName);
    }


    /**
     * 获取http请求地址
     * @param path
     * @return
     */
    private static String getHttpCDNReqUrl(String path)
    {
        return   Ks3ClientConfig.PROTOCOL.http.toString() + "://"+
                resDomain + (path.startsWith("/")?path:"/"+path);
    }

    /**
     * 获取HTTP请求地址
     * @param path
     * @return
     */
    private static String getHttpReqUrl(String path)
    {
        return   Ks3ClientConfig.PROTOCOL.http.toString() + "://"+
                endpoint + "/" + bucketName + (path.startsWith("/")?path:"/"+path);
    }



    /**
     *  获取请求的key
     * @param
     * @return
     */
    private static String getFileKey(String httpreqUrl)
    {
        if(StringUtils.isBlank(httpreqUrl))
        {
            return  httpreqUrl;
        }

        httpreqUrl = httpreqUrl.replace("http://","");
        httpreqUrl = httpreqUrl.replace(endpoint + "/" + bucketName,"");
        httpreqUrl = httpreqUrl.startsWith("/")?httpreqUrl.substring(1,httpreqUrl.length()):httpreqUrl;
        return  httpreqUrl;
    }

}
