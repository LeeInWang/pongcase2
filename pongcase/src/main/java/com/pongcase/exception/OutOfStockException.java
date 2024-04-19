package com.pongcase.exception;


//사용자 정의 Exception
public class OutOfStockException extends RuntimeException{
    public OutOfStockException(String message){
        super(message);
    }
}
