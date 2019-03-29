package com.aoji.controller;

import com.alibaba.fastjson.JSONObject;
import com.aoji.contants.Contants;
import com.aoji.model.*;
import com.aoji.model.dto.UserInfoDTO;
import com.aoji.service.*;
import com.aoji.vo.AllotVO;
import com.aoji.vo.SaveTransferVO;
import com.aoji.vo.TransferVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * author: chenhaibo
 * description: 转案管理控制类
 * date: 2018/1/3
 */
//@Controller
public class TransferSendController extends BaseController {

    @Autowired
    UserService userService;
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

    public static final Logger logger = LoggerFactory.getLogger(TransferSendController.class);

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
        if (roles.contains(Contants.ROLE_VISA_MANAGER) || roles.contains(Contants.ROLE_VISA_CONSULTANT)) {
            model.addAttribute("queryType", Contants.TRANSFER_TYPE_VISA);
            //查询当前用户角色和组，判断是否拥有分配权限
            UserInfoDTO userInfoDTO = userService.getUserInfoDTOByOaid(sysUser.getOaid());
            if (userInfoDTO != null) {
                if (userInfoDTO.getLeaderStatus() && Contants.ROLE_VISA_MANAGER.equals(userInfoDTO.getRoleName())) {
                    model.addAttribute("canAssign", true);
                }
            }
        } else if (roles.contains(Contants.ROLE_OUTREACH_MANAGER) || roles.contains(Contants.ROLE_OUTREACH_CONSULTANT)) {
            model.addAttribute("queryType", Contants.TRANSFER_TYPE_OUT);
            // 是否为外联经理
            if (roles.contains(Contants.ROLE_OUTREACH_MANAGER)) {
                model.addAttribute("canAssign", true);
            }
        }
        model.addAttribute("currentUser", sysUser.getOaid());

        return "/transfer/list";
    }

    /**
     * 转案列表数据
     *
     * @param pageParam
     * @param basePageModel
     * @return
     */
    @RequestMapping("/transfer/listData")
    @ResponseBody
    public BasePageModel listData(TransferVO transferVO, PageParam pageParam, BasePageModel basePageModel, Model model) {

        SysUser sysUser = userService.getLoginUser();

        // 如果是经理，显示接收人或操作人为该用户； 如果是文签顾问，显示接收人为该用户.
        List<String> roles = roleService.getRolesByOaId(sysUser.getOaid());
        if (roles.contains(Contants.ROLE_VISA_MANAGER) || roles.contains(Contants.ROLE_OUTREACH_MANAGER)) {
            transferVO.setReceiverNo(sysUser.getOaid());
            transferVO.setOperatorNo(sysUser.getOaid());
            transferVO.setManager(Contants.YES_FLAG);
        } else if (roles.contains(Contants.ROLE_VISA_CONSULTANT) || roles.contains(Contants.ROLE_OUTREACH_CONSULTANT)) {
            transferVO.setReceiverNo(sysUser.getOaid());
            transferVO.setManager(Contants.NO_FLAG);
        } else {
            logger.info("role error!!");
            return dataTableWapper(new Page<>(), basePageModel);
        }
        //查询其他组长
        List<SysUser> otherLeaders = userService.getOtherLeaders(sysUser.getOaid());
        int transferVOCount = transferSendService.transferInfoRelatedQueryCount(transferVO);
        Page<?> page = PageHelper.startPage(pageParam.getiDisplayStart() / pageParam.getiDisplayLength() + 1, pageParam.getiDisplayLength(), false);
        List<TransferVO> transferVOS = transferSendService.transferInfoRelatedQuery(transferVO);
        for (TransferVO transfer : transferVOS) {
            for (SysUser user : otherLeaders) {
                if (user.getOaid().equals(transfer.getReceiverNo())) {
                    transfer.setChangeGroup(true);
                }
            }
        }
        page.setTotal(transferVOCount);
        return dataTableWapper(page, basePageModel);
    }


    /**
     * 转案列表数据
     *
     * @param pageParam
     * @param basePageModel
     * @return
     */
    @RequestMapping(value = "/transfer/listData", method = RequestMethod.POST)
    @ResponseBody
    public BasePageModel transferlistByPage(TransferVO transferVO, PageParam pageParam, BasePageModel basePageModel, Model model) {

        int i = this.pageListPrepare(transferVO);
        if (i == 0) {
            return dataTableWapper(new Page(), basePageModel);
        }

        int transferVOCount = transferSendService.transferInfoRelatedQueryCount(transferVO);
        Page<?> page = pageWapper3(pageParam, propList());

        List<TransferVO> transferVOS = this.transferSendService.transferlistByPage(transferVO);
        page.setTotal(transferVOCount);
        return dataTableWapper(page, basePageModel);
    }

    
    /**
     * 分页准备数据
     * 去掉了运行时判断当前用户非空的判断，如果用户为空，shiro会拦截跳转
     */

    private int pageListPrepare(TransferVO transferVO) {

        SysUser sysUser = userService.getLoginUser();

        // 如果是经理，显示接收人或操作人为该用户； 如果是文签顾问，显示接收人为该用户.
        List<String> roles = roleService.getRolesByOaId(sysUser.getOaid());

        if (roles.contains(Contants.ROLE_VISA_MANAGER) || roles.contains(Contants.ROLE_OUTREACH_MANAGER)) {

            transferVO.setReceiverNo(sysUser.getOaid());

            transferVO.setOperatorNo(sysUser.getOaid());

            transferVO.setManager(Contants.YES_FLAG);

        } else if (roles.contains(Contants.ROLE_VISA_CONSULTANT) || roles.contains(Contants.ROLE_OUTREACH_CONSULTANT)) {

            transferVO.setReceiverNo(sysUser.getOaid());

            transferVO.setManager(Contants.NO_FLAG);

        } else {
            logger.info("当前用户权限错误,用户工号：  " + sysUser.getOaid() + ",用户名称：  " + sysUser.getUsername());
            return 0;
        }
        return 1;
    }

    public String[] propList() {
        return new String[]{""};
    }



    /**
     * 跳转编辑页
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/transfer/edit")
    public String toEditPage(Integer id, String studentNo, Model model) {
        TransferSendInfo transferSendInfo = transferSendService.getTransferSendById(id);
        SysUser sysUser = userService.getLoginUser();
        TransferVO transferVO = new TransferVO();
        transferVO.setStudentNo(studentNo);
        transferVO.setQueryType(Contants.TRANSFER_TYPE_VISA);
        transferVO.setManager(Contants.YES_FLAG);
        transferVO.setOperatorNo(sysUser.getOaid());
        List<TransferVO> transferVOS = transferSendService.transferInfoRelatedQuery(transferVO);
        // 判断是文签还是外联
        if (transferSendInfo.getApplyId() != null) {
            model.addAttribute("queryType", Contants.TRANSFER_TYPE_OUT);
        } else {
            model.addAttribute("queryType", Contants.TRANSFER_TYPE_VISA);
        }

        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setStudentNo(studentNo);
        StudentInfo student = studentService.get(studentInfo);
        //是否为两个顾问的国家
        if (student != null && StringUtils.hasText(student.getNationName())) {
            if (Contants.TWO_RECEIVE.contains(student.getNationId())) {
                model.addAttribute("receiveNum", 2);
            }
        }
        //查询留学国家
        CountryInfo countryInfo = new CountryInfo();
        countryInfo.setId(student.getNationId());
        List<CountryInfo> countryInfos = countryService.getList(countryInfo);
        if (!countryInfos.isEmpty()) {
            model.addAttribute("nation", countryInfos.get(0).getCountryBussid());
        }
        model.addAttribute("transferSend", transferSendInfo);

        transferVOS.forEach(transferVO1 -> {
            if (transferVO1.isCopyOperator()) {
                model.addAttribute("transfer1", transferVO1);
            } else {
                model.addAttribute("transfer2", transferVO1);
            }
        });
        model.addAttribute("sysUser", sysUser);

        return "/transfer/edit";
    }

    /**
     * 跳转转组页
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/transfer/editGroup")
    public String toEditGroupPage(Integer id, Model model) {
        TransferSendInfo transferSendInfo = transferSendService.getTransferSendById(id);
        // 判断是文签还是外联
        if (transferSendInfo.getStudentNo() == null && transferSendInfo.getApplyId() != null) {
            model.addAttribute("queryType", Contants.TRANSFER_TYPE_OUT);
        } else if (transferSendInfo.getStudentNo() != null && transferSendInfo.getApplyId() == null) {
            model.addAttribute("queryType", Contants.TRANSFER_TYPE_VISA);
        }

        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setStudentNo(transferSendInfo.getStudentNo());
        StudentInfo student = studentService.get(studentInfo);
        //是否为两个顾问的国家
        if (student != null && StringUtils.hasText(student.getNationName())) {
            if (Contants.TWO_RECEIVE.contains(student.getNationId())) {
                model.addAttribute("receiveNum", 2);
            }
        }
        //查询留学国家
        CountryInfo countryInfo = new CountryInfo();
        countryInfo.setId(student.getNationId());
        List<CountryInfo> countryInfos = countryService.getList(countryInfo);
        if (!countryInfos.isEmpty()) {
            model.addAttribute("nation", countryInfos.get(0).getCountryBussid());
        }
        model.addAttribute("transferSend", transferSendInfo);
        return "/transfer/editGroup";
    }

    /**
     * 分配人员选择页
     *
     * @return
     */
    @RequestMapping("/transfer/staff")
    public String toStaffTreePage() {
        return "/transfer/staffTree";
    }

    /**
     * 转组选择页
     *
     * @return
     */
    @RequestMapping("/transfer/groupLeader")
    public String toGroupLeaderTreePage() {
        return "/transfer/groupLeaderTree";
    }


    /**
     * 分配人员数据
     *
     * @param parentId      父节点
     * @param countryBussId 国家对应的内网ID
     * @param queryType     查询类型：文签/外联
     * @return
     */
    @RequestMapping("/transfer/staffData")
    @ResponseBody
    public TreeItem staffTree(Integer parentId, Integer countryBussId, String queryType) {
        TreeItem treeItem = new TreeItem();

        List<JSONObject> dataList = new ArrayList<>();

        List<SysUser> sysUsers = new ArrayList<>();
        if (Contants.TRANSFER_TYPE_VISA.equals(queryType)) {
            sysUsers = userService.getByLeaderOaid(userService.getLoginUser().getOaid());
            sysUsers.add(userService.getLoginUser());
        } else if (Contants.TRANSFER_TYPE_OUT.equals(queryType)) {
            sysUsers = userService.getByRoleName(Contants.ROLE_OUTREACH_CONSULTANT);
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
     * 转组数据
     *
     * @param parentId      父节点
     * @param countryBussId 国家对应的内网ID
     * @param queryType     查询类型：文签/外联
     * @return
     */
    @RequestMapping("/transfer/groupLeaderData")
    @ResponseBody
    public TreeItem groupLeaderTree(Integer parentId, Integer countryBussId, String queryType) {
        TreeItem treeItem = new TreeItem();

        List<JSONObject> dataList = new ArrayList<>();

        List<SysUser> sysUsersLeader = new ArrayList<>();
        if (Contants.TRANSFER_TYPE_VISA.equals(queryType)) {
            sysUsersLeader = userService.getOtherLeaders(userService.getLoginUser().getOaid());
        }

        sysUsersLeader.forEach(sul -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", sul.getOaid());
            jsonObject.put("name", sul.getUsername());
            jsonObject.put("isParent", false);
            dataList.add(jsonObject);
        });

        treeItem.setMsg(dataList.toString());
        return treeItem;
    }

    /**
     * 保存分配记录
     *
     * @param saveTransferVO
     * @return
     */
    @RequestMapping("/transfer/save")
    @ResponseBody
    public Boolean save(SaveTransferVO saveTransferVO) {
        return transferSendService.saveTransfer(saveTransferVO);
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
    public Boolean updateReceiveStatus(Integer id, Integer confirmStatus, String comment) {
        return transferReceiveService.insert(id, confirmStatus, comment);
    }
}
