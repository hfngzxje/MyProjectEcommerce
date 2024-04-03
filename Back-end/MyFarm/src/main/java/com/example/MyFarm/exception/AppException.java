package com.example.MyFarm.exception;

import com.example.MyFarm.enums.ErrorCode;
import lombok.Data;

@Data
public class AppException extends RuntimeException{
    private ErrorCode errorCode;

    public AppException(ErrorCode errorCode) {
        super(errorCode.getMassage());
        this.errorCode = errorCode;
    }
}
