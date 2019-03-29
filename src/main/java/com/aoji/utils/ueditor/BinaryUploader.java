package com.aoji.utils.ueditor;

/**
 * @author yangsaixing
 * @description
 * @date Created in 下午2:03 2017/12/5
 */
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.FileType;
import com.baidu.ueditor.define.State;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class BinaryUploader {
    public BinaryUploader() {
    }

    public static final State save(HttpServletRequest request, Map<String, Object> conf) {
        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
            MultipartFile multipartFile = multipartRequest.getFile(conf.get("fieldName").toString());
            String savePath = (String)conf.get("savePath");
            String originFileName = multipartFile.getOriginalFilename();
            String suffix = FileType.getSuffixByFilename(originFileName);
            originFileName = originFileName.substring(0, originFileName.length() - suffix.length());
            savePath = savePath + suffix;
            long maxSize = ((Long)conf.get("maxSize")).longValue();
            if (!validType(suffix, (String[])conf.get("allowFiles"))) {
                return new BaseState(false, 8);
            } else {
                savePath = PathFormat.parse(savePath, originFileName);
                String[] savePathBySplit_temp = savePath.split("/");
                String temp = "/";
                String fileName = savePathBySplit_temp[savePathBySplit_temp.length - 1];

                for(int i = 1; i < savePathBySplit_temp.length - 1; ++i) {
                    if (i != savePathBySplit_temp.length - 2) {
                        temp = temp + savePathBySplit_temp[i] + "/";
                    } else {
                        temp = temp + savePathBySplit_temp[i];
                    }
                }

                System.out.println("temp:"+temp);
                String pathTemp = request.getSession().getServletContext().getRealPath(temp);
                System.out.println("pathTemp："+pathTemp);
                File targetFile = new File(temp);
                if (!targetFile.exists()) {
                    targetFile.mkdirs();
                }
                System.out.println("totalPath:"+temp+"/"+fileName);
                State storageState = StorageManager.saveFileByInputStream(multipartFile, temp + "/" + fileName, maxSize);
                System.out.println("storageState:"+storageState);
                if (storageState.isSuccess()) {
                    storageState.putInfo("url", PathFormat.format(savePath));
                    storageState.putInfo("type", suffix);
                    storageState.putInfo("original", originFileName + suffix);
                }

                return storageState;
            }
        } catch (Exception var15) {
            var15.printStackTrace();
            System.out.println(var15.getMessage());
            return new BaseState(false, 4);
        }
    }

    private static boolean validType(String type, String[] allowTypes) {
        List<String> list = Arrays.asList(allowTypes);
        return list.contains(type);
    }
}

