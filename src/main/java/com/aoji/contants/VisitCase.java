package com.aoji.contants;

import java.util.HashMap;

/**
 * @author 赵剑飞
 * @description 回访场景
 * @date Created in 下午2:43 2017/12/12
 */
public class VisitCase {
    public final static HashMap<Integer, String> map = new HashMap<Integer, String>();
    public static HashMap<Integer, String> get(){
        if(map.size() == 0){
            map.put(1, "文案回访");
            map.put(2, "外联学校申请");
            map.put(3, "后续回访");
        }
        return map;
    }
}
