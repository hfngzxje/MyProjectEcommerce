package com.example.MyFarm.enums;

import lombok.Data;

public enum ErrorCode {
    INVALID_KEY(1001,"Invalid massage key!"),
    USER_EXISTED(1002,"User existed!"),
    INVALID_USERNAME(1003,"Username must be 3 characters!"),
    INVALID_PASSWORD(1004,"Password must be 8 characters!"),
    INVALID_EMAIL(1005,"Email must be correct format !"),
    INVALID_PHONE_NUMBER(1006,"Phone number must be 10-digit !"),
    USER_NOT_EXISTED(1007,"User not existed!"),
    INVALID_LOGINRQ(1008,"Wrong username or password "),
    INVALID_ACCBAN(1009,"Your account has been locked !"),
    INVALID_OLDPASSWORD(1010,"Old password don't match !"),
    NOTMATCH_NEWPASSWORD(1011,"Re-password do not match with new password!"),
    NOTMATCH_OTP(1012,"The OTP is not match!!"),
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
