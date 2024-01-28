package com.myblog7.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)//3.used what mesg I want to send when status not found it means this obj will be created when record not found
public class ResourceNotFound extends RuntimeException {
    public ResourceNotFound(String msg) {//1.when i create obj of this class i send mesg to constructor resource not found
        super(msg);//2.super will automatically display mesg in postman response
    }
}
