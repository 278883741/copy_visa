package com.aoji.controller;

import com.aoji.model.StudentInfo;
import com.aoji.service.StudentService;
import com.aoji.service.impl.ExcelServiceImpl;
import com.aoji.utils.ExcelUtil;
import com.aoji.utils.SysUserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yangsaixing
 * @description
 * @date Created in 下午1:30 2018/11/5
 */
@Controller
public class ExportTestController {

        @Autowired
        StudentService studentService;


        @RequestMapping("/test/new/export")
        public void export(HttpServletResponse response, StudentInfo studentInfo) throws Exception {
            List<Map> data=studentService.getUserMap(studentInfo);
            try{
                ExcelUtil util=new ExcelUtil();
                util.export(response,data,SysUserModel.class);
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        @RequestMapping("/test/old/export")
        public void export1(HttpServletResponse response,StudentInfo studentInfo) throws Exception {
            String[] rowsName = new String[]{
                    "学生姓名","拼音","公司号","代理推荐","出生日期","学生姓名","拼音","公司号","代理推荐","出生日期","学生姓名","拼音","公司号","代理推荐","出生日期"};
            List<Map> data=studentService.getUserMap(studentInfo);
            System.out.println("开始进行list换map");
            List<Object[]> objsList=new ArrayList<>();
            Object[] objs = null;
            for (Map map:data){
                objs = new Object[rowsName.length];
                objs[0] = map.get("studentNo");
                objs[1]=map.get("studentName");
                objs[2] = map.get("confeeid");
                objs[3]=map.get("contractNo");
                objs[4] = map.get("contractType");
                objs[5]=map.get("branchId");
                objs[6] = map.get("branchName");
                objs[7]=map.get("salesConsultant");
                objs[8] = map.get("salesConsultantNo");
                objs[9]=map.get("createTime");
                objs[10] = map.get("channelStatus");
                objs[11]=map.get("remark");
                objs[12] = map.get("channelTransferStatus");

                objsList.add(objs);
            }
            System.out.println("完成list换map");
            ExcelServiceImpl ex = new ExcelServiceImpl("djlk", rowsName, objsList,response);
            ex.export();

        }

        @RequestMapping("/test/query")
        public void query1(HttpServletResponse response,StudentInfo studentInfo) throws Exception {

            List<Map> data=studentService.getUserMap(studentInfo);
        }
}
