package com.aoji.service;

import com.aoji.model.ApplyInfo;
import com.aoji.vo.ApplyCollegeVo;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;

/**
 * @author yangsaixing
 * @description 申请院校的服务
 * @date Created in 下午2:29 2017/12/7
 */
public interface ApplyCollegeService {

    /**
     * 分页查询申请记录
     * @param applyInfo 申请实体
     * @param roles 当前用户角色
     * @param oaid 当前用户工号
     * @return
     */
    List<ApplyInfo> getByPage(ApplyInfo applyInfo, List<String> roles, String oaid);


    /**
     * 分页查询入读院校的列表
     * @param applyInfo 申请实体
     * @param
     * @param
     * @return
     */
    List<ApplyInfo> selectByPlanCourseStatus(ApplyInfo applyInfo);


    /**
     * 分页查询入读院校的列表(pdf)
     * @param applyInfo 申请实体
     * @param
     * @param
     * @return
     */
    Map<String, Object> selectPdfByPlanCourseStatus(ApplyInfo applyInfo);

    /**
     * 分页查询申请记录（销售顾问）
     * @param applyInfo 申请实体
     *
     * @return
     */
    List<ApplyInfo> getSaleByPage(ApplyInfo applyInfo);

    /**
     * 根据申请id查询申请信息
     * @param id 申请id
     * @return
     */
    ApplyInfo getById(Integer id);

    /**
     * 根据申请id查询申请详情
     * @param applyId 申请id
     * @return
     */
    ApplyCollegeVo getApplyDetail(Integer applyId);

    /**
     * 根据申请id查询申请详情，NPA非私密附件，私密附件地址前台获取，不绑定
     * @param applyId 申请id
     * @return
     */
    ApplyCollegeVo getApplyDetailNPA(Integer applyId);

    /**
     * 保存申请信息
     * @param applyCollegeVo 申请实体
     * @return
     */
    Boolean saveApplyAndSupplement(ApplyCollegeVo applyCollegeVo);

    /**
     * 更新申请记录实体
     * @param applyInfo
     * @return
     */
    Integer update(ApplyInfo applyInfo);

    /**
     * 根据内网学号查询录取院校列表
     * @param studentNo 内网学号
     * @return
     */
    List<ApplyInfo> queryAcceptSchoolByStudentNo(String studentNo);

    /**
     * 根据内网学号查询申请的院校列表
     * @param studentNo
     * @return
     */
    List<ApplyInfo> queryApplyInfoByStudentNo(String studentNo);

    /**
     * 插入院校申请信息
     * @param applyInfo
     * @return
     */
    Integer insert(ApplyInfo applyInfo);

    /**
     * 根据studentNo,applyId获取外联联系人
     * @param studentNo
     * @param applyId
     * @return
     */
    String getApplyConnector(String studentNo,Integer applyId);

    /**
     * 学生接受offer
     * @param applyId
     * @return
     */
    Boolean acceptOffer(Integer applyId);

    ApplyInfo get(ApplyInfo applyInfo);

    List<String> getApplyInfoByStudentNo(String StudentNo);

    Boolean removeApply(Integer applyId);

    void judgeUser(Model model);

    Map<String,Object> getApplyCostByStudentNo(String studentNo);

    ApplyInfo  basicCostApplylist(ApplyInfo applyInfo);

    Boolean basicEditFee(ApplyInfo applyInfo);

    /**
     * 管理层查看-所有院校申请列表
     * @param applyInfo
     * @return
     */
    List<ApplyInfo> checkAllApplyList(ApplyInfo applyInfo,String dateStart, String dateEnd);
}
