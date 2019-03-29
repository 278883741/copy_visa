package com.aoji.controller;

import com.aoji.model.BasePageModel;
import com.aoji.model.PageParam;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * @author yangsaixing
 * @description 控制器基类
 * @date Created in 下午6:33 2017/11/29
 */
public class BaseController {

    /**
     * 页面查询包赚
     *
     * @param pageParam 分页参数
     * @param sortStr   要排序的数组
     */
    public Page<?> pageWapper(PageParam pageParam, String[] sortStr) {
        Page<?> page = PageHelper.startPage(pageParam.getiDisplayStart() / pageParam.getiDisplayLength() + 1, pageParam.getiDisplayLength());
        return page;
    }


    /**
     * 页面查询包赚
     *
     * @param pageParam 分页参数
     * @param sortStr   要排序的数组
     */
    public Page<?> pageWapper2(PageParam pageParam, String[] sortStr) {
        Page<?> page = PageHelper.startPage(pageParam.getiDisplayStart() / pageParam.getiDisplayLength() + 1, pageParam.getiDisplayLength());
        pageParam.setSortPro(sortStr[pageParam.getiSortCol_0()]);
        return page;
    }

    /**
     * 页面查询   不自动 count
     *
     * @param pageParam 分页参数
     * @param sortStr   要排序的数组
     */
    public Page<?> pageWapper3(PageParam pageParam, String[] sortStr) {
        Page<?> page = PageHelper.startPage(pageParam.getiDisplayStart() / pageParam.getiDisplayLength() + 1, pageParam.getiDisplayLength(), false);
        pageParam.setSortPro(sortStr[pageParam.getiSortCol_0()]);
        return page;
    }

    /**
     * dataTable 页面dataTable参数包装
     *
     * @param page          分页数据
     * @param basePageModel 返回类型
     */
    public BasePageModel dataTableWapper(Page<?> page, BasePageModel basePageModel) {
        basePageModel.setAaData(page);
        basePageModel.setiTotalDisplayRecords((int) page.getTotal());
        basePageModel.setiTotalRecords((int) page.getTotal());
        return basePageModel;
    }
}
