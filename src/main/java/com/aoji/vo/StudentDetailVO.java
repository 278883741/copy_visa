package com.aoji.vo;

import com.aoji.model.*;
import com.aoji.utils.ConvertUtils;

import java.util.List;

public class StudentDetailVO {

    /**
     * 展示完整手机号
     * @return
     */
    public String showTel(){
        return tel;
    }

    private String studentName;

    private String pinyin;

    private String wechat;

    private int gender;

    private String potentialNo;

    private String zipCode;

    private String birthday;

    private String tel;

    private String email;

    private String idNo;

    private boolean usaAStatus;

    private boolean branchRecommendStatus;

    private int agentType;

    private boolean doubleSignStatus;

    private boolean scholarshipStatus;

    private String futureEducation;

    private String contractType;

    private String contractName;

    private String nation;

    private String nationName;

    private String branch;

    private String branchName;

    private String consultor;

    private String consultorName;

    private String tranConsultor;

    private String tranConsultorName;

    private boolean discountStatus;

    private String contractServiceFee;

    private String refuseFee;

    private SpecialAgreement specialAgreement;

    private String accessoriesFee;

    private List<Education> educationList;

    private List<FamilyMember> familyMemberList;
    //工作经历
    private List<Experience> experienceList;
    //合同费用
    private List<ContractFee> contractFeeList;
    //合同附件
    private List<ContractFile> contractFileList;

    private ExtraApplyFee extraApplyFeeList;
    //国内地址
    private  String address;

    public String getTranConsultor() {
        return tranConsultor;
    }

    public void setTranConsultor(String tranConsultor) {
        this.tranConsultor = tranConsultor;
    }

    public String getTranConsultorName() {
        return tranConsultorName;
    }

    public void setTranConsultorName(String tranConsultorName) {
        this.tranConsultorName = tranConsultorName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getPotentialNo() {
        return potentialNo;
    }

    public void setPotentialNo(String potentialNo) {
        this.potentialNo = potentialNo;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getTel() {
        return ConvertUtils.hideTel(tel);
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public boolean isUsaAStatus() {
        return usaAStatus;
    }

    public void setUsaAStatus(boolean usaAStatus) {
        this.usaAStatus = usaAStatus;
    }

    public boolean isBranchRecommendStatus() {
        return branchRecommendStatus;
    }

    public void setBranchRecommendStatus(boolean branchRecommendStatus) {
        this.branchRecommendStatus = branchRecommendStatus;
    }

    public int getAgentType() {
        return agentType;
    }

    public void setAgentType(int agentType) {
        this.agentType = agentType;
    }

    public boolean isDoubleSignStatus() {
        return doubleSignStatus;
    }

    public void setDoubleSignStatus(boolean doubleSignStatus) {
        this.doubleSignStatus = doubleSignStatus;
    }

    public boolean isScholarshipStatus() {
        return scholarshipStatus;
    }

    public void setScholarshipStatus(boolean scholarshipStatus) {
        this.scholarshipStatus = scholarshipStatus;
    }

    public String getFutureEducation() {
        return futureEducation;
    }

    public void setFutureEducation(String futureEducation) {
        this.futureEducation = futureEducation;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getContractServiceFee() {
        return contractServiceFee;
    }

    public void setContractServiceFee(String contractServiceFee) {
        this.contractServiceFee = contractServiceFee;
    }

    public String getRefuseFee() {
        return refuseFee;
    }

    public void setRefuseFee(String refuseFee) {
        this.refuseFee = refuseFee;
    }

    public List<Education> getEducationList() {
        return educationList;
    }

    public void setEducationList(List<Education> educationList) {
        this.educationList = educationList;
    }

    public List<FamilyMember> getFamilyMemberList() {
        return familyMemberList;
    }

    public void setFamilyMemberList(List<FamilyMember> familyMemberList) {
        this.familyMemberList = familyMemberList;
    }

    public List<Experience> getExperienceList() {
        return experienceList;
    }

    public void setExperienceList(List<Experience> experienceList) {
        this.experienceList = experienceList;
    }

    public String getNationName() {
        return nationName;
    }

    public void setNationName(String nationName) {
        this.nationName = nationName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getConsultor() {
        return consultor;
    }

    public void setConsultor(String consultor) {
        this.consultor = consultor;
    }

    public String getConsultorName() {
        return consultorName;
    }

    public void setConsultorName(String consultorName) {
        this.consultorName = consultorName;
    }

    public boolean isDiscountStatus() {
        return discountStatus;
    }

    public void setDiscountStatus(boolean discountStatus) {
        this.discountStatus = discountStatus;
    }

    public String getAccessoriesFee() {
        return accessoriesFee;
    }

    public void setAccessoriesFee(String accessoriesFee) {
        this.accessoriesFee = accessoriesFee;
    }

    public List<ContractFee> getContractFeeList() {
        return contractFeeList;
    }

    public void setContractFeeList(List<ContractFee> contractFeeList) {
        this.contractFeeList = contractFeeList;
    }

    public void setSpecialAgreement(SpecialAgreement specialAgreement) {
        this.specialAgreement = specialAgreement;
    }

    public ExtraApplyFee getExtraApplyFeeList() {
        return extraApplyFeeList;
    }

    public void setExtraApplyFeeList(ExtraApplyFee extraApplyFeeList) {
        this.extraApplyFeeList = extraApplyFeeList;
    }

    public SpecialAgreement getSpecialAgreement() {
        return specialAgreement;
    }

    public List<ContractFile> getContractFileList() {
        return contractFileList;
    }

    public void setContractFileList(List<ContractFile> contractFileList) {
        this.contractFileList = contractFileList;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
