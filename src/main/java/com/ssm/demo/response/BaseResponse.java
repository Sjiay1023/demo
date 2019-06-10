package com.ssm.demo.response;

/**
 * @author o_0sky
 * @date 2019/5/21 16:11
 */
public class BaseResponse<T> {
    private Integer code;
    private String msg;
    private T date;
    public BaseResponse(StatusCode statusCode,T date){
        this.code = statusCode.getCode();
        this.msg = statusCode.getMsg();
        this.date = date;
    }

    public BaseResponse(StatusCode statusCode){
        this.code = statusCode.getCode();
        this.msg = statusCode.getMsg();
    }

    public BaseResponse(Integer code,String msg,T date){
        this.code = code;
        this.msg = msg;
        this.date = date;
    }

    public BaseResponse(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getDate() {
        return date;
    }

    public void setDate(T date) {
        this.date = date;
    }
}
