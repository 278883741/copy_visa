package com.aoji.controller;

import com.alibaba.fastjson.JSONObject;
import com.aoji.contants.Contants;
import com.aoji.contants.StudentStatus;
import com.aoji.model.BaseResponse;
import com.aoji.model.PreStudentInfo;
import com.aoji.model.StudentInfo;
import com.aoji.model.req.ConsultantReq;
import com.aoji.model.req.TransferReq;
import com.aoji.model.res.TransferRes;
import com.aoji.service.PreStudentInfoService;
import com.aoji.service.StudentService;
import com.aoji.service.TransferSendService;
import com.aoji.utils.HttpUtils;
import io.swagger.annotations.*;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.MessageFormat;
import java.text.ParseException;

@Api(value = "Swagger测试控制器")
@Controller
public class AutoTransferController {

    @Autowired
    TransferSendService transferSendService;
    @Autowired
    StudentService studentService;
    @Autowired
    PreStudentInfoService preStudentInfoService;


    public static final Logger logger = LoggerFactory.getLogger(AutoTransferController.class);

    @RequestMapping(value = "/doTransfer",method = RequestMethod.POST)
    @ApiOperation(value = "转案",notes = "将学生由销售系统转到文签系统")
    @ResponseBody
    public TransferRes doTransferTest(@ApiParam(name = "transferReq", value = "转案请求对象") TransferReq transferReq){
        logger.info("doTransfer RQ : " + transferReq.toString());
        TransferRes transferRes = new TransferRes();
        try {

            // 预科国家
            if(Contants.preCounrtyList.contains(transferReq.getCountry())){
                try {
                    PreStudentInfo preStudentInfo = new PreStudentInfo();
                    this.convert(transferReq, preStudentInfo);
                    preStudentInfoService.insert(preStudentInfo);
                }catch (Exception e){
                    logger.error("preStudentInfoService insert failed! RQ "+ e.getMessage());
                    transferRes.setResult(false);
                    transferRes.setErrorMsg("系统异常");
                    return transferRes;
                }
                transferRes.setResult(true);
                return transferRes;
            }else{
                TransferRes res = transferSendService.doTransfer(transferReq);
                logger.info("doTransfer RS : " + res.toString());
                return res;
            }
        } catch (Exception e) {
            logger.error("doTransfer RS : " + e.getMessage());
            transferRes.setErrorMsg(e.getMessage());
            transferRes.setResult(false);
            return transferRes;
        }
    }

    /**
     * 参数转换
     * @param transferReq
     * @param preStudentInfo
     */
    private void convert(TransferReq transferReq, PreStudentInfo preStudentInfo) throws ParseException {
        preStudentInfo.setStudentNo(transferReq.getStudentNo());
        preStudentInfo.setStudentName(transferReq.getStudentName());
        preStudentInfo.setPinyin(transferReq.getSpelling());
        preStudentInfo.setBirthday(DateUtils.parseDate(transferReq.getBirthday(), Contants.datePattern));
        preStudentInfo.setContractNo(transferReq.getContractNo());
        preStudentInfo.setContractType(transferReq.getContractType());
        preStudentInfo.setUsaStatus(transferReq.getUsaAStatus());
        preStudentInfo.setOperatorNo(transferReq.getConsultantNo());
        preStudentInfo.setOperatorName(transferReq.getConsultant());
        preStudentInfo.setSignDate(DateUtils.parseDate(transferReq.getSignDate(), Contants.datePattern));
        preStudentInfo.setOldNationId(transferReq.getCountry());
        preStudentInfo.setBranchId(transferReq.getBrandId());
        preStudentInfo.setBranchName(transferReq.getBrandName());
        preStudentInfo.setSalesConsultant(transferReq.getConsultant());
        preStudentInfo.setSalesConsultantNo(transferReq.getConsultantNo());
        preStudentInfo.setChannelStatus(transferReq.getChannelStatus());
        preStudentInfo.setContent(transferReq.getComment());
        preStudentInfo.setConfeeid(transferReq.getConfeeid());
    }

    @RequestMapping(value = "/modifyConsultant",method = RequestMethod.POST)
    @ApiOperation(value = "修改咨询顾问",notes = "将学生咨询顾问同步到文签系统")
    @ResponseBody
    public BaseResponse modifyConsultant(@ApiParam(name = "consultantReq", value = "请求参数") ConsultantReq consultantReq){
        logger.info("modifyConsultant RQ : " + consultantReq.toString());
        BaseResponse response = new BaseResponse();
        try {
            StudentInfo studentInfo = new StudentInfo();
            studentInfo.setStudentNo(consultantReq.getStudentNo());
            studentInfo.setSalesConsultantNo(consultantReq.getConsultantNo());
            studentInfo.setSalesConsultant(consultantReq.getConsultant());
            Boolean result = studentService.updateByStudentNo(studentInfo);
            if(result){
                response.setResult(true);
            }else{
                response.setResult(false);
                response.setErrorMsg("修改失败！");
            }
            return response;
        } catch (Exception e) {
            logger.error("modifyConsultant RS : " + e.getMessage());
            response.setResult(false);
            response.setErrorMsg("修改失败！");
            return response;
        }
    }

    @RequestMapping(value = "/refundStatus",method = RequestMethod.POST)
    @ApiOperation(value = "退费修改状态",notes = "修改学生进程状态")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "form", dataType = "String", name = "studentNo", value="学号"),
            @ApiImplicitParam(paramType = "form", dataType = "String", name = "operatorNo", value = "操作人工号"),
            @ApiImplicitParam(paramType = "form", dataType = "String", name = "operator", value = "操作人姓名")})
    @ResponseBody
    public Boolean updateStatus(String studentNo, String operatorNo, String operator){
        logger.info(MessageFormat.format("updateStatus RQ : studentNo={0}, operatorNo={1}, operator={2}",
                studentNo, operatorNo, operator));
        return studentService.updateProcessStatus(studentNo, StudentStatus.REFUND.getCode(), operatorNo);
    }

}
