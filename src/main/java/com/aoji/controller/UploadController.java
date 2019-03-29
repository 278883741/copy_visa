package com.aoji.controller;

import com.alibaba.fastjson.JSONObject;
import com.aoji.service.KsyunKs3UtilService;
import com.aoji.utils.ueditor.ActionEnter;
import com.aoji.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author yangsaixing
 * @description
 * @date Created in 下午3:58 2017/11/2
 */
@Controller
public class UploadController {

    @Autowired
    FileService fileService;

    @Autowired
    KsyunKs3UtilService ksyunKs3UtilService;


    @Value("${upload.root_path}")
    private String root_path;


    @Value("${upload.ks3.resDomain}")
    private String resDomain;
    @Value("${upload.imagepath.format}")
    private String imagepathFormat;
    @Value("${upload.filepath.format}")
    private String filepathFormat;
    @Value("${upload.ks3.bruck_name}")
    private String bruckName;

    @Value("${upload.status}")
    private Boolean uploadStatus;

    @Value("${upload.secret.access}")
    private Boolean uploadSecretAccess;
    @RequestMapping("/marker")
    public String marker(Model model) {
        model.addAttribute("title", "示例");
        return "uploadPage";
    }

    /**
     * 单上传文件
     *
     * @param file 文件流
     * @return
     */
    @RequestMapping("/upload")
    public ResponseEntity<JSONObject> upload(@RequestParam("fileInput") MultipartFile file,@RequestParam(name = "privateAccess",required = false) boolean privateAccess) {
        JSONObject jsonObject = new JSONObject();
        List<String> PIC_Format = Arrays.asList(".jpg", ".JPG", ".png", ".PNG", ".gif", ".jpeg", ".JPEG",".doc",".docx",".pdf",".eml");
        if (file.isEmpty()) {
            throw new RuntimeException("上传图片不能为空");
        }
        //上传到金山云
        if (uploadStatus) {
            try {
                // 获取文件扩展名
                String extend =  file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                if (!PIC_Format.contains(extend)) {
                    jsonObject.put("success", false);
                    jsonObject.put("obj","上传文件格式有误");
                    jsonObject.put("code","1");
                    return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.OK);
                }
                String path = fileService.yunUpload(file, "", "", "product/", imagepathFormat,uploadSecretAccess);
                getUploadPath(path, jsonObject,file.getOriginalFilename());
            } catch (Exception e) {
                jsonObject.put("success", false);
                e.printStackTrace();
            }
        }
        return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.OK);
    }

    /**
     * 富文本编辑器-上传图片
     * @param request
     * @param response
     */
    @RequestMapping("/project/ueditor")
    public void load(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Type", "text/html");
            String rootPath = request.getSession().getServletContext().getRealPath("/");
            String exec = new ActionEnter(request, rootPath).exec();
            PrintWriter writer = response.getWriter();
            writer.write(exec);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JSONObject getUploadPath(String path, JSONObject jsonObject,String originalFilename) {
        if (!StringUtils.hasText(path)) {
            jsonObject.put("success", false);
        } else {
            jsonObject.put("success", true);
            if (uploadStatus) {
                jsonObject.put("obj", path);
                jsonObject.put("originalFilename", originalFilename);
            } else {
                jsonObject.put("obj", path);
                jsonObject.put("originalFilename", originalFilename);
            }

        }
        return jsonObject;
    }

    /**
     * 下载
     * @param response
     * @param request
     */
    @RequestMapping("/download")
    public void download(HttpServletResponse response, HttpServletRequest request) {
        try {
            String filePath = request.getParameter("filePath");
            String fileName = request.getParameter("fileName");
            String downloadFilename;
            if (StringUtils.isEmpty(fileName)) {
                // 在下载框默认显示的文件名
                downloadFilename = filePath.substring(filePath.lastIndexOf("/") + 1);
                downloadFilename = URLEncoder.encode(downloadFilename, "UTF-8");
                String path = "D:\\";
                File file = new File(path + "output" + File.separator + downloadFilename);
                if (file.exists()) {
                    file.mkdirs();
                }
            } else {
                // 对默认下载的文件名编码。不编码的结果就是，在客户端下载时文件名乱码
                downloadFilename = URLEncoder.encode(fileName, "UTF-8");
            }

            HttpURLConnection conn = null;
            InputStream inputStream = null;
            ServletOutputStream fileOut = null;

            URL httpUrl = new URL(filePath);
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
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            fileOut = response.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(fileOut);

            byte[] buf = new byte[1024 * 1024 * 10];
            int length = bis.read(buf);

            // 读出文件到response
            // 这里是先需要把要把文件内容先读到缓冲区
            // 再把缓冲区的内容写到response的输出流供用户下载
            response.setHeader("Content-Disposition", "attachment;filename="+ downloadFilename);
            // 指明response的返回对象是文件流
            response.setContentType("application/octet-stream");
            while (length != -1) {
                bos.write(buf, 0, length);
                length = bis.read(buf);
            }
            bos.close();
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

        }
    }


    /**
     * 上传多个文件
     *
     * @param files 文件流
     * @return
     */
    @RequestMapping("/uploadFiles")
    public ResponseEntity<JSONObject> uploadFiles(@RequestParam("fileInput") MultipartFile[] files,@RequestParam(name = "privateAccess",required = false) boolean privateAccess) {
        JSONObject jsonObject = new JSONObject();
        List<String> FILE_PATH = new ArrayList<>();
        List<String> originalFiles = new ArrayList<>();
        List<String> PIC_Format = Arrays.asList(".jpg", ".JPG", ".png", ".PNG", ".gif", ".jpeg", ".JPEG",".doc",".docx",".pdf",".eml");
        if (files.length == 0) {
            throw new RuntimeException("上传图片不能为空");
        }
        //上传到金山云
        if (uploadStatus) {
            try {
                for(MultipartFile item:files){
                    // 获取文件扩展名
                    String extend =  item.getOriginalFilename().substring(item.getOriginalFilename().lastIndexOf("."));
                    if (!PIC_Format.contains(extend)) {
                        jsonObject.put("success", false);
                        jsonObject.put("obj","上传文件格式有误");
                        jsonObject.put("code","1");
                        return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.OK);
                    }
                    String path = fileService.yunUpload(item, "", "", "product/", imagepathFormat,privateAccess);
                    getUploadPath(path, jsonObject,item.getOriginalFilename());
                    FILE_PATH.add(path);
                    originalFiles.add(item.getOriginalFilename());
                }
                jsonObject.put("objNew",FILE_PATH);
                jsonObject.put("originalFiles",originalFiles);

            } catch (Exception e) {
                jsonObject.put("success", false);
                e.printStackTrace();
            }
        }

        return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.OK);
    }

    /**
     * 生成文件私密访问地址
     * @param fileUrl
     * @return
     */
    @RequestMapping(value = "/getPrivateUrl",method = RequestMethod.POST)
    @ResponseBody
    public String getPrivateUrl(@RequestParam String fileUrl){
        return fileService.getPrivateUrl(fileUrl);
    }
}
