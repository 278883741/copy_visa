package com.aoji.service.impl;
import com.aoji.contants.*;
import com.aoji.contants.manager.SearchOptionVisaApply;
import com.aoji.mapper.VisaApplyInfoMapper;
import com.aoji.mapper.VisaTypeMapper;
import com.aoji.model.VisaApplyInfo;
import com.aoji.model.*;
import com.aoji.model.req.SendMessageReq;
import com.aoji.service.*;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author zhaojianfei
 * @description 签证申请信息表
 * @date Created in 下午2:31 2017/12/7
 */
@Service
public class VisaApplyServiceImpl implements VisaApplyService{
    @Autowired
    VisaApplyInfoMapper visaApplyInfoMapper;
    @Autowired
    VisaTypeMapper visaTypeMapper;
    @Autowired
    AuditApplyService auditApplyService;
    @Autowired
    StudentService studentService;
    @Autowired
    UserTaskRelationService userTaskRelationService;
    @Autowired
    UserService userService;
    @Autowired
    ApplyCollegeService applyCollegeService;
    @Autowired
    AuditResultService auditResultService;

    @Value("${upload.ks3.resDomain}")
    private String resDomain;
    @Override
    public boolean add(VisaApplyInfo visaApplyInfo){
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setStudentNo(visaApplyInfo.getStudentNo());
        studentInfo.setDeleteStatus(false);
        studentInfo = studentService.get(studentInfo);

        VisaApplyInfo visaApplyInfoTemp = new VisaApplyInfo();
        visaApplyInfoTemp.setStudentNo(visaApplyInfo.getStudentNo());

        List<VisaApplyInfo> listTemp = this.getList(visaApplyInfoTemp);
        if(listTemp == null || listTemp.size() == 0){
            studentInfo.setVisaSendDate(visaApplyInfo.getCreateTime());
            studentService.update(studentInfo);
        }

        if(visaApplyInfoMapper.insertSelective(visaApplyInfo) > 0) {
            SysUser sysUser = userService.getLoginUser();


            // 学生留学国家为加拿大给学生外联老师发工作消息
            if(studentInfo.getNationId().equals(CountryEnum.Canada.getCode())){
                List<String> list = applyCollegeService.getApplyInfoByStudentNo(studentInfo.getStudentNo());
                if(list != null && list.size()>0) {
                    for (String item : list) {
                        SendMessageReq sendMessageReq = new SendMessageReq();
                        sendMessageReq.setOaid(item);
                        sendMessageReq.setTemplateCode("visaApplyAdd");
                        sendMessageReq.setOperatorNo(sysUser.getOaid());
                        sendMessageReq.setStudentNo(visaApplyInfo.getStudentNo());
                        sendMessageReq.setTaskType(Contants.WORK_MESSAGE);
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("studentNo", visaApplyInfo.getStudentNo());
                        map.put("studentName", studentInfo.getStudentName());
                        sendMessageReq.setTemplateParam(map);
                        userTaskRelationService.sendMessage(sendMessageReq);
                    }
                }
            }
            return auditApplyService.add(visaApplyInfo.getId(), CaseIdEnum.VisaApply.getCode(), 1, visaApplyInfo.getStudentNo(),"","");
        }
        return false;
    }

    @Override
    public Integer update(VisaApplyInfo visaApplyInfo) {
        return visaApplyInfoMapper.updateByPrimaryKeySelective(visaApplyInfo);
    }

    @Override
    public List<VisaApplyInfo> getList(VisaApplyInfo visaApplyInfo) {
        Example example=new Example(VisaApplyInfo.class);
        example.createCriteria().andEqualTo("deleteStatus",false).andEqualTo("studentNo",visaApplyInfo.getStudentNo());
        example.setOrderByClause("create_time DESC");
        List<VisaApplyInfo> list= visaApplyInfoMapper.selectByExample(example);
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setStudentNo(visaApplyInfo.getStudentNo());
        studentInfo.setDeleteStatus(false);
        studentInfo = studentService.get(studentInfo);
        if(list != null && list.size() > 0) {
            for (VisaApplyInfo item : list) {
                item.setVisaWay_string(getVisaWay().get(item.getVisaWay()));
                if (item.getVisaType() != null) {
                    item.setVisaType_string(getVisaTypeName(item.getVisaType()));
                }
            }
        }
        return list;

    }

    @Override
    public List<Map<String,Object>> getManegeList(SearchOptionVisaApply searchOption) {
        List<Map<String,Object>> list = visaApplyInfoMapper.getManageList(searchOption);
        return list;
    }

    @Override
    public List<VisaApplyInfo> getListByOpeator(VisaApplyInfo visaApplyInfo) {
       return visaApplyInfoMapper.select(visaApplyInfo);
    }

    @Override
    public VisaApplyInfo get(VisaApplyInfo visaApplyInfo) {
        visaApplyInfo.setDeleteStatus(false);
        List<VisaApplyInfo> list = visaApplyInfoMapper.select(visaApplyInfo);
        if(list !=null  && list.size() > 0 ){
            visaApplyInfo = list.get(0);
            return visaApplyInfo;
        }
        return null;
    }

    @Override
    public HashMap<Integer,String> getVisaWay(){
        return VisaWay.get();
    }

    public final static List<VisaType> list_VisaType = new ArrayList<VisaType>();

    @Override
    public List<VisaType> getVisaTypeList(){
        List<VisaType> list = new ArrayList<VisaType>();
        if (VisaApplyServiceImpl.list_VisaType.size() <=0) {
            Example example = new Example(VisaType.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("deleteStatus", false);
            list = visaTypeMapper.selectByExample(example);
        }
        return list;
    }

    @Override
    public List<VisaType> getVisaTypeList(Integer nation){
        List<VisaType> list = new ArrayList<VisaType>();
        if (VisaApplyServiceImpl.list_VisaType.size() <=0) {
            Example example = new Example(VisaType.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("deleteStatus", false);
            criteria.andEqualTo("nation", nation);
            list = visaTypeMapper.selectByExample(example);
        }
        return list;
    }

    @Override
    public List<VisaType> getVisaTypeListBy(Integer studentVisaStatus,Integer nation){
        List<VisaType> list = new ArrayList<VisaType>();
        if(studentVisaStatus == -1){
            list = getVisaTypeList(nation);
        }
        else{
            boolean studentVisaStatus_bool = (studentVisaStatus == 1?true:false);
            for(VisaType item :getVisaTypeList(nation)){
                if(studentVisaStatus_bool == item.getStudentVisaStatus() && nation.equals(item.getNation())){
                    list.add(item);
                }
            }
        }
        return list;
    }

    @Override
    public List<VisaApplyInfo> getTODOList(VisaApplyInfo visaApplyInfo) {
        return visaApplyInfoMapper.select(visaApplyInfo);
    }

    @Override
    public String getVisaTypeName(Integer key){
        String VisaTypeName = "";
        if( VisaApplyServiceImpl.list_VisaType.size() <= 0) {
            getVisaTypeList();
        }
        for(VisaType item : getVisaTypeList()){
            if(key.equals(item.getId())){
                VisaTypeName =  item.getVisaName();
                break;
            }
        }
        return VisaTypeName;
    }

    @Override
    public Integer delete(VisaApplyInfo visaApplyInfo){
        SysUser sysUser = userService.getLoginUser();

        AuditApplyInfo auditApplyInfo = new AuditApplyInfo();
        auditApplyInfo.setBusinessId(visaApplyInfo.getId());
        auditApplyInfo.setDeleteStatus(false);
        auditApplyInfo.setCaseId(CaseIdEnum.VisaApply.getCode());
        auditApplyInfo = auditApplyService.get(auditApplyInfo);
        if(auditApplyInfo != null){
            auditApplyInfo.setDeleteStatus(true);
            auditApplyInfo.setUpdateTime(new Date());
            auditApplyInfo.setOperatorNo(sysUser.getOaid());
            auditApplyInfo.setOperatorName(sysUser.getUsername());
            auditApplyService.update(auditApplyInfo);
        }

        visaApplyInfo.setDeleteStatus(true);
        visaApplyInfo.setUpdateTime(new Date());
        visaApplyInfo.setOperatorNo(sysUser.getOaid());
        visaApplyInfo.setOperatorName(sysUser.getUsername());
        return visaApplyInfoMapper.updateByPrimaryKeySelective(visaApplyInfo);
    }

    @Override
    public List<Map<String,Object>> getUnPassVisaApplyList(String oaId){
        return visaApplyInfoMapper.getUnPassVisaApplyList(oaId);
    }

    @Override
    public List<VisaApplyInfo> getToAuditList(String oaId) {
        return visaApplyInfoMapper.selectToAuditList(oaId);
    }

    @Override
    public List<Map<String, Object>> getToAuditListWithMySubmited(String oaId) {
        return visaApplyInfoMapper.getToAuditListWithMySubmited(oaId);
    }
}
