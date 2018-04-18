package com.shiho.mtool.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.shiho.mtool.ApiStatus;
import com.shiho.mtool.convert.JsonStringConverter;
import com.shiho.mtool.expection.LibStatusNullException;

import java.io.IOException;

public class JsonApiUtils extends BaseApiUtils {
    public JsonApiUtils(String url) {
        super(url, new JsonStringConverter());
    }

    public String convertRequestToJsonString(Object request) throws JsonProcessingException, LibStatusNullException {
        if(request != null){
            String jsonString = objectMapper.writeValueAsString(request);
            if(isDebug){
                System.out.printf("request json string: %s%n", jsonString);
            }
            return jsonString;
        }
        else{
            throw new LibStatusNullException(ApiStatus.API_REQUEST_NULL_MSG);
        }
    }

    public <T> T parseJsonResponse(String jsonStr, Class<T> object) throws LibStatusNullException {
        if(jsonStr == null){
            throw new LibStatusNullException(ApiStatus.API_RESPONSE_NULL_MSG);
        }
        try {
            T result = objectMapper.readValue(jsonStr, object);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException ie){
            ie.printStackTrace();
        }
        throw new LibStatusNullException(ApiStatus.API_PARSE_JSON2OBJ_NULL_MSG);
    }

}
