package com.aoji.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    /**
     *
     * @param o1 String or Date 格式时间
     * @param o2 String or Date 格式时间
     * @param pattern1 example = "yyyy-MM-dd HH:mm:ss"
     * @param pattern2 example = "yyyy-MM-dd HH:mm:ss"
     * @return
     */
    public static final boolean compareTo(Object o1,Object o2,String pattern1,String pattern2){
        return getResult(o1, o2, pattern1,pattern2);
    }

    /**
     *
     * @param o1 String or Date 格式时间
     * @param o2 String or Date 格式时间
     * @param pattern1 example = "yyyy-MM-dd HH:mm:ss"(o1或者o2的时间格式)
     * @return
     */
    public static final boolean compareTo(Object o1,Object o2,String pattern1){
        boolean a=getResult(o1, o2, pattern1,null);
        return getResult(o1, o2, pattern1,null);
    }
    /**
     *
     * @param o1 String or Date 格式时间
     * @param o2 String or Date 格式时间
     * 默认时间格式 example = "yyyy-MM-dd HH:mm:ss"
     * @return
     */
    public static final boolean compareTo(Object o1,Object o2){
        return getResult(o1, o2, null,null);
    }

    private static boolean getResult(Object o1, Object o2, String pattern1,String pattern2){
        Date d1=null,d2=null;
        if(pattern1==null&& pattern2==null){
            pattern1="yyyy-MM-dd HH:mm:ss";
        }
        if(pattern2==null){
            pattern2=pattern1;
        }
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(getString(pattern1));
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(getString(pattern2));
        try {
            if(o1 instanceof String){
                d1=simpleDateFormat1.parse(getString(o1.toString()));
            }else if(o1 instanceof  Date){
                d1=new Date(o1.toString());
            }else {
                d1=null;
            }

            if(o2 instanceof String){
                d2=simpleDateFormat2.parse(getString(o2.toString()));
            }else if(o2 instanceof  Date){
                d2=new Date(o2.toString());
            }else {
                d2=null;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if(d1==null || d2==null){
                return false;
            }
            return d1.compareTo(d2)>=0;
        }
    }

    private static String getString(String s){
        return s.replaceAll("/","-");
    }

    /**
     * Date日期改变天数
     */
    public static Date changeDayForDate(Date date, int days) {
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, days);
            return calendar.getTime();
        }
        return null;
    }
}
