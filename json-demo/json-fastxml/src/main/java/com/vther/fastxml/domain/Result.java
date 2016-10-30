package com.vther.fastxml.domain;

/**
 * Created by Wither on 2016/10/30.
 */
public class Result<T> {
    private int code;
    private String msg;
    T resultData;

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

    public T getResultData() {
        return resultData;
    }

    public void setResultData(T resultData) {
        this.resultData = resultData;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", resultData=" + resultData +
                '}';
    }
}
