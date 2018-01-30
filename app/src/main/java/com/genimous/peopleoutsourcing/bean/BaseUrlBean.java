package com.genimous.peopleoutsourcing.bean;

/**
 * Created by wudi on 18/1/29.
 */

public class BaseUrlBean <T> {

    int code;

    String status;

    T msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getMsg() {
        return msg;
    }

    public void setMsg(T msg) {
        this.msg = msg;
    }


}
