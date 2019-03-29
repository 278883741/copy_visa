package com.aoji.controller;

import com.aoji.model.BasePageModel;
import com.aoji.model.PageParam;
import com.aoji.model.StudentCopyInfo;
import com.aoji.service.CopyInfoService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * author: chenhaibo
 * description: 文书信息管理控制类
 * date: 2018/1/22
 */
@Controller
public class CopyInfoController extends BaseController{

    @Autowired
    CopyInfoService copyInfoService;

    @Value("${upload.ks3.resDomain}")
    private String resDomain;

    /**
     * 跳转列表页
     * @return
     */
    @RequestMapping("/copyInfo")
    public String list(){
        return "copyInfo/list";
    }

    /**
     * 文书列表数据
     * @param studentCopyInfo
     * @param pageParam
     * @param basePageModel
     * @return
     */
    @RequestMapping("/copyInfo/list/data")
    @ResponseBody
    public BasePageModel listData(StudentCopyInfo studentCopyInfo, PageParam pageParam, BasePageModel basePageModel){
        Page<?> page = pageWapper(pageParam,propList());
        //文书信息
        if(!StringUtils.hasText(studentCopyInfo.getStudentNo())){
            studentCopyInfo.setStudentNo(null);
        }

        copyInfoService.getListByExample(studentCopyInfo);

        return dataTableWapper(page,basePageModel);
    }

    /**
     * 排序列表
     * @return
     */
    public String[] propList(){
        return new String[]{};
    }
}
