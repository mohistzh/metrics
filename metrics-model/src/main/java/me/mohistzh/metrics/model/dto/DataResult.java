package me.mohistzh.metrics.model.dto;

import java.util.Date;
/**
 * Common data model of API response
 * @Author Jonathan
 * @Date 2019/12/11
 **/
public class DataResult<T> {

    private int status;
    private String message;
    private T data;
    private Date time;

    public DataResult() {}

    public DataResult(int status, String message, T data) {
        this.status =status;
        this.data = data;
        this.message = message;
        this.setTime(new Date());
    }


    public static <V> DataResult<V> failure(int status, String message, V result) {
        return new DataResult<V>(status, message, result);
    }

    public static <V> DataResult<V> failure(String message) {
        DataResult<V> result = new DataResult<V>();
        result.setMessage(message);
        result.setStatus(-1);
        result.setTime(new Date());
        return result;
    }

    public static <V> DataResult<V> failure(int status, String message) {
        DataResult<V> result = new DataResult<V>();
        result.setStatus(status);
        result.setMessage(message);
        result.setTime(new Date());
        return result;
    }

    public static <V> DataResult<V> success() {
        DataResult<V> result = new DataResult<>();
        result.setMessage("success");
        return result;
    }
    public static <V> DataResult<V> success(V data) {
        DataResult<V> result = new DataResult<V>();
        result.setStatus(0);
        result.setMessage("success");
        result.setData(data);
        result.setTime(new Date());
        return result;
    }

    public static <V> DataResult<V> success(String message, V data) {
        DataResult<V> result = new DataResult<V>();
        result.setMessage(message);
        result.setData(data);
        result.setTime(new Date());
        return result;
    }

    public static <V> DataResult<V> success(Integer status,String message, V data) {
        DataResult<V> result = new DataResult<V>();
        result.setStatus(status);
        result.setMessage(message);
        result.setData(data);
        result.setTime(new Date());
        return result;
    }
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
