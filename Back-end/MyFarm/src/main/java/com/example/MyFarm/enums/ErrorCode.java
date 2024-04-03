package com.example.MyFarm.enums;

import lombok.Data;

public enum ErrorCode {
    INVALID_KEY(1001,"Invalid massage key!"),
    USER_EXISTED(1002,"User existed!"),
    USERNAME_INVALID(1003,"Username must be 3 characters!"),
    INVALID_PASSWORD(1004,"Password must be 8 characters!"),
    USER_NOT_EXISTED(1005,"User not existed!"),
    INVALID_LOGINRQ(1005,"Wrong username or password!"),
    UNCATEGORIZED_EXCEPTION(9999,"Uncategorized error!"),
    ;
    private int code;
    private String massage;



    ErrorCode(int code, String massage) {
        this.code = code;
        this.massage = massage;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }
}
