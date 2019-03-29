package com.aoji.controller;

import com.aoji.contants.Contants;
import com.aoji.model.BasePageModel;
import com.aoji.model.NewsInfo;
import com.aoji.model.PageParam;
import com.aoji.service.NewsService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author yangsaixing
 * @description 公告控制器
 * @date Created in 下午3:25 2018/1/25
 */
@Controller
public class NewsInfoController extends BaseController{
    @Autowired
    NewsService newsService;


    @RequestMapping("/news")
    public String list(){
        return "news/list";
    }

    /**
     * 跳转到编辑页
     * @return
     */
    @RequestMapping("/news/editPage")
    public String editPage(Integer id,Model model){
        NewsInfo newsInfo=newsService.queryById(id);
        if(newsInfo==null){
            newsInfo=new NewsInfo();
        }
        model.addAttribute("news",newsInfo);
        return "news/edit";
    }

    /**
     * 跳转到详情页
     * @return
     */
    @RequestMapping("/news/detailPage")
    public String detailPage(Integer id,Model model){
        NewsInfo newsInfo=newsService.queryById(id);
        if(newsInfo==null){
            newsInfo=new NewsInfo();
        }
        model.addAttribute("news",newsInfo);
        return "news/detail";
    }

    /**
     * 分页查询公告列表
     * @param newsInfo
     * @return
     */
    @RequestMapping("/news/page")
    @ResponseBody
    public BasePageModel queryByPage(NewsInfo newsInfo, PageParam pageParam, BasePageModel basePageModel){
        Page<?> page = pageWapper(pageParam,propList());
        newsInfo.setDeleteStatus(false);
        List<NewsInfo> newsInfos=newsService.queryByPage(newsInfo);
        for (NewsInfo news:newsInfos){
            if(!StringUtils.isEmpty(news.getUpdateTime())){
                news.setUpdateTimeString(new SimpleDateFormat(Contants.datePattern).format(news.getUpdateTime()));
            }
            if(!StringUtils.isEmpty(news.getStartTime())){
                news.setStartTimeString(new SimpleDateFormat(Contants.datePattern).format(news.getStartTime()));
            }
            if(!StringUtils.isEmpty(news.getEndTime())){
                news.setEndTimeString(new SimpleDateFormat(Contants.datePattern).format(news.getEndTime()));
            }
        }
        return dataTableWapper(page,basePageModel);
    }

    @RequestMapping(value = "/news/save",method = RequestMethod.POST)
    @ResponseBody
    public Boolean detail(NewsInfo newsInfo) {
        Boolean saveResult=false;
        if(newsInfo==null ){
            return  saveResult;
        }
        newsInfo.setUpdateTime(new Date());
        newsInfo.setOperatorNo("admin");
        if(!StringUtils.isEmpty(newsInfo.getId())){
           saveResult= newsService.updateNews(newsInfo)==1;
        }else{
            newsInfo.setCreateTime(new Date());
            newsInfo.setDeleteStatus(false);
            saveResult=newsService.addNews(newsInfo)==1;
        }
        return saveResult;
    }
    public String[] propList(){
        return new String[]{"id","enableStatus","content","startTimeString","endTimeString","operatorNo","updateTimeString"};
    }
}
