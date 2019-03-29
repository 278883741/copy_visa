package com.aoji.controller;

import com.alibaba.fastjson.JSONObject;
import com.aoji.model.*;
import com.aoji.service.PermissionService;
import com.aoji.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class PermissionController {
    @Autowired
    PermissionService permissionService;
    @Autowired
    UserService userService;
    private Logger logger= LoggerFactory.getLogger(UserController.class);

    @RequestMapping("/permission")
    public String list(){
        return "permission/list";
    }

    @RequestMapping(value="permission_query",method = RequestMethod.GET)
    @ResponseBody
    public BasePageModel get(SysPermission sysPermission, PageParam pageParam, BasePageModel basePageModel){
        Page<?> page = PageHelper.startPage(pageParam.getiDisplayStart()/pageParam.getiDisplayLength()+1, pageParam.getiDisplayLength());
        String[] str = new String[]{"id","permission_name","level","operator","created"};
        permissionService.getList(sysPermission);
        //pageParam.setSortPro(str[pageParam.getiSortCol_0()]);
        basePageModel.setAaData(page);
        basePageModel.setiTotalDisplayRecords((int)page.getTotal());
        basePageModel.setiTotalRecords((int)page.getTotal());
        return basePageModel;
    }

    /**
     * 添加操作
     * @param model
     * @return
     */
    @RequestMapping(value="/permission/Action_add",method = RequestMethod.POST)
    @ResponseBody
    public Boolean Action_add(SysPermission model){
        SysUser sysUser = userService.getLoginUser();
        model.setCreated(new Date());
        model.setDeleted(null);
        model.setDeleteStatus(false);
        model.setOperator(sysUser.getOaid());//当前登录工号
        //没有父节点，默认最高级
        if (model.getParentId() == null){
            model.setLevel(Integer.valueOf(0));
        }
        return permissionService.addOne(model) > 0;
    }

    /**
     * 更新操作
     * @param model
     * @return
     */
    @RequestMapping(value="/permission/Action_edit",method = RequestMethod.POST)
    @ResponseBody
    public Boolean Action_edit(SysPermission model){
        if (model != null && model.getParentId() == null){
            model.setParentId(Integer.valueOf(0));
        }
        return permissionService.update(model) > 0;
    }

    /**
     * 删除操作
     * @param id
     * @return
     */
    @RequestMapping(value="/permission/Action_del",method = RequestMethod.POST)
    @ResponseBody
    public Integer Action_del(Integer id){
        return permissionService.deleteOne(id);
    }

    /**
     * 跳转到添加页
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/permission/add")
    public String toAddPage(Integer id, Model model){
        return "/permission/add";
    }

    /**
     * 跳转到编辑页
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/permission/edit")
    public String toEditPage(Integer id, Model model){
        SysPermission sysPermission = permissionService.getPermissionById(id);
        if(sysPermission != null && sysPermission.getParentId() != null){
            SysPermission parentPermission = permissionService.getPermissionById(sysPermission.getParentId());
            if(parentPermission != null){
                model.addAttribute("parentName", parentPermission.getPermissionName());
            }
        }
        model.addAttribute("permission", sysPermission);
        return "/permission/edit";
    }

    /**
     * 跳转到权限选择页
     * @return
     */
    @RequestMapping("/permission/tree")
    public String toPermissionTree(){
        return "/permission/tree";
    }

    /**
     * 权限管理 父级权限选择
     * @param parentId 父节点ID
     * @param permissionId 当前权限ID
     * @param level 级别
     * @return
     */
    @RequestMapping("/permission/treeData")
    @ResponseBody
    public TreeItem treeData(Integer parentId, Integer level, Integer permissionId){
        TreeItem treeItem=new TreeItem();

        List<JSONObject> dataList=new ArrayList<>();

        SysPermission permission = new SysPermission();
        permission.setParentId(parentId);
        permission.setLevel(level);
        if (parentId == null){
            permission.setLevel(Integer.valueOf(0));
        }
        //根据parentId查询权限列表
        List<SysPermission> sysPermissions = permissionService.get(permission);

        for (SysPermission sysPermission : sysPermissions) {
            // 添加操作时permissionId == null  修改时排除当前权限
            if(permissionId == null || !permissionId.equals(sysPermission.getId())){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", sysPermission.getId());
                jsonObject.put("name", sysPermission.getPermissionName());
                jsonObject.put("pId", sysPermission.getParentId());
                jsonObject.put("level", sysPermission.getLevel());
                jsonObject.put("isParent", true);
                dataList.add(jsonObject);
            }
        }

        treeItem.setMsg(dataList.toString());
        return treeItem;
    }
}
