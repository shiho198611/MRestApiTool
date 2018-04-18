package com.shiho.sample.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shiho.mtool.ErrorHandler;
import retrofit2.Response;

public class JsonErrorHandler implements ErrorHandler {

    private ObjectMapper objectMapper;

    public JsonErrorHandler(){
        objectMapper = new ObjectMapper();
    }

    @Override
    public String handleErrorResponse(Response<String> response) {

        SampleApiResponse sampleApiResponse = new SampleApiResponse();
        sampleApiResponse.setCode((long) response.code());
        sampleApiResponse.setMessage(response.message());

        try {
            return objectMapper.writeValueAsString(sampleApiResponse);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}";
        }

    }
}
