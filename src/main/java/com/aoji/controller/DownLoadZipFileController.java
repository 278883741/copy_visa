package com.aoji.controller;

import com.aoji.utils.ZipUtil;
import com.aoji.vo.materialListVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipOutputStream;

@Controller
public class DownLoadZipFileController {
    private Logger logger = LoggerFactory.getLogger(DownLoadZipFileController.class);
    /**
     * 打包压缩下载文件
     */
    @RequestMapping(value = "/download/zip",method = RequestMethod.GET)
    public void downLoadZipFile (HttpServletResponse response,HttpServletRequest request,String zipName) throws IOException {
        //用于判断打包下载中，文件名称是否重复，如果重复，则只下载重复文件中的第一个
        HashSet<String> names = new HashSet<>();

        List<materialListVo> pcPapersFileInfo =(List<materialListVo>)request.getSession().getAttribute("pcPapersFileList");
        logger.info("材料清单查询数据 pcPapersFileInfo"+pcPapersFileInfo.toString());
        if(pcPapersFileInfo.size()<1 || pcPapersFileInfo==null){
            return;
        }
        zipName = URLEncoder.encode(zipName, "UTF-8");
        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition", "attachment;filename="+ zipName);
        ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
        try {
            for(Iterator<materialListVo> it = pcPapersFileInfo.iterator();it.hasNext();){
                materialListVo file = it.next();
                ZipUtil.doZip(out, file.getFileName(),file.getUrl(),names);
                response.flushBuffer();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
        }

    }
}


