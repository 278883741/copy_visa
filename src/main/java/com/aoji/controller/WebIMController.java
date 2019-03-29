package com.aoji.controller;

import com.aoji.service.UserService;
import com.aoji.service.WebIMService;
import com.aoji.vo.IMGroupHistoryVO;
import com.aoji.vo.IMStudentSearchVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * author: chenhaibo
 * description: WebIM控制类
 * date: 2018/10/26
 */
@Controller
@RequestMapping("/webim")
public class WebIMController {

    @Autowired
    UserService userService;
    @Autowired
    WebIMService webIMService;

    /**
     * IM首页
     * @return
     */
    @RequestMapping("/index")
    public String WebImIndex(){
        return "/webim/index";
    }

    /**
     * IM首页
     * @return
     */
    @RequestMapping("/login")
    public String imIndex(Model model, String memberId){
        model.addAttribute("loginInfo", webIMService.getLoginInfo(memberId));
        model.addAttribute("studentSearchData", webIMService.studentSearch(memberId));
        return "/im/index";
    }

    /**
     * 学生搜索数据
     * @param telOrName
     * @return
     */
    @RequestMapping(value = "/student/search")
    @ResponseBody
    public List<IMStudentSearchVO> studentSearch(String telOrName, String memberId){
        return webIMService.studentSearch(memberId);
    }

    /**
     * 查询群组ID
     * @param groupId 群组ID
     * @param msgTimeStamp 最近一条消息的时间
     * @return
     */
    @RequestMapping(value = "/group/history")
    @ResponseBody
    public List<IMGroupHistoryVO> getGroupHistoryRecord(String groupId, String msgTimeStamp){
        return webIMService.getGroupHistory(groupId, msgTimeStamp);
    }
}
