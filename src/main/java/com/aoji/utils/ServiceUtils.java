package com.aoji.utils;

import com.aoji.contants.Contants;
import com.aoji.contants.StudentStatus;
import com.aoji.mapper.SysUserRoleMapper;
import com.aoji.model.ServiceInfo;
import com.aoji.vo.StudentProcessStatusVO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServiceUtils {
    @Autowired
    SysUserRoleMapper sysUserRoleMapper;
    /**
     * 合同类型及对应服务：全程
     */
    public static final int CONTRACT_TYPE_ALL = 1;
    public static final List<String> CONTRACT_TYPE_ALL_SERVICES = Arrays.asList(Contants.COFFICE_COPY, Contants.APPLICATION, Contants.VISA, Contants.FOLLOW);

    /**
     * 合同类型及对应服务：单申请
     */
    public static final int CONTRACT_TYPE_APPLY = 2;
    public static final List<String> CONTRACT_TYPE_APPLY_SERVICES = Arrays.asList(Contants.COFFICE_COPY, Contants.APPLICATION);
    /**
     * 合同类型及对应服务：单文书
     */
    public static final int CONTRACT_TYPE_COPY = 3;
    public static final List<String> CONTRACT_TYPE_COPY_SERVICES = Arrays.asList(Contants.COFFICE_COPY);
    /**
     * 合同类型及对应服务：单签证
     */
    public static final int CONTRACT_TYPE_VISA = 4;
    public static final List<String> CONTRACT_TYPE_VISA_SERVICES = Arrays.asList(Contants.VISA, Contants.FOLLOW);


    /**
     * 根据合同类型获取服务ID的集合
     * @param serviceInfoList
     * @param contractType
     * @return
     */
    public static List<Integer> getServiceIdByContractType(List<ServiceInfo> serviceInfoList, int contractType){
        List<Integer> serviceIds = new ArrayList<>();
        // 全程
        if (CONTRACT_TYPE_ALL == contractType){
            serviceInfoList.forEach(serviceInfo -> {
                if(CONTRACT_TYPE_ALL_SERVICES.contains(serviceInfo.getServiceCode())){
                    serviceIds.add(serviceInfo.getId());
                }
            });
        // 单申请
        } else if (CONTRACT_TYPE_APPLY == contractType){
            serviceInfoList.forEach(serviceInfo -> {
                if(CONTRACT_TYPE_APPLY_SERVICES.contains(serviceInfo.getServiceCode())){
                    serviceIds.add(serviceInfo.getId());
                }
            });
        // 单文书
        } else if(CONTRACT_TYPE_COPY == contractType){
            serviceInfoList.forEach(serviceInfo -> {
                if(CONTRACT_TYPE_COPY_SERVICES.contains(serviceInfo.getServiceCode())){
                    serviceIds.add(serviceInfo.getId());
                }
            });
        // 单签证
        } else if(CONTRACT_TYPE_VISA == contractType){
            serviceInfoList.forEach(serviceInfo -> {
                if(CONTRACT_TYPE_VISA_SERVICES.contains(serviceInfo.getServiceCode())){
                    serviceIds.add(serviceInfo.getId());
                }
            });
        }
        return serviceIds;
    }

    /**
     * 根据合同类型获取初始状态
     * @param contractType
     * @return
     */
    public static Integer getInitStatusByContractNo(int contractType){
        List<StudentProcessStatusVO> statusVOS = new ArrayList<>();
        if (CONTRACT_TYPE_ALL == contractType){ //全程
            statusVOS = StudentStatus.getStatusByServiceCode(CONTRACT_TYPE_ALL_SERVICES);
        } else if (CONTRACT_TYPE_APPLY == contractType){ //单申请
            statusVOS = StudentStatus.getStatusByServiceCode(CONTRACT_TYPE_APPLY_SERVICES);
        } else if(CONTRACT_TYPE_COPY == contractType){ //单文书
            statusVOS = StudentStatus.getStatusByServiceCode(CONTRACT_TYPE_COPY_SERVICES);
        } else if(CONTRACT_TYPE_VISA == contractType){ //单签证
            statusVOS = StudentStatus.getStatusByServiceCode(CONTRACT_TYPE_VISA_SERVICES);
        }
        if(statusVOS.size() > 0){
            return statusVOS.get(0).getCode();
        }
        return null;
    }


}
