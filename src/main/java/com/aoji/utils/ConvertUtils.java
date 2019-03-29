package com.aoji.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * author: chenhaibo
 * description: 常用数据类型转换
 * date: 2018/10/17
 */
public class ConvertUtils {

    /**
     * 字符串转 List<Integer>
     * @param schoolIdStr
     * @param split
     * @return
     */
    public static List<Integer> strToList(String schoolIdStr, String split){
        String[] strArray = schoolIdStr.split(split);
        List<Integer> list = new ArrayList<>();
        for(int i=0; i<strArray.length; i++){
            list.add(Integer.valueOf(strArray[i]));
        }
        return list;
    }

    /**
     * 汉字转为拼音
     * @param chinese
     * @return
     */
    public static String ToPinyin(String chinese){
        String pinyinStr = "";
        char[] newChar = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < newChar.length; i++) {
            if (newChar[i] > 128) {
                try {
                    pinyinStr += PinyinHelper.toHanyuPinyinStringArray(newChar[i], defaultFormat)[0];
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            }else{
                pinyinStr += newChar[i];
            }
        }
        return pinyinStr;
    }

    /**
     * 隐藏手机号
     * @param tel
     * @return
     */
    public static String hideTel(String tel){
        if(StringUtils.hasText(tel) && tel.length() > 10){
            StringBuffer sb = new StringBuffer();
            sb.append(tel.substring(0, 3));
            sb.append("****");
            sb.append(tel.substring(7));
            return sb.toString();
        }
        return tel;
    }
}
