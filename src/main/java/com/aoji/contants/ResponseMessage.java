package com.aoji.contants;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * @author yangsaixing
 * @description  响应体信息
 * @date Created in 下午4:10 2017/10/10
 */
public class ResponseMessage implements Serializable {

    private static final long serialVersionUID = -588970587102075140L;

    /**响应头**/
    private  Head head=new Head();
    /**响应体**/
    private JSONObject body=new JSONObject();

    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    public JSONObject getBody() {
        return body;
    }

    public void setBody(JSONObject body) {
        this.body = body;
    }
    public ResponseMessage(){
        body.put("code",0);
        body.put("message","操作成功");
    }
    @Override
    public String toString() {
        return "ResponseBody [head=" + head + ", body=" + body + "]";
    }

    /**响应头信息**/
    public class Head{
        /**响应代码**/
        private Integer code=0;
        /**响应消息**/
        private String message="操作成功";

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
        @Override
        public String toString() {
            return "head [code=" + code + ", message=" + message + "]";
        }
    }
}
