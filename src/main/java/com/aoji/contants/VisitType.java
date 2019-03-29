package com.aoji.contants;

import java.util.HashMap;

/**
 * @author 赵剑飞
 * @description 回访类型
 * @date Created in 下午2:43 2017/12/12
 */
public class VisitType {
    public final static HashMap<Integer, String> map = new HashMap<Integer, String>();
    public static HashMap<Integer, String> get(){
        if(map.size() == 0){
            map.put(1, "收材料");
            map.put(2, "要求补件");
            map.put(3, "确认补件");
            map.put(4, "跟催");
            map.put(5, "其他");
        }
        return map;
    }
}
