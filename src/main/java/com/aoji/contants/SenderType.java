package com.aoji.contants;

import java.util.HashMap;

/**
 * @author 赵剑飞
 * @description 发送方
 * @date Created in 下午2:43 2017/12/12
 */
public class SenderType {
    public final static HashMap<Integer, String> map = new HashMap<Integer, String>();
    public static HashMap<Integer, String> get(){
        if(map.size() == 0){
            map.put(1, "公司");
            map.put(2, "学校");
        }
        return map;
    }
}
