package com.aoji.contants;
/**
 * @author yangsaixing
 * @description  异常信息类
 * @date Created in 下午4:11 2017/10/10
 */
public class ContentException extends RuntimeException{

    private Integer code;

    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private  ContentException(){}

    public ContentException(Integer code, String message) {
        super(String.format("code=[%d],message=[%s]", code, message));
        this.code = code;
        this.message = message;
    }
}
