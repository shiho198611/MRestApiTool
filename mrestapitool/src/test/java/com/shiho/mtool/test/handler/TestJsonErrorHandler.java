package com.shiho.mtool.test.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shiho.mtool.ErrorHandler;
import com.shiho.mtool.test.response.BaseTestApiResponse;
import retrofit2.Response;

public class TestJsonErrorHandler implements ErrorHandler {

    private ObjectMapper objectMapper;

    public TestJsonErrorHandler(){
        objectMapper = new ObjectMapper();
    }

    @Override
    public String handleErrorResponse(Response<String> response) {
        BaseTestApiResponse apiResponse = new BaseTestApiResponse();
        apiResponse.setCode((long) response.code());
        apiResponse.setMessage(response.message());

        try {
            return objectMapper.writeValueAsString(apiResponse);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}";
        }
    }

}
