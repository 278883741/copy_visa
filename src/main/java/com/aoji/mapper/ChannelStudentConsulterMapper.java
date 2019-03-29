package com.aoji.mapper;

import com.aoji.model.ChannelStudentConsulter;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface ChannelStudentConsulterMapper extends Mapper<ChannelStudentConsulter> {

    String getConsulterByNo(@Param("consulterNo") String consulterNo);

    /**
     * 根据顾问工号获取顾问邮箱账号
     * @param consulterNo
     * @return
     */
    String selectEmailByConsulterNo(@Param("consulterNo") String consulterNo);
}