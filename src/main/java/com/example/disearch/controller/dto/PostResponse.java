package com.example.disearch.controller.dto;

import java.util.List;

import java.util.List;
import java.util.Map;

public class PostResponse {

    private int status;
    private String msg;
    private Map<String, Long> data;

    public PostResponse(int status, String msg, Map<String, Long> data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public Map<String, Long> getData() {
        return data;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(Map<String, Long> data) {
        this.data = data;
    }
}

