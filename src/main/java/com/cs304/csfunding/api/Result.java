package com.cs304.csfunding.api;

import lombok.Data;

@Data
public class Result {
    private Integer code;
    private String message;
    private Object data;

    public Result() {

    }

    public Result(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }


}
