package com.aoji.controller;

import com.alibaba.fastjson.JSONObject;
import com.aoji.contants.ResponseMessage;
import com.aoji.model.StudentInfo;
import com.aoji.service.StudentService;
import com.aoji.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author yangsaixing
 * @description
 * @date Created in 上午10:25 2017/11/17
 */
@RestController
public class RestTestController {
    @Autowired
    StudentService studentService;


    RestTemplate restTemplate=new RestTemplate();

    @RequestMapping(value="/student/list",method = RequestMethod.GET)
    public  ResponseEntity<ResponseMessage> query(ResponseMessage responseMessage){
        responseMessage.getBody().put("students",studentService.getList(new StudentInfo(),""));
        return  new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
    }

    @RequestMapping(value = "/remote/get")
    public String getRemote(){
        String url="http://localhost:8082/student/list";
        return  restTemplate.getForObject(url,String.class);
    }

    @RequestMapping("/remote/post")
    public String postRemote(){
        String url="http://localhost:8082/student/remote";
        HttpHeaders httpHeaders=new HttpHeaders();
        MediaType mediaType= MediaType.parseMediaType("application/json;charset=UTF-8");
        httpHeaders.setContentType(mediaType);
        httpHeaders.add("Accept",MediaType.APPLICATION_JSON.toString());


        JSONObject jsonParam =new  JSONObject();
        jsonParam.put("id",1);
        HttpEntity<String> httpEntity=new HttpEntity(jsonParam);
        String jsonResult=restTemplate.postForObject(url,httpEntity,String.class);
        System.out.println(jsonResult);
        JSONObject jsonObject=JSONObject.parseObject(jsonResult);
        String name="";
        if(StringUtils.hasText(jsonObject.get("name").toString())){
            name= jsonObject.get("name").toString();
        }
        return name;
    }


    @RequestMapping(value="/student/remote",method = RequestMethod.POST)
    public List<StudentInfo> query(String id){
        StudentInfo studentInfo=new StudentInfo();
        studentInfo.setId(Integer.valueOf(id));
        return studentService.getList(studentInfo,"");
    }

    public static void main(String args[]){
        Pattern pattern = Pattern.compile("^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$");
        Matcher m = pattern.matcher("19836668566");

        System.out.println(m.matches()+"---");
    }
}
