package com.aoji.mapper;

import com.aoji.model.VisitInfo;
import tk.mybatis.mapper.common.Mapper;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

public interface VisitInfoMapper extends Mapper<VisitInfo> {
    List<VisitInfo> selectLastVisitDateByStudentNo(String studentNo);
}