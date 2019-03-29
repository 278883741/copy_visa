package com.aoji.contants;

import java.util.HashMap;

/**
 * @author 赵剑飞
 * @description 机构类型
 * @date Created in 下午2:43 2017/12/12
 */
public class AgencyType {
    public final static HashMap<Integer, String> map = new HashMap<Integer, String>();
    public static HashMap<Integer, String> get(){
        if(map.size() == 0){
            map.put(1, "接机");
            map.put(2, "保险");
            map.put(3, "监护");
            map.put(4, "住宿");
            map.put(5, "院校");
        }
        return map;
    }
}
