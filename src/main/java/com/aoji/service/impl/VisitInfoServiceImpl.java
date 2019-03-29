package com.aoji.service.impl;

import com.aoji.contants.*;
import com.aoji.mapper.VisitInfoMapper;
import com.aoji.model.VisitInfo;
import com.aoji.service.KsyunKs3UtilService;
import com.aoji.service.UserService;
import com.aoji.service.VisitInfoService;
import com.fasterxml.jackson.annotation.JsonView;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import lombok.experimental.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhaojianfei
 * @description 回访记录
 * @date Created in 下午2:31 2017/12/7
 */
@Service
public class VisitInfoServiceImpl implements VisitInfoService {
    @Override
    public Integer add(VisitInfo visitInfo) {
        return visitInfoMapper.insertSelective(visitInfo);
    }

    @Override
    public Integer update(VisitInfo visitInfo) {
        return visitInfoMapper.updateByPrimaryKeySelective(visitInfo);
    }

    @Autowired
    VisitInfoMapper visitInfoMapper;
    @Autowired
    UserService userService;

    @Value("${upload.ks3.resDomain}")
    private String resDomain;

    @Autowired
    private KsyunKs3UtilService ksyunKs3UtilService;

    @Override
    public List<VisitInfo> getList(VisitInfo visitInfo) {
        Example example = new Example(VisitInfo.class);

        if (visitInfo.getBusinessId() != null) {
            example.createCriteria().andEqualTo("deleteStatus", false).andEqualTo("studentNo", visitInfo.getStudentNo()).andEqualTo("visitCase", visitInfo.getVisitCase()).andEqualTo("isPublic",1).andEqualTo("businessId", visitInfo.getBusinessId());
        } else {
            example.createCriteria().andEqualTo("deleteStatus", false).andEqualTo("studentNo", visitInfo.getStudentNo()).andEqualTo("visitCase", visitInfo.getVisitCase()).andEqualTo("isPublic",1);
        }
        example.setOrderByClause("create_time DESC");
        List<VisitInfo> list = visitInfoMapper.selectByExample(example);
        for (VisitInfo item : list) {
            if (StringUtils.hasText(item.getAttachment()) && !item.getAttachment().contains(resDomain)) {
                item.setAttachment(resDomain + item.getAttachment());
            }
            if (item.getSenderType() != null && item.getSenderType() != -1) {
                item.setSenderType_string(VisitInfoServiceImpl.getSenderType(item.getSenderType()));
            }
            if (item.getVisitWay() != null && item.getVisitWay() != -1) {
                item.setVisitWay_string(VisitInfoServiceImpl.getVisitWay(item.getVisitWay()));
            }
            if (item.getVisitCase() != null && item.getVisitCase() != -1) {
                item.setVisitCase_string(VisitInfoServiceImpl.getVisitCase(item.getVisitCase()));
            }
            if (item.getVisitType() != null && item.getVisitType() != -1) {
                item.setVisitType_string(VisitInfoServiceImpl.getVisitType(item.getVisitType()));
            }
            if (item.getCreateTime() != null) {
                item.setCreateTimeString(new SimpleDateFormat(Contants.datePattern).format(item.getCreateTime()));
            }
            if (item.getVisitDate() != null) {
                item.setVisitDateString(new SimpleDateFormat(Contants.datePattern).format(item.getVisitDate()));
            }
            if (StringUtils.hasText(item.getContent())) {
                if (item.getContent().contains(resDomain)) {
                    List<String> imgSrc = getImgSrc(item.getContent().replaceAll(resDomain, ""));
                    if (imgSrc != null && imgSrc.size() > 0) {
                        for (String imgSrcUrl : imgSrc) {
                            String s = ksyunKs3UtilService.generatePrivateUrl("", imgSrcUrl);
                            item.setContent(item.getContent().replaceAll("<\\s*img\\s+([^>]*)\\s*>", "<img src='" + s + "' >"));
                        }
                    }

                }
            }
            if (StringUtils.hasText(item.getAttachment())) {
                String fileUrl = ksyunKs3UtilService.generatePrivateUrl("", item.getAttachment());
                item.setAttachment(fileUrl);
            }
        }
        return list;
    }

    public List<String> getImgSrc(String htmlStr) {
        String img = "";
        Pattern p_image;
        Matcher m_image;
        List<String> pics = new ArrayList<String>();
//		 String regEx_img = "<img.*src=(.*?)[^>]*?>"; //图片链接地址
        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
        p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            img = img + "," + m_image.group();
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            while (m.find()) {
                pics.add(m.group(1));
            }
        }
        return pics;
    }


    @Override
    public List<VisitInfo> getListForSupplement(Integer applyId) {
        Example example = new Example(VisitInfo.class);
        if (applyId != null) {
            example.createCriteria().andEqualTo("deleteStatus", false).andEqualTo("businessId", applyId).andEqualTo("visitType", Contants.VISITTYPE_CONFIRM_MATERIAL);
        }
        List<VisitInfo> list = visitInfoMapper.selectByExample(example);
        for (VisitInfo item : list) {
            item.setOperatorName(userService.getUserByName(item.getOperatorNo()).getUsername());
        }
        return list;
    }

    @Override
    public VisitInfo get(Integer id) {
        VisitInfo visitInfo = new VisitInfo();
        visitInfo.setId(id);
        visitInfo.setDeleteStatus(false);
        List<VisitInfo> list = visitInfoMapper.select(visitInfo);
        VisitInfo model = null;
        if (list.size() > 0) {
            model = list.get(0);
            if (StringUtils.hasText(model.getAttachment()) && !model.getAttachment().contains(resDomain)) {
                model.setAttachment(resDomain + model.getAttachment());
            }
            if (model.getSenderType() != null) {
                model.setSenderType_string(VisitInfoServiceImpl.getSenderType(model.getSenderType()));
            }
            if (model.getVisitWay() != null) {
                model.setVisitWay_string(VisitInfoServiceImpl.getVisitWay(model.getVisitWay()));
            }
            if (model.getVisitCase() != null) {
                model.setVisitCase_string(VisitInfoServiceImpl.getVisitCase(model.getVisitCase()));
            }
            if (model.getVisitType() != null) {
                model.setVisitType_string(VisitInfoServiceImpl.getVisitType(model.getVisitType()));
            }
        }
        return model;
    }

    @JsonView
    public static void main(String[] args) {

    }

    public static String getSenderType(int key) {
        return SenderType.get().get(key);
    }

    public static String getVisitWay(int key) {
        return VisitWay.get().get(key);
    }

    public static String getVisitCase(int key) {
        return VisitCase.get().get(key);
    }

    public static String getVisitType(int key) {
        return VisitType.get().get(key);
    }
}
