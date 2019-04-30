package com.feicent.zhang.base.enums;

/**
 * @desc: RPC调用状态
 */
public enum RpcState {
    SUCCESS(200, "调用成功"),
    FAILURE(400, "调用失败"),
    ERROR(500,   "调用错误");

    private int    code; //状态值
    private String desc; //状态描述

    RpcState(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
