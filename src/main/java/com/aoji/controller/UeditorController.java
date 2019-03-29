package com.aoji.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;


/**
 * @description:百度UEDITOR 上传控制辅助类
 * @author:liubaocheng
 * @Date: 2017-12-20 下午1:42
 **/
@Controller
@RequestMapping("ueditor")
public class UeditorController {

    private final static Logger logger = LoggerFactory.getLogger(UeditorController.class);


    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public ResponseEntity<Object> delete(String key) {
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("success", "success");
        json.put("key", key);
        return new ResponseEntity<Object>(json, HttpStatus.OK);
    }
}
