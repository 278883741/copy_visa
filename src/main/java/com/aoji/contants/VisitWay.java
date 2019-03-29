package com.aoji.contants;

import java.util.HashMap;

/**
 * @author 赵剑飞
 * @description 回访途径
 * @date Created in 下午2:43 2017/12/12
 */
public class VisitWay {
    public final static HashMap<Integer, String> map = new HashMap<Integer, String>();
    public static HashMap<Integer, String> get(){
        if(map.size() == 0){
            map.put(1, "传真");
            map.put(2, "邮件");
            map.put(3, "电话");
        }
        return map;
    }
}
