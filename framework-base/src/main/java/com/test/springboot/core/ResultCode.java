package com.test.springboot.core;


import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.Serializable;

@JsonInclude(Include.NON_NULL)
public class ResultCode implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = LogManager.getLogger(ResultCode.class);

    private String code;
    private String msg;
    private Object data;

    public ResultCode() {
    }

    public ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
        logger.info("返回数据，code:" + code + ",msg:" + msg);
    }

    public ResultCode(String code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        if (data == null) {
            data = new Object();
        }
        this.data = data;
        logger.info("返回数据，code:" + code + ",msg:" + msg + ",data:" + JSON.toJSONString(data));
    }

/*    public ResultCode(StatusCode statusCode) {
        this.code = statusCode.getCode();
        this.msg = statusCode.getMsg();
        logger.info("返回数据，code:" + code + ",msg:" + msg);
    }

    public ResultCode(StatusCode statusCode, Object o) {
        this.code = statusCode.getCode();
        this.msg = statusCode.getMsg();
        if(o == null){
            o = new Object();
        }
        this.data = o;
        logger.info("返回数据，code:" + code + ",msg:" + msg + ",data:" + JSON.toJSONString(data));
    }*/

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}