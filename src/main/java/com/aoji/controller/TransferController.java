package com.aoji.controller;

import com.alibaba.fastjson.JSONObject;
import com.aoji.contants.Contants;
import com.aoji.contants.CountryGroup;
import com.aoji.contants.TransferRelatedEnum;
import com.aoji.model.*;
import com.aoji.service.*;
import com.aoji.vo.AllotVO;
import com.aoji.vo.TransferListVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * author: chenhaibo
 * description: 转案管理控制类
 * date: 2018/5/7
 */
@Controller
public class TransferController extends BaseController {

    @Autowired
    UserService userService;
    @Autowired
    TransferService transferService;
    @Autowired
    UserGroupService userGroupService;

    @Autowired
    RoleService roleService;
    @Autowired
    StudentService studentService;
    @Autowired
    CountryService countryService;
    @Autowired
    TransferSendService transferSendService;
    @Autowired
    TransferReceiveService transferReceiveService;

    public static final Logger logger = LoggerFactory.getLogger(TransferController.class);

    /**
     * 跳转转案管理列表页
     *
     * @return
     */
    @RequestMapping("/transfer/list")
    public String listPage(Model model) {
        SysUser sysUser = userService.getLoginUser();
        List<String> roles = roleService.getRolesByOaId(sysUser.getOaid());
        //查看用户属于文签还是外联
        if (roles.contains(Contants.ROLE_VISA_MANAGER) || roles.contains(Contants.ROLE_VISA_CONSULTANT)
                || roles.contains(Contants.ROLE_VISA_DIRECTOR)) {
            model.addAttribute("queryType", TransferRelatedEnum.TRAN_TYPE_VISA.getCode());
        } else if (roles.contains(Contants.ROLE_OUTREACH_MANAGER) || roles.contains(Contants.ROLE_OUTREACH_CONSULTANT)) {
            model.addAttribute("queryType", TransferRelatedEnum.TRAN_TYPE_OUTREACH.getCode());
        }

        // 查询当前用户的用户组、国家组
        List<UserGroup> userGroups = userGroupService.getUserGroupByoaidAndLeaderStatus(sysUser.getOaid(), null);
        model.addAttribute("userGroups", userGroups);

        boolean isChannel = false;
        if(!userGroups.isEmpty()){
            Set<Integer> countries = new HashSet<>();
            for(UserGroup userGroup : userGroups) {
                if (userGroup.getNation().equals(CountryGroup.GROUP_CHANNEL.getCode())) {
                    isChannel = true;
                }
                countries.add(userGroup.getNation());
            }
            Map<Integer, String> countryGroups = new HashMap<>();
            countries.forEach(country -> {
                countryGroups.put(country, CountryGroup.getNameByCode(country));
            });
            model.addAttribute("countryGroups", countryGroups);
        }
        model.addAttribute("isChannel", isChannel);

        return "/transferNew/list";
    }

    /**
     * 转案列表数据
     * @param pageParam
     * @param basePageModel
     * @return
     */
    @RequestMapping(value = "/transfer/listData", method = RequestMethod.POST)
    @ResponseBody
    public BasePageModel listData(TransferListVO transferListVO, PageParam pageParam, BasePageModel basePageModel) {

        this.checkPermission(transferListVO);

        Page<?> page = PageHelper.startPage(pageParam.getiDisplayStart() / pageParam.getiDisplayLength() + 1, pageParam.getiDisplayLength());
        List<TransferListVO> transferListVOList = null;
        if (TransferRelatedEnum.TRAN_TYPE_VISA.getCode().equals(transferListVO.getTransferType())){
            transferListVOList = transferService.transferListForVisa(transferListVO);
        } else if (TransferRelatedEnum.TRAN_TYPE_OUTREACH.getCode().equals(transferListVO.getTransferType())){
            transferListVOList = transferService.transferListForOutreach(transferListVO);
        } else {
            logger.error("transferType error!!");
        }

        return dataTableWapper(page, basePageModel);
    }

    /**
     * 校验权限
     * @param transferListVO
     */
    private void checkPermission(TransferListVO transferListVO){
        Subject currentUser = SecurityUtils.getSubject();
        if(currentUser != null){
            SysUser sysUser = (SysUser) currentUser.getPrincipal();
            if(currentUser.isPermitted(Contants.TRAN_CHANGE_GROUP_PERMISSION)){
                transferListVO.setUserGroup(null);
            }
            if(currentUser.isPermitted(Contants.TRAN_ASSIGN_PERMISSION)){
                transferListVO.setCanAssign(true);
            }else{
                transferListVO.setUserGroup(null);
            }
            transferListVO.setReceiveNo(sysUser.getOaid());
        }
    }

    /**
     * 跳转编辑页
     * @param sendId
     * @param model
     * @return
     */
    @RequestMapping("/transfer/edit")
    public String toEditPage(Integer sendId, String studentNo, Model model) {
        Subject currentUser = SecurityUtils.getSubject();

        model.addAttribute("studentNo", studentNo);
        model.addAttribute("sendId", sendId);
        Integer transferType = TransferRelatedEnum.TRAN_TYPE_VISA.getCode();
        if(currentUser.hasRole(Contants.ROLE_VISA_MANAGER) || currentUser.hasRole(Contants.ROLE_VISA_CONSULTANT)) {
            model.addAttribute("queryType", TransferRelatedEnum.TRAN_QUERY_TYPE_1.getCode());
        }else if(currentUser.hasRole(Contants.ROLE_OUTREACH_MANAGER) || currentUser.hasRole(Contants.ROLE_OUTREACH_CONSULTANT)){
            transferType = TransferRelatedEnum.TRAN_TYPE_OUTREACH.getCode();
            model.addAttribute("queryType", TransferRelatedEnum.TRAN_QUERY_TYPE_4.getCode());
        }
        TransferInfo transferInfo = transferService.getTransferInfoByStudentNo(studentNo, transferType);
        if(transferInfo != null){
            model.addAttribute("transferId", transferInfo.getId());
            model.addAttribute("userGroup", transferInfo.getUserGroup());
            model.addAttribute("countryGroup", transferInfo.getCountryGroup());
        }
        return "/transferNew/edit";
    }

    /**
     * 跳转转组页
     * @param sendId
     * @param model
     * @return
     */
    @RequestMapping("/transfer/editGroup")
    public String editGroup(Integer sendId, String studentNo, Integer type,  Model model) {
        model.addAttribute("sendId", sendId);
        model.addAttribute("studentNo", studentNo);
        if(TransferRelatedEnum.TRAN_SEND_TYPE_3.getCode().equals(type)){
            model.addAttribute("queryType", TransferRelatedEnum.TRAN_QUERY_TYPE_2.getCode());
        }else if(TransferRelatedEnum.TRAN_SEND_TYPE_4.getCode().equals(type)){
            model.addAttribute("queryType", TransferRelatedEnum.TRAN_QUERY_TYPE_3.getCode());
        }
        model.addAttribute("transferSendType", type);
        TransferInfo transferInfo = transferService.getTransferInfoByStudentNo(studentNo, TransferRelatedEnum.TRAN_TYPE_VISA.getCode());
        if(transferInfo != null){
            model.addAttribute("transferId", transferInfo.getId());
            model.addAttribute("userGroup", transferInfo.getUserGroup());
            model.addAttribute("countryGroup", transferInfo.getCountryGroup());
        }
        return "/transferNew/editGroup";
    }

    /**
     * 分配人员选择页
     *
     * @return
     */
    @RequestMapping("/transfer/staff")
    public String toStaffTreePage() {
        return "/transferNew/staffTree";
    }

    /**
     * 分配人员数据
     * @param countryGroup 国家组
     * @param userGroup 用户组
     * @param queryType 查询类型：分配，转组，转国家，外联
     * @return
     */
    @RequestMapping("/transfer/staffData")
    @ResponseBody
    public TreeItem staffTree(Integer countryGroup, Integer userGroup, Integer queryType) {

        Subject currentUser = SecurityUtils.getSubject();
        SysUser user = (SysUser)currentUser.getPrincipal();

        TreeItem treeItem = new TreeItem();

        List<JSONObject> dataList = new ArrayList<>();

        List<SysUser> sysUsers = new ArrayList<>();

        if (TransferRelatedEnum.TRAN_QUERY_TYPE_4.getCode().equals(queryType)) {
            sysUsers = userService.getByRoleName(Contants.ROLE_OUTREACH_CONSULTANT);
        } else {
            if(userGroup == null){
                List<UserGroup> userGroups = userGroupService.getUserGroupByoaidAndLeaderStatus(user.getOaid(), true);
                if(!userGroups.isEmpty()) {
                    userGroup = userGroups.get(0).getId();
                }
            }
            if(currentUser.isPermitted(Contants.TRAN_CHANGE_GROUP_PERMISSION)){
                userGroup = null;
            }
            if(TransferRelatedEnum.TRAN_QUERY_TYPE_3.getCode().equals(queryType)){
                //转国家，目前只支持美高美普互转
                if(CountryGroup.GROUP_AMERICA_H.getCode() == countryGroup){
                    sysUsers = userService.getByUserGroupAndCountry(userGroup, CountryGroup.GROUP_AMERICA_C.getCode(), true);
                }else if(CountryGroup.GROUP_AMERICA_C.getCode() == countryGroup){
                    sysUsers = userService.getByUserGroupAndCountry(userGroup, CountryGroup.GROUP_AMERICA_H.getCode(), true);
                }else{
                    sysUsers = userService.getByUserGroupAndCountry(null, null, true);
                }
            }else{
                sysUsers = userService.getByUserGroupAndCountry(userGroup, countryGroup, null);
            }
        }

        sysUsers.forEach(sysUser -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", sysUser.getOaid());
            jsonObject.put("name", sysUser.getUsername());
            jsonObject.put("isParent", false);
            dataList.add(jsonObject);
        });

        treeItem.setMsg(dataList.toString());
        return treeItem;
    }


    /**
     * 保存分配记录
     *
     * @param transferListVO
     * @return
     */
    @RequestMapping("/transfer/save")
    @ResponseBody
    public BaseResponse save(TransferListVO transferListVO, Integer queryType) {
        if(TransferRelatedEnum.TRAN_QUERY_TYPE_4.getCode().equals(queryType)){ // 外联
            return transferService.saveTransferForOutreach(transferListVO);
        }else{
            return transferService.saveTransferForVisa(transferListVO);
        }
    }

    /**
     * 修改接收状态
     *
     * @param id
     * @param confirmStatus
     * @return
     */
    @RequestMapping("/transfer/updateReceiveStatus")
    @ResponseBody
    public BaseResponse updateReceiveStatus(Integer id, Integer confirmStatus, String comment) {
        SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
        if(sysUser == null){
            BaseResponse response = new BaseResponse();
            response.setResult(false);
            response.setErrorMsg("会话超时，请重新登录！");
            return response;
        }
        return transferService.updateConfirmStatus(id, confirmStatus, comment, sysUser);
    }

    /**
     * 跳转编辑页
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/transfer/allot")
    public String toAllotPage(Integer id, String studentNo, Model model) {
        //根据登录人查找该组下面的文签顾问
        Subject currentUser = SecurityUtils.getSubject();
        SysUser user = (SysUser)currentUser.getPrincipal();
        Integer userGroup = null;
        Integer countryGroup = null;
        List<SysUser> sysUsers = new ArrayList<>();

        List<UserGroup> userGroups = userGroupService.getUserGroupByoaidAndLeaderStatus(user.getOaid(), true);
        if(!userGroups.isEmpty()) {
            userGroup = userGroups.get(0).getId();
            countryGroup = userGroups.get(0).getNation();
        }

        if(currentUser.isPermitted(Contants.TRAN_CHANGE_GROUP_PERMISSION)){
            userGroup = null;
        }
        sysUsers = userService.getByUserGroupAndCountry(userGroup, countryGroup, null);

        model.addAttribute("sysUsers",sysUsers);
        model.addAttribute("studentNo",studentNo);
        return "/transferNew/allotEdit";
    }
    @RequestMapping("/transfer/toAllot")
    @ResponseBody
    public Boolean saveAllot(AllotVO allotVO){
        return transferSendService.updateAllot(allotVO);
    }

    /**
     * 批量转案
     * @return
     */
    @RequestMapping("/transfer/batch")
    @ResponseBody
    public Boolean batch(TransferListVO transferListVO){
        return transferService.batchSaveTransferForVisa(transferListVO);
    }

    @RequestMapping("/transfer/batch/allot")
    public String toAllotPage(Model model){
        Subject currentUser = SecurityUtils.getSubject();
        SysUser sysUser = (SysUser) currentUser.getPrincipal();
        if(currentUser.hasRole(Contants.ROLE_VISA_MANAGER) || currentUser.hasRole(Contants.ROLE_VISA_CONSULTANT)) {
            model.addAttribute("queryType", TransferRelatedEnum.TRAN_QUERY_TYPE_1.getCode());
        }else if(currentUser.hasRole(Contants.ROLE_OUTREACH_MANAGER) || currentUser.hasRole(Contants.ROLE_OUTREACH_CONSULTANT)){
            model.addAttribute("queryType", TransferRelatedEnum.TRAN_QUERY_TYPE_4.getCode());
        }
        List<UserGroup> userGroups = userGroupService.getUserGroupByoaidAndLeaderStatus(sysUser.getOaid(), null);
        if(!userGroups.isEmpty()) {
            model.addAttribute("userGroup", userGroups.get(0).getId());
            model.addAttribute("countryGroup", userGroups.get(0).getNation());
        }
        return "/transferNew/allot";
    }
}
