package com.aoji.model;

import com.aoji.vo.StudentVO;

import java.util.List;

/**
 * @author wangjunqiang
 * @description
 * @date Created in 2018/6/14 14:32
 */
public class ResponseJson {

    /**
     * 成功失败标识(0-成功,-1失败)
     */
    public String code;

    /**
     * 返回描述
     */
    public String message;

    /**
     * 返回数据
     */
    public String date;

    /**
     * 数据详细说明
     * @return
     */
    public List<StudentVO> dateDetail;

    public List<StudentVO> getDateDetail() {
        return dateDetail;
    }

    public void setDateDetail(List<StudentVO> dateDetail) {
        this.dateDetail = dateDetail;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ResponseJson{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
