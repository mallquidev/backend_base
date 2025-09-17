package com.deone.base.bussoft.core.commons.exception;

import lombok.Getter;

@Getter
public class EmailAlreadyExistsException extends RuntimeException{
    private ErrorCode errorCode;

    public EmailAlreadyExistsException() {
        super(ErrorCode.USER_EMAIL_EXISTS.getMessage());
        this.errorCode = ErrorCode.USER_EMAIL_EXISTS;
    }
}
