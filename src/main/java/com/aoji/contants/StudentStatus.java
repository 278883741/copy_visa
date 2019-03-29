package com.aoji.contants;

import com.aoji.vo.StudentProcessStatusVO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author yangsaixing
 * @description 学生状态枚举类
 * @date Created in 下午5:31 2017/12/22
 */
public enum StudentStatus {

    NO_COPY(10, "未上传文书材料", Contants.COFFICE_COPY),
    NO_COLLEGE_APPLY(20, "未递交申请材料", Contants.APPLICATION), 
    NO_COLLEGE_RESULT(30, "未收到申请结果", Contants.APPLICATION), 
    NO_COLLEGE_INFO(40, "未确认录取院校", Contants.APPLICATION), 
    NO_VISA_COACH(50, "未进行签证辅导", ""),
    NO_VISA_APPLY(60, "未递交签证申请", Contants.VISA), 
    NO_VISA_RESULT(70, "未收到签证结果", Contants.VISA), 
    NO_VISA_INFO(80,  "未填写获签信息",Contants.VISA),
    INCOMPLETE(85,  "未结案",""),
    COMPLETED(90, "已结案", ""),
    REFUND(100, "已退费", "");

    /**
     * 状态code
     */
    private int code;
    /**
     * 状态名称
     */
    private String name;
    /**
     * 服务代码
     */
    private String serviceCode;

     StudentStatus(int index, String name, String serviceCode) {
        this.name = name;
        this.code = index;
        this.serviceCode = serviceCode;
    }

    /**
     * 获取下一个进程状态
     * @param serviceCodes
     * @param currentStatus
     * @return
     */
    public static Integer getNextStatusCode(List<String> serviceCodes, Integer currentStatus){
        // 退费
        if(REFUND.getCode() == currentStatus){
            return REFUND.getCode();
        }
        //手动结案
        if(INCOMPLETE.getCode() == currentStatus){
            return COMPLETED.getCode();
        }

        StudentStatus[] studentStatusArray = StudentStatus.values();
        List<Integer> statusCodes = new ArrayList<Integer>();
        // 根据服务获取对应状态
        serviceCodes.forEach(serviceCode -> {
            Arrays.stream(studentStatusArray).forEach(studentStatus -> {
                if(studentStatus.getServiceCode().equals(serviceCode)){
                    statusCodes.add(studentStatus.getCode());
                }
            });
        });
        statusCodes.add(StudentStatus.COMPLETED.getCode());
        // 排序
        Collections.sort(statusCodes);
        Integer nextStatus = null;
        // 判断当前状态是否存在
        if(statusCodes.contains(currentStatus)){
            // 拿到当前状态的index
            int index = statusCodes.indexOf(currentStatus);
            if(index < statusCodes.size() - 1){
                // 返回下一个状态
                nextStatus = statusCodes.get(index + 1);
            }
        }
        //需要手动结案，返回未结案的状态
        if(NO_VISA_INFO.getCode() != currentStatus && COMPLETED.getCode() == nextStatus){
            return INCOMPLETE.getCode();
        }
        //自动结案，跳过未结案的状态
        if(NO_VISA_INFO.getCode() == currentStatus){
            return COMPLETED.getCode();
        }
        return nextStatus;
    }

    /**
     * 根据服务获取进程状态
     * @param serviceCodes
     * @return
     */
    public static List<StudentProcessStatusVO> getStatusByServiceCode(List<String> serviceCodes){
        List<StudentProcessStatusVO> studentProcessStatusVOS = new ArrayList<StudentProcessStatusVO>();
        StudentStatus[] studentStatusArray = StudentStatus.values();
        // 根据服务获取对应状态
        serviceCodes.forEach(serviceCode -> {
            Arrays.stream(studentStatusArray).forEach(studentStatus -> {
                if(studentStatus.getServiceCode().equals(serviceCode)){
                    StudentProcessStatusVO studentProcessStatusVO = new StudentProcessStatusVO();
                    studentProcessStatusVO.setCode(studentStatus.getCode());
                    studentProcessStatusVO.setName(studentStatus.getName());
                    studentProcessStatusVOS.add(studentProcessStatusVO);
                }
            });
        });
        StudentProcessStatusVO studentProcessStatusVO = new StudentProcessStatusVO();
        studentProcessStatusVO.setCode(StudentStatus.INCOMPLETE.getCode());
        studentProcessStatusVO.setName(StudentStatus.INCOMPLETE.getName());
        studentProcessStatusVOS.add(studentProcessStatusVO);
        //排序
        studentProcessStatusVOS.sort((status1, status2) -> {
            if(status1.getCode() < status2.getCode()){
                return -1;
            }else if(status1.getCode() == status2.getCode()){
                return 0;
            }else{
                return 1;
            }
        });
        return studentProcessStatusVOS;
    }

    /**
     * 根据状态code查询状态名称
     * @param code
     * @return
     */
    public static String getNameByCode(int code){
        StudentStatus[] studentStatuses = StudentStatus.values();
        for(StudentStatus ss : studentStatuses){
            if(ss.getCode() == code){
                return ss.getName();
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }
}
