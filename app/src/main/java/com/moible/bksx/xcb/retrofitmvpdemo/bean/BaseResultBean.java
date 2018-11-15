package com.moible.bksx.xcb.retrofitmvpdemo.bean;

public class BaseResultBean {
    protected int code;
    protected String msg;
    public BaseResultBean() {
    }
    public BaseResultBean(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
