package com.feicent.zhang.base;

import com.feicent.zhang.base.enums.RpcState;
import com.feicent.zhang.base.exception.RpcException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @desc: RPC远程调用结果封装
 */
public class RpcResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private RpcState          state;                // 状态枚举
    private String            result;               // 结果内容
    private T                 data;                 // 业务数据
    private Map<String, Object> extraData;          // 扩展参数

    /**
     * 获取RPC调用状态
     * @return RpcState
     */
    public RpcState getState() {
        return state;
    }

    public void setState(RpcState state) {
        this.state = state;
    }
    /**
     * 获取调用结果消息
     * @return String
     */
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    /**
     * 获取返回的数据内容
     * @return T
     */
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Map<String, Object> getExtraData() {
        return extraData;
    }

    public void setExtraData(Map<String, Object> extraData) {
        this.extraData = extraData;
    }

    public void addExtraData(String key, Object value) {
        if (this.extraData == null) {
            this.extraData = new HashMap<String, Object>();
        }
        this.extraData.put(key, value);
    }



    /**
     *
     * @param data
     * @return T
     */
    public RpcResult<T> success(T data) {
        return success(null, data);
    }

    /**
     *
     * @param result
     * @param data
     * @return T
     */
    public RpcResult<T> success(String result, T data) {
        return changeAttrs(RpcState.SUCCESS, result, data);
    }

    /**
     *
     * @param rpcEx
     * @return T
     */
    public RpcResult<T> failure(RpcException rpcEx) {
        return failure(rpcEx.getMessage());
    }

    /**
     *
     * @param result
     * @return T
     */
    public RpcResult<T> failure(String result) {
        return changeAttrs(RpcState.FAILURE, result, null);
    }

    /**
     *
     * @param throwable
     * @return RpcResult
     */
    public RpcResult<T> error(Throwable throwable) {
        try {
            // 不抛详细异常到对方服务
            return error(throwable.toString());
        } catch (Exception e) {
            return error(throwable.getMessage());
        }
    }

    /**
     *
     * @param result
     * @return E
     */
    public RpcResult<T> error(String result) {
        return changeAttrs(RpcState.ERROR, result, null);
    }

    /**
     * 改变当前对象的值
     * @param state
     * @param result
     * @param data
     */
    private RpcResult<T> changeAttrs(RpcState state, String result, T data) {
        this.state = state == null ? RpcState.FAILURE : state;
        this.result = result == null ? this.state.getDesc() : result;
        this.data = data;
        return this;
    }

    public boolean failed() {
        return getState() == null || getState() != RpcState.SUCCESS;
    }

    public boolean succeed() {
        return getState() != null && getState() == RpcState.SUCCESS;
    }

    public boolean hasSuccessData() {
        return succeed() && getData() != null;
    }

    /**
     * 创建RPC返回实例
     * @param <T> T
     * @return RpcResult
     */
    public static <T> RpcResult<T> newInstance() {
        return new RpcResult<T>();
    }

    public static <T> RpcResult<T> initSuccess(T data) {
        return build(RpcState.SUCCESS, null, data);
    }

    public static <T> RpcResult<T> initSuccess(String message, T data) {
        return build(RpcState.SUCCESS, message, data);
    }

    public static <T> RpcResult<T> initFailure(String message) {
        return build(RpcState.FAILURE, message, null);
    }

    public static <T> RpcResult<T> initError(String message) {
        return build(RpcState.ERROR, message, null);
    }

    protected static <T> RpcResult<T> build(RpcState state, String message, T data) {
        RpcResult<T> result = newInstance();
        result.state = state;
        result.result = message;
        result.data = data;
        return result;
    }
}

