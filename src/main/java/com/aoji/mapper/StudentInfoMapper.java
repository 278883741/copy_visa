package com.aoji.mapper;

import com.aoji.model.StudentInfo;
import com.aoji.model.bo.StudentInfoBO;
import com.aoji.model.dto.StudentInfoDTO;
import com.aoji.model.req.StudentInfoReq;
import com.aoji.vo.IMStudentSearchVO;
import com.aoji.vo.StudentVO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface StudentInfoMapper extends Mapper<StudentInfo> {

    int updateByStudentNo(StudentInfo studentInfo);

    List<StudentInfoDTO> selectStudentAndServiceByStudentNo(String studentNo);

    List<String> selectServiceCodeByStudentNo(String studentNo);

    int selectStudentInfoByMoreConditionCount(@Param("studentInfo") StudentInfo studentInfo, @Param("roleName") String roleName,@Param("isChannelStatus") Boolean isChannelStatus);

    List<StudentInfo> selectStudentInfoByMoreCondition(@Param("studentInfo") StudentInfo studentInfo, @Param("roleName") String roleName);

    List<StudentInfo> selectStudentInfoByMoreCondition(
            @Param("studentInfo") StudentInfo studentInfo,
            @Param("roleName") String roleName,
            @Param("sortPro") String sortPro,
            @Param("sSortDir_0") String sSortDir_0,
            @Param("isChannelStatus")Boolean isChannelStatus,
            @Param("isPlannManager")Boolean isPlannManager,
            @Param("pageStart")Integer pageStart,
            @Param("pageEnd")Integer pageEnd);

    StudentInfo getStudentMaterial(String id);

    //根据学生的序号查询学生的服务进程状态
    StudentInfo getStudentInfoByStudentNo(@Param("studentNo") String studentNo);

    List<StudentInfo> getStudentInfoByCondition(@Param("studentInfo") StudentInfo studentInfo,
                                                @Param("oaid") String oaid);

    //查询当前登录人的签证待审批信息(用studentInfo接收数据，审批id用studentInfo.id接收)
    List<StudentInfo> getVisaUnAudit(@Param("oaid") String oaid,@Param("studentNo") String studentNo,@Param("nationName") String nationName);

    //查询当前登录人的签证结果待审批信息(用studentInfo接收数据，审批id用studentInfo.id接收)
    List<StudentInfo> getVisaResultUnAudit(@Param("oaid") String oaid,@Param("studentNo") String studentNo,@Param("nationName") String nationName);

    //查询当前登录人的结案待审批信息(用studentInfo接收数据，审批id用studentInfo.id接收)
    List<StudentInfo> getSettleUnAudit(@Param("oaid") String oaid,@Param("studentNo") String studentNo,@Param("nationName") String nationName);

    /**
     * 根据oaId获取待回访学生列表
     *
     * @param operator
     * @return
     */
    List<StudentInfo> getStudentListByOaId(@Param("studentInfo") StudentInfo studentInfo, @Param("operator") String operator, @Param("roleName") String roleName);

    /**
     * 根据国家和分支查询学生 -- 咨询顾问查看
     *
     * @param studentInfoReq
     * @return
     */
    List<StudentInfoBO> getStudentByBranchAndCountry(@Param("studentInfoReq") StudentInfoReq studentInfoReq,
                                                     @Param("studentInfoURL") String studentInfoURL,
                                                     @Param("begin") Integer begin,
                                                     @Param("end") Integer end);

    /**
     * 根据国家和分支查询学生 -- 咨询顾问查看
     *
     * @param studentInfoReq
     * @return
     */
    int getStudentByBranchAndCountryCount(@Param("studentInfoReq") StudentInfoReq studentInfoReq);

    List<StudentVO> getIsSettleStudents(@Param("oaid") String oaid);

    List<StudentInfo> getStudentInfoByOaid(@Param("oaid") String oaid,@Param("type") Integer type,@Param("startIndex") Integer startIndex,@Param("pageSize") Integer pageSize);

    List<StudentInfo> getDisabledList(@Param("studentNo") String oaid,@Param("studentName") String studentName);
    Integer getDisabledListCount(@Param("studentNo") String oaid,@Param("studentName") String studentName);

    List<String> getStudentNoList(@Param("studentNoList") List<String> studentNoList);

    List<StudentInfo> getAuditStudentByAgentId(@Param("studentInfo")StudentInfo studentInfo,
                                               @Param("channelStatus")Boolean channelStatus,
                                               @Param("agentId")String agentId);

    List<Map> getUserMap(StudentInfo studentInfo);

    /**
     * 查询我提交的待审批首次寄出列表
     */
    List<StudentInfo> getFirstBonusList(@Param("oaid") String oaid);

    /**
     * 查询待我审批的首次寄出列表
     */
    List<StudentInfo> getToAuditFirstBonusList(@Param("oaid") String oaid,@Param("studentInfo") StudentInfo studentInfo);

    /**
     * IM 学生搜索数据
     */
    List<StudentInfo> getIMStudentSearchData(@Param("imStudentSearchVOS") List<IMStudentSearchVO> imStudentSearchVOS);
}
