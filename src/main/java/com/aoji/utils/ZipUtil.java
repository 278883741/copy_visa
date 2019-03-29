package com.aoji.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil {
    /*
     * zip下载
     * @param out
     * @param dir
     * @param fileUrl
     * @throws IOException
     */
    public static void doZip(ZipOutputStream out, String dir, String fileUrl, HashSet<String> names) throws IOException {

        //下载的单个文件的名称
        String entryName = null;
        if (!"".equals(dir)) {
            entryName = dir;
        }

        ZipEntry entry = new ZipEntry(entryName);
        if (! names.add(entryName)) {
            return;
        }
        out.putNextEntry(entry);
        int len = 0 ;
        byte[] buffer = new byte[1024];
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        URL httpUrl = new URL(fileUrl);
        conn = (HttpURLConnection) httpUrl.openConnection();
        //以Post方式提交表单，默认get方式
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        // post方式不能使用缓存
        conn.setUseCaches(false);
        //连接指定的资源
        conn.connect();
        //获取网络输入流
        inputStream = conn.getInputStream();
        BufferedInputStream fis = new BufferedInputStream(inputStream);
        while ((len = fis.read(buffer)) > 0) {
            out.write(buffer, 0, len);
            out.flush();
        }
        out.closeEntry();
        inputStream.close();
        fis.close();
    }

}
