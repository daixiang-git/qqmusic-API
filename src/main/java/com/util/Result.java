package com.util;

/**
 * Created by 祥少 on 2017/7/2.
 */
public class Result<T> {
    private boolean isSuccess;
    private T data;
    private String message;

    public Result(boolean isSuccess, T data, String message) {
        this.isSuccess = isSuccess;
        this.data = data;
        this.message = message;
    }

    public Result(boolean isSuccess, T data) {
        this.isSuccess = isSuccess;
        this.data = data;
    }

    public Result(boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        this.message = message;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
