package com.shiho.mtool.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shiho.mtool.ApiStatus;
import com.shiho.mtool.expection.LibStatusNullException;
import com.shiho.mtool.test.handler.TestJsonErrorHandler;
import com.shiho.mtool.test.response.BaseTestApiResponse;
import com.shiho.mtool.utils.JsonApiUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class JsonApiUtilsTest {

    private static JsonApiUtils jsonApiUtils;

    @BeforeClass
    public static void initUnitTest(){
        jsonApiUtils = new JsonApiUtils("http://www.google.com.tw/");
    }

    @Test
    public void convertRequestToJsonStringRequestSuccess(){
        BaseTestApiResponse apiResponse = new BaseTestApiResponse();
        apiResponse.setCode(new Long(200));
        apiResponse.setMessage("success message");

        try {
            String request = jsonApiUtils.convertRequestToJsonString(apiResponse);
            Assert.assertNotNull(request);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (LibStatusNullException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void parseJsonResponseSuccess(){
        String jsonStr = "{\"code\": 200,\"message\": \"success message\"}";

        try {
            BaseTestApiResponse apiResponse = jsonApiUtils.parseJsonResponse(jsonStr, BaseTestApiResponse.class);
            Assert.assertEquals(apiResponse.getCode().intValue(), 200);
            Assert.assertEquals(apiResponse.getMessage(), "success message");
        } catch (LibStatusNullException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void convertRequestToJsonStringRequestNullTest() {
        try {
            jsonApiUtils.convertRequestToJsonString(null);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (LibStatusNullException e) {
            Assert.assertEquals(e.getMessage(), ApiStatus.API_REQUEST_NULL_MSG);
        }
    }

    @Test
    public void parseJsonResponseNullJsonStrTest(){
        try {
            jsonApiUtils.parseJsonResponse(null, BaseTestApiResponse.class);
        } catch (LibStatusNullException e) {
            Assert.assertEquals(e.getMessage(), ApiStatus.API_RESPONSE_NULL_MSG);
        }
    }

    @Test
    public void parseJsonResponseErrorObjTest(){
        try {
            jsonApiUtils.parseJsonResponse("{\"message\": \"success message\"}", TestJsonErrorHandler.class);
        } catch (LibStatusNullException e) {
            Assert.assertEquals(e.getMessage(), ApiStatus.API_PARSE_JSON2OBJ_NULL_MSG);
        }
    }

    @Test
    public void parseJsonResponseWrongJsonStrTest(){
        try {
            jsonApiUtils.parseJsonResponse("{\"message\": \"success message\"", BaseTestApiResponse.class);
        } catch (LibStatusNullException e) {
            Assert.assertEquals(e.getMessage(), ApiStatus.API_PARSE_JSON2OBJ_NULL_MSG);
        }
    }

    @Test
    public void parseJsonResponseNullParseClass(){
        try {
            jsonApiUtils.parseJsonResponse("{\"message\": \"success message\"}", null);
        } catch (LibStatusNullException e) {
            Assert.assertEquals(e.getMessage(), ApiStatus.API_PARSE_JSON2OBJ_NULL_MSG);
        }
    }

}
