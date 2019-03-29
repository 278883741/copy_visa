package com.aoji.service;

public interface KsyunKs3UtilService {


    /**
     * 上传对象
     * @param data
     * @param path
     * @return
     */
     String putObjectSimple(byte[] data,String path,boolean privateAccess);


     String getHttpCDNReqUrl(String path);

     String getHttpReqUrl(String path);

    /**
     * 生成私密访问地址
     * @param bucket 比如：http://test-bucket.kss.ksyun.com/2015/10/19/image.jpg
     *               该URL中的{bucket}是test-bucket,{endpoint}是kssws.ks-cdn.com,{key}是2015/10/19/image.jpg
     * @param key
     * @return
     */
     String generatePrivateUrl(String bucket,String key);
}
