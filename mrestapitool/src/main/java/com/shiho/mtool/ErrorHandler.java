package com.shiho.mtool;


import retrofit2.Response;

public interface ErrorHandler {
//    abstract public String handleErrorResponse(int status, String errorMsg);
    abstract public String handleErrorResponse(Response<String> response);
}
