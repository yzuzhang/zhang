package com.feicent.zhang.base.exception;

/**
 * @author: yzuzhang
 * @date: 2019/4/30 16:31
 * @desc: 自定义-业务异常
 */
public class RpcException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String msg;
    private int code = 500;

    public RpcException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public RpcException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public RpcException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public RpcException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}

