package com.aoji.service;

import com.aoji.model.ApplyResultInfo;
import com.aoji.model.BaseResponse;
import com.aoji.model.StudentInfo;
import com.aoji.vo.ApplyResultVo;
import org.json.JSONObject;
import org.springframework.ui.Model;

import java.util.Date;
import java.util.List;

/**
 * @author yangsaixing
 * @description
 * @date Created in 下午2:29 2017/12/7
 */
public interface ApplyResultService {

    /**
     * 分页查询申请结果记录
     * @param applyResultInfo 申请结果参数实体
     * @return
     */
    List<ApplyResultInfo> getByPage(ApplyResultInfo applyResultInfo);
    /**
     * 分页查询申请结果记录,NPA非私密附件，私密附件地址前台获取，不绑定
     * @param applyResultInfo 申请结果参数实体
     * @return
     */
    List<ApplyResultInfo> getByPageNPA(ApplyResultInfo applyResultInfo);

    List<ApplyResultInfo> getByPageList(ApplyResultInfo applyResultInfo);

    /**
     * 根据申请结果id查询申请结果信息
     * @param id 申请结果id
     * @return
     */
    ApplyResultInfo getById(Integer id);

    /**
     * 保存offer结果页的信息
     * @param applyResultVo
     * @return
     */
    BaseResponse saveResultAndSchool(ApplyResultVo applyResultVo) throws Exception;

    /**
     * 单个实体添加
     * @param applyResultInfo
     * @return
     */
    Integer insertResult(ApplyResultInfo applyResultInfo);

    /**
     * 单个实体修改
     * @param applyResultInfo
     * @return
     */
    Integer update(ApplyResultInfo applyResultInfo);

    /**
     * 删除申请结果
     * @param applyResultId
     * @return
     */
    boolean remove(Integer applyResultId);

    ApplyResultInfo get(ApplyResultInfo applyResultInfo);

    /**
     * 审批
     * @param applyId
     * @param type
     * @param remark
     * @return
     */
    BaseResponse approve(Integer applyId, Integer type, String remark, Integer id, String studentNo, String updateTime,BaseResponse baseResponse );

    StudentInfo getStudentInfo(String applyId);

    /**
     * 查询员工信息
     *
     * @param memberId
     * @return
     */
    String getStaffInfo(String memberId);

}
