package com.yc2;

public class jsonModel {
    private Integer code;
    private Object obj;
    private String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "jsonModel{" +
                "code=" + code +
                ", obj=" + obj +
                ", msg='" + msg + '\'' +
                '}';
    }
}
