package com.aoji.utils.ueditor;

/**
 * @author yangsaixing
 * @description
 * @date Created in 下午2:00 2017/12/5
 */
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.baidu.ueditor.define.State;
import com.baidu.ueditor.upload.Base64Uploader;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class Uploader {
    private HttpServletRequest request = null;
    private Map<String, Object> conf = null;

    public Uploader(HttpServletRequest request, Map<String, Object> conf) {
        this.request = request;
        this.conf = conf;
    }

    public final State doExec() {
        String filedName = (String)this.conf.get("fieldName");
        State state = null;
        if ("true".equals(this.conf.get("isBase64"))) {
            System.out.println("进入Base64Uploader");
            state = Base64Uploader.save(this.request.getParameter(filedName), this.conf);
        } else {
            System.out.println("进入BinaryUploader");
            state = BinaryUploader.save(this.request, this.conf);
        }

        return state;
    }
}
