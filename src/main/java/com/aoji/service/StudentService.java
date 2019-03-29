package com.aoji.service;

import com.aoji.model.ChannelStudentInfo;
import com.aoji.model.StudentDelayInfo;
import com.aoji.model.StudentInfo;
import com.aoji.model.req.RefundEndCaseReq;
import com.aoji.model.req.StudentInfoReq;
import com.aoji.model.res.Consultor;
import com.aoji.model.res.StudentListRes;
import com.aoji.vo.StudentDetailVO;
import com.aoji.vo.StudentInfoVo;
import com.aoji.vo.StudentVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author yangsaixing
 * @description
 * @date Created in 上午11:14 2017/11/10
 */
public interface StudentService {

    /**
     * 查询学生列表
     *
     * @param studentInfo
     * @return
     */
    List<StudentInfo> getList(StudentInfo studentInfo);
    List<StudentInfo> getList(StudentInfo studentInfo, String roleName);

    /**
     * 查询学生总数
     *
     * @param studentInfo
     * @param roleName
     * @return
     */
    int getListCount(StudentInfo studentInfo, String roleName,Boolean isChannelStatus);

    /**
     * 根据学生id查询信息
     *
     * @param id
     * @return
     */
    StudentInfo getById(Integer id);

    /**
     * 更新学生信息
     *
     * @param studentInfo
     * @return
     */
    Integer update(StudentInfo studentInfo);

    /**
     * 修改进程状态
     *
     * @param studentNo     学号
     * @param currentStatus 当前状态
     * @param operator      操作人
     * @return
     */
    boolean updateProcessStatus(String studentNo, Integer currentStatus, String operator);

    /**
     * 根据学号查询学生服务
     *
     * @param studentNo
     * @return
     */
    List<String> getServiceByStudentNo(String studentNo);

    StudentInfo get(StudentInfo studentInfo);
    StudentInfo get1(StudentInfo studentInfo);

    /**
     * 根据学号和服务名称查询该学生是否有该服务
     *
     * @param studentNo 学号
     * @param serviceId 服务id
     * @return
     */
    Integer getExitStudentService(String studentNo, Integer serviceId);

    /**
     * 根据员工工号查询他权限内的学生信息
     *
     * @param oaId
     * @param statusCode
     * @return
     */
    Integer getStudentsByOAAndStatus(String oaId, Integer statusCode);

    /**
     * 根据状态code查询学生个数
     *
     * @param oaId
     * @param statusCode
     * @return
     */
    Integer getCountByOAIdAndStatus(String oaId, Integer statusCode);

    /**
     * 根据学号修改信息
     *
     * @param studentInfo
     * @return
     */
    Boolean updateByStudentNo(StudentInfo studentInfo);
    Boolean updateSalesConsultantByStudentNo(StudentInfo studentInfo,HttpServletRequest request);

    /**
     * 根据学号查询文签联系人
     *
     * @param studentNo
     * @return
     */
    String getCopyOperator(String studentNo);

    /**
     * 根据学生的学号查询学生的信息
     */
    StudentInfo getStudentInfoByStudentNo(String studentNo);

    /**
     * 根据学生的学号查询学生的信息(pdf)
     */
    Map<String, Object> getPdfStudentInfo(String studentNo);

    /**
     * 查询学生详情
     *
     * @param studentNo
     * @return
     */
    StudentDetailVO getStudentDetailByStudentNo(String studentNo);

    /**
     * 查询学生手机号（从资源库拿手机号码）
     *
     * @param studentNo
     * @return
     */
    Map<String,String> GetPhoneNumByStudentNo(String studentNo);

    /**
     * 申请缓办
     *
     * @param studentDelayInfo
     * @return
     */
    String approveDelay(StudentDelayInfo studentDelayInfo, Integer auditResultSize);

    /**
     * 查询销售顾问
     *
     * @param studentNo
     * @return
     */
    Consultor getConsultorByStudentNo(String studentNo);

    /**
     * 查询综合部待审批数据
     *
     * @param Type
     * @return
     */
    List<StudentInfo> getGDUnAuditInfo(Integer Type,String studentNo,String nationName);

    /**
     * 导出
     *
     * @param request
     * @param response
     */
    void excel(HttpServletRequest request, HttpServletResponse response);

    void excelVisitInfo(HttpServletRequest request, HttpServletResponse response);

    /**
     * 分页
     *
     * @param studentInfo
     * @return
     */
    List<StudentInfo> getStudentINfoList(HttpServletRequest request, StudentInfoVo studentInfo, String roleName,Boolean isChannelStatus,Boolean isPlannManager);

    /**
     * 根据oaId获取待回访学生列表
     *
     * @param oaId
     * @return
     */
    List<StudentInfo> getStudentListByOaId(StudentInfo studentInfo, String oaId, String roleName);

    /**
     * 根据国家和分支查询学生 -- 咨询顾问查看
     *
     * @param studentInfoReq
     * @return
     */
    StudentListRes getStudentByBranchAndCountry(StudentInfoReq studentInfoReq);

    Integer addStudentService(String studentNo,String addMessage);

    List<StudentVO> getIsSettleStudents(String oaid);

    Boolean getIsSettle(String studentNo);

    List<StudentInfo> getSettleList(StudentInfo studentInfo);

    List<StudentInfo> getDisabledList(String studentNo,String studentName);

    Integer getDisabledListCount(String studentNo,String studentName);


    StudentDetailVO getChannelStudentInfo(ChannelStudentInfo channelStudentInfo, StudentDetailVO studentDetailVO, String studentNo, int i);

    List<String> getIsSettle1(String studentNo);

    ChannelStudentInfo getStudentMessageByStudentNo(String studentNo);

    boolean refundEndCase(RefundEndCaseReq refundEndCaseReq);

    List<Map> getUserMap(StudentInfo studentInfo);

    /**
     * 查询我提交的待审批首次寄出列表
     */
    List<StudentInfo> getFirstBonusList(String oaid);

    /**
     * 查询待我审批的首次寄出列表
     */
    List<StudentInfo> getToAuditFirstBonusList(String oaid,StudentInfo studentInfo);

    /**
     * 更新学生基本信息（含转案表） -- 运营权限
     * @param studentInfo
     * @return
     */
    boolean updateStudentInfo(StudentInfo studentInfo);

    /**
     * 获取callCenter地址
     * @param studentNo
     * @param studentName
     * @return
     */
    String getCallCenterUrl(String studentNo, String studentName);
}
