package com.aoji.mapper;

import com.aoji.model.ChannelStudentInfo;
import com.aoji.model.StudentInfo;
import com.aoji.vo.ChannelStudentInfoVo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ChannelStudentInfoMapper extends Mapper<ChannelStudentInfo> {
    /**
     * 查询同业学生详细信息
     * @param channelStudentInfo
     * @return
     */
    List<ChannelStudentInfo> getList(@Param("channelStudentInfo") ChannelStudentInfo channelStudentInfo);
    /**
     * 根据学号查询同业学生详细信息
     * @param studentNo
     * @return
     */
    ChannelStudentInfo selectChannelStudentInfoByStudentNo(String studentNo);
}