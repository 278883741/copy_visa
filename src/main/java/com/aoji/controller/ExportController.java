package com.aoji.controller;
import com.aoji.mapper.CostInfoMapper;
import com.aoji.model.ExportParamModel;
import com.aoji.utils.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author yangsaixing
 * @description
 * @date Created in 下午2:33 2018/11/23
 */
@Controller
public class ExportController {
    @Autowired
    CostInfoMapper costInfoMapper;

    @RequestMapping("/export/util")
    public void export(HttpServletResponse response){
        List<Map> models=costInfoMapper.queryCosts();

        ExcelUtil.export(response,models,ExportParamModel.class);
    }
}
