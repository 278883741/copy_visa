package com.aoji.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aoji.service.CountryAddress;
import com.aoji.utils.HttpUtils;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
@Service
public class CountryAddressImpl implements CountryAddress{

    //调取国内地址（pdf导出）
    @Value("${student.detail.url}")
    private String studentDetailUrl;

    @Override
    public Map<String, Object> getCountryAddress(String studentNo) {
        Map<String, Object> data4 = new HashMap<String, Object>();
        String json = HttpUtils.doGet(MessageFormat.format(studentDetailUrl, studentNo));
        JSONObject jsonObject = JSON.parseObject(json);
        String address = jsonObject.getString("data");
        JSONObject jsondata= JSON.parseObject(address);
        String token = jsondata.getString("address");
        if (token != null ) {
            data4.put("cost24",token);//国内地址中文0
            //国内地址中文转拼音

            HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
            // UPPERCASE：大写  (ZHONG)
            // LOWERCASE：小写  (zhong)
            format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
            // WITHOUT_TONE：无音标  (zhong)
            // WITH_TONE_NUMBER：1-4数字表示英标  (zhong4)
            // WITH_TONE_MARK：直接用音标符（必须WITH_U_UNICODE否则异常）  (zhòng)
            format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
            // WITH_V：用v表示ü  (nv)
            // WITH_U_AND_COLON：用"u:"表示ü  (nu:)
            // WITH_U_UNICODE：直接用ü (nü)
            format.setVCharType(HanyuPinyinVCharType.WITH_V);
            char[] input = token.trim().toCharArray();
            String output = "";
            try {
                for (int i = 0; i < input.length; i++) {
                    if (Character.toString(input[i]).matches("[\\u4E00-\\u9FA5]+")) {  //判断字符是否是中文
                        //toHanyuPinyinStringArray 如果传入的不是汉字，就不能转换成拼音，那么直接返回null
                        //由于中文有很多是多音字，所以这些字会有多个String，在这里我们默认的选择第一个作为pinyin
                        String[] temp = PinyinHelper.toHanyuPinyinStringArray(input[i], format);
                        output += temp[0];
                    } else {
                        output += Character.toString(input[i]);
                    }
                }
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                e.printStackTrace();
            }

            data4.put("cost25",output);


        }else{
            data4.put("cost24","");
            data4.put("cost25","");
        }

        return data4;
    }
}
