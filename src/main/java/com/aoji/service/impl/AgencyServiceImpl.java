package com.aoji.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aoji.contants.AgencyType;
import com.aoji.contants.Contants;
import com.aoji.contants.CountryGroup;
import com.aoji.mapper.AgencyInfoMapper;
import com.aoji.model.AgencyInfo;
import com.aoji.model.CountryInfo;
import com.aoji.model.TreeItem;
import com.aoji.service.AgencyService;
import com.aoji.service.CountryService;
import com.aoji.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author zhaojianfei
 * @description 合作机构信息
 * @date Created in 下午2:31 2017/12/7
 */
@Service
public class AgencyServiceImpl implements AgencyService {
    @Autowired
    AgencyInfoMapper agencyInfoMapper;
    @Autowired
    CountryService countryService;
    @Autowired
    UserService userService;
    @Override
    public List<AgencyInfo> get(AgencyInfo agencyInfo) {
        agencyInfo.setDeleteStatus(false);
        List<AgencyInfo> list= agencyInfoMapper.get(agencyInfo);
        for(AgencyInfo item:list){
            item.setOperatorName(userService.getUserByName(item.getOperatorNo()).getUsername());
            item.setCreateTime_string(new SimpleDateFormat(Contants.datePattern).format(item.getCreateTime()));
            if(item.getAgencyType() != null){
                item.setAgencyType_string(AgencyServiceImpl.GetAgencyType(item.getAgencyType()));
            }
            String[] countries = item.getNationId().split(",");
            StringBuilder sb = new StringBuilder("");
            for (String country : countries) {
                CountryInfo countryInfo = new CountryInfo();
                countryInfo.setId(Integer.valueOf(country));
                countryInfo = countryService.getList(countryInfo).get(0);
                sb.append(countryInfo.getCountryName() + ",");
            }
            String nationNameMore = sb.toString();
            item.setNationNameMore(nationNameMore.substring(0, nationNameMore.length() - 1));
            if (nationNameMore.length() <= 10) {
                item.setNationName(nationNameMore.contains(",")?nationNameMore.substring(0,nationNameMore.length() -1):nationNameMore);
            } else {
                item.setNationName(nationNameMore.substring(0, 10) + "...");
            }
        }

        //按国家筛选
        if(agencyInfo.getNationId() != null){
            List<AgencyInfo> agencyInfos = new ArrayList<>();
            list.forEach(agency -> {
                if(agency.getNationId() != null){
                    String[] nationArray = agency.getNationId().split(",");
                    List<String> nations = Arrays.asList(nationArray);
                    if(nations.contains(agencyInfo.getNationId())){
                        agencyInfos.add(agency);
                    }
                }
            });
            return agencyInfos;
        }
        return list;
    }

    @Override
    public AgencyInfo getById(Integer id) {
        AgencyInfo agencyInfo = new AgencyInfo();
        agencyInfo.setId(id);
        agencyInfo.setDeleteStatus(false);
        List<AgencyInfo> list = agencyInfoMapper.select(agencyInfo);
        AgencyInfo model = null;
        if(list.size() > 0){
            model = list.get(0);
        }
        model.setCreateTime_string(new SimpleDateFormat(Contants.timePattern).format(model.getCreateTime()));
        model.setAgencyType_string(AgencyServiceImpl.GetAgencyType(model.getAgencyType()));
        return model;
    }

    @Override
    public Integer update(AgencyInfo agencyInfo) {
        return agencyInfoMapper.updateByPrimaryKeySelective(agencyInfo);
    }

    @Override
    public Integer delete(Integer id){
        AgencyInfo agencyInfo = new AgencyInfo();
        agencyInfo.setId(id);
        agencyInfo.setDeleteStatus(true);
        return agencyInfoMapper.updateByPrimaryKey(agencyInfo);
    }

    @Override
    public Integer add(AgencyInfo agencyInfo){
        return agencyInfoMapper.insertSelective(agencyInfo);
    }

    @JsonView
    public static void main(String[] args) {

    }
    public static String GetAgencyType(int key){
        return AgencyType.get().get(key);
    }

    @Override
    public TreeItem getCountry(Integer parentId){
        HashMap<Integer, String> map = CountryGroup.get();
        ArrayList<JSONObject> dataList = new ArrayList<JSONObject>();
        if(parentId == null) {
            for (Integer key : map.keySet()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", key);
                jsonObject.put("name", map.get(key));
                jsonObject.put("pId", null);
                jsonObject.put("checked", false);
                jsonObject.put("isParent", true);
                jsonObject.put("chkDisabled", true);
                dataList.add(jsonObject);
            }
        }
        else{
            CountryInfo countryInfo = new CountryInfo();
            countryInfo.setCountryGroup(parentId);
            List<CountryInfo> list = countryService.getList(countryInfo);
            for (CountryInfo item : list) {
                JSONObject jsonObject_child = new JSONObject();
                jsonObject_child.put("id", item.getId());
                jsonObject_child.put("name", item.getCountryName());
                jsonObject_child.put("pId", parentId);
                jsonObject_child.put("checked", false);
                jsonObject_child.put("isParent", false);
                dataList.add(jsonObject_child);
            }
        }
        TreeItem treeItem = new TreeItem();
        treeItem.setMsg(dataList.toString());
        return treeItem;
    }

    @Override
    public TreeItem getCountryEdit(String checkdCountry,String action){
        String[] countries = checkdCountry.split(",");
        ArrayList<JSONObject> dataList = new ArrayList<JSONObject>();
        HashMap<Integer, String> map = CountryGroup.get();
        for (Integer key : map.keySet()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", key);
            jsonObject.put("name", map.get(key));
            jsonObject.put("pId", null);
            jsonObject.put("checked", false);
            jsonObject.put("isParent", true);
            jsonObject.put("chkDisabled", true);
            jsonObject.put("open", true);
            dataList.add(jsonObject);

            CountryInfo countryInfo = new CountryInfo();
            countryInfo.setCountryGroup(key);
            List<CountryInfo> list = countryService.getList(countryInfo);
            for (CountryInfo item : list) {
                JSONObject jsonObject_child = new JSONObject();
                jsonObject_child.put("id", item.getId());
                jsonObject_child.put("name", item.getCountryName());
                jsonObject_child.put("pId", key);
                for (String country:countries) {
                    if(action.equals("detail")){
                        jsonObject_child.put("chkDisabled",true);
                    }
                    if (jsonObject_child.get("id").toString().equals(country)) {
                        jsonObject_child.put("checked", true);
                        break;
                    }
                    else{
                        jsonObject_child.put("checked", false);
                    }
                }
                jsonObject_child.put("isParent", false);
                dataList.add(jsonObject_child);
            }
        }
        TreeItem treeItem = new TreeItem();
        treeItem.setMsg(dataList.toString());
        return treeItem;
    }
}
