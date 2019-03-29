package com.aoji.utils.ueditor;

import com.aoji.contants.ImgTools;
import com.aoji.utils.KsyunKs3Util;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.State;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class StorageManager {
    public static final int BUFFER_SIZE = 8192;

    @Value("${picture.compress.width}")
    private String pictureWidth;

    @Value("${picture.compress.heigth}")
    private String pictureHeigth;

    public static State saveBinaryFile(byte[] data, String path) {
        File file = new File(path);

        State state = valid(file);
        if (!state.isSuccess()) {
            return state;
        }
        try {
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(file));
            bos.write(data);
            bos.flush();
            bos.close();
        } catch (IOException ioe) {
            return new BaseState(false, 4);
        }
        state = new BaseState(true, file.getAbsolutePath());
        state.putInfo("size", data.length);
        state.putInfo("title", file.getName());
        return state;
    }

    public static State saveFileByInputStream(MultipartFile multipartFile, String path, long maxSize) {
        try {
            InputStream is = multipartFile.getInputStream();

            State state = null;

            File tmpFile = getTmpFile();

            byte[] dataBuf = new byte['?'];
            BufferedInputStream bis = new BufferedInputStream(is, 8192);

            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(tmpFile), 8192);

            int count = 0;
            while ((count = bis.read(dataBuf)) != -1) {
                bos.write(dataBuf, 0, count);
            }
            bos.flush();
            bos.close();
            if (tmpFile.length() > maxSize) {
                tmpFile.delete();
                return new BaseState(false, 1);
            }
            state = saveTmpFile(tmpFile, path, multipartFile);
            if (!state.isSuccess()) {
                tmpFile.delete();
            }
            return state;
        } catch (IOException localIOException) {
        }
        return new BaseState(false, 4);
    }

    public static State saveFileByInputStream(InputStream is, String path, MultipartFile multipartFile) {
        State state = null;

        File tmpFile = getTmpFile();

        byte[] dataBuf = new byte['?'];
        BufferedInputStream bis = new BufferedInputStream(is, 8192);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(tmpFile), 8192);

            int count = 0;
            while ((count = bis.read(dataBuf)) != -1) {
                bos.write(dataBuf, 0, count);
            }
            bos.flush();
            bos.close();

            state = saveTmpFile(tmpFile, path, multipartFile);
            if (!state.isSuccess()) {
                tmpFile.delete();
            }
            return state;
        } catch (IOException localIOException) {
        }
        return new BaseState(false, 4);
    }

    private static File getTmpFile() {
        File tmpDir = FileUtils.getTempDirectory();
        String tmpFileName = Double.valueOf(Math.random() * 10000.0D).toString().replace(".", "");
        return new File(tmpDir, tmpFileName);
    }

    private static State saveTmpFile(File tmpFile, String path, MultipartFile multipartFile) {
        State state = null;
        File targetFile = new File(path);
        if (targetFile.canWrite()) {
            return new BaseState(false, 2);
        }
        String remotePath = "";
        try {
            File savefile = new File(path);
            /*对图片进行压缩
            byte[] fileData = ImgTools.compressUnderSize(IOUtils.toByteArray(multipartFile.getInputStream()), Long.valueOf(150) * Long.valueOf(150));*/
             byte [] fileData = multipartFile.getBytes();
            remotePath = KsyunKs3Util.putObjectSimple(fileData, path);
//            FileUtils.moveFile(tmpFile, targetFile);
        } catch (Exception e) {
            return new BaseState(false, 4);
        }
        state = new BaseState(true);
        state.putInfo("size", targetFile.length());
        state.putInfo("title", targetFile.getName());
//        state.putInfo("url", PathFormat.format(remotePath));

        return state;
    }

    private static State valid(File file) {
        File parentPath = file.getParentFile();
        if ((!parentPath.exists()) && (!parentPath.mkdirs())) {
            return new BaseState(false, 3);
        }
        if (!parentPath.canWrite()) {
            return new BaseState(false, 2);
        }
        return new BaseState(true);
    }
}

