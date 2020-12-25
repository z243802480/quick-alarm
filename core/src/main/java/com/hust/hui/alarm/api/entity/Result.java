package com.hust.hui.alarm.api.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class Result implements Serializable {
    private static final long serialVersionUID = 15357923558724454L;

    private int code = 200;
    private String msg;
    private Object data;

    public static Result ok() {
        Result r = new Result();
        r.setMsg("操作成功");
        return r;
    }

    public static Result ok(Object data) {
        Result r = new Result();
        r.setMsg("操作成功");
        r.setData(data);
        return r;
    }

    public static Result error() {
        return error(500, "未知异常，请联系管理员");
    }

    public static Result error(String msg) {
        return error(500, msg);
    }

    public static Result error(int code, String msg) {
        Result r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }
}
