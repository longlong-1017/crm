package com.atlong.crm.commons.domain;

/**
 * @Author: YunLong
 * @Date: 2022/7/22 20:21
 */
public class ReturnObject {
    private String code;//处理成功获取失败的标记：1---成功,0---失败
    private String message;//提示信息
    private Object retData;

    public ReturnObject() {
    }

    public ReturnObject(String code, String message, Object retData) {
        this.code = code;
        this.message = message;
        this.retData = retData;
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

    public Object getRetData() {
        return retData;
    }

    public void setRetData(Object retData) {
        this.retData = retData;
    }
}
