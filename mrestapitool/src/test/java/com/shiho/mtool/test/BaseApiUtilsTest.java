package com.shiho.mtool.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shiho.mtool.ApiStatus;
import com.shiho.mtool.test.handler.TestJsonErrorHandler;
import com.shiho.mtool.test.utils.TestApiUtils;
import com.shiho.mtool.utils.BaseApiUtils;
import com.shiho.mtool.test.response.BaseTestApiResponse;
import com.shiho.mtool.test.response.MockResponse;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import retrofit2.Call;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BaseApiUtilsTest {

    private static BaseApiUtils baseApiUtils;

    @BeforeClass
    public static void initUnitTest(){
        baseApiUtils = new TestApiUtils("https://www.google.com.tw/");
    }

    @Test
    public void doApiSuccessTest(){
        Call<String> response = mock(Call.class);
        try {
            when(response.execute()).then(MockResponse
                    .getSuccessMockResponse("{\"message\": \"success message\"}"));

            String result = baseApiUtils.doApi(response);
            System.out.println(result);

            Assert.assertNotNull(result);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void defaultErrorHandleTest(){
        int testStatus = 400;
        String testErrMsg = "test error message.";

        baseApiUtils.setErrorHandler(null);
        Call<String> response = mock(Call.class);
        try {
            when(response.execute())
                    .then(MockResponse.getErrorHttpMockResponse(testStatus, testErrMsg));

            String result = baseApiUtils.doApi(response);
            System.out.println(result);

            ObjectMapper mapper = new ObjectMapper();

            BaseTestApiResponse baseTestApiResponse = mapper.readValue(result, BaseTestApiResponse.class);
            Assert.assertEquals(testStatus, baseTestApiResponse.getCode().intValue());
            Assert.assertEquals(testErrMsg, baseTestApiResponse.getMessage());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void customErrorHandlerTest(){
        int testStatus = 403;
        String testErrMsg = "Forbidden";

        TestJsonErrorHandler errorHandler = new TestJsonErrorHandler();
        baseApiUtils.setErrorHandler(errorHandler);

        Call<String> response = mock(Call.class);

        try {
            when(response.execute()).then(MockResponse.getErrorHttpMockResponse(testStatus, testErrMsg));

            String result = baseApiUtils.doApi(response);

            ObjectMapper mapper = new ObjectMapper();

            BaseTestApiResponse baseTestApiResponse = mapper.readValue(result, BaseTestApiResponse.class);
            Assert.assertEquals(testStatus, baseTestApiResponse.getCode().intValue());
            Assert.assertEquals(testErrMsg, baseTestApiResponse.getMessage());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void customErrorHandleExceptionTest(){
        TestJsonErrorHandler errorHandler = new TestJsonErrorHandler();
        baseApiUtils.setErrorHandler(errorHandler);

        Call<String> response = mock(Call.class);

        try {
            when(response.execute()).then(MockResponse.getExceptionMockResponse());

            String result = baseApiUtils.doApi(response);

            ObjectMapper mapper = new ObjectMapper();
            BaseTestApiResponse apiResponse = mapper.readValue(result, BaseTestApiResponse.class);
            Assert.assertEquals(apiResponse.getCode().intValue(), ApiStatus.API_JAVA_EXCEPTION);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void defaultErrorHandleExceptionTest(){
        baseApiUtils.setErrorHandler(null);

        Call<String> response = mock(Call.class);
        try {
            when(response.execute()).then(MockResponse.getExceptionMockResponse());

            String result = baseApiUtils.doApi(response);

            ObjectMapper mapper = new ObjectMapper();
            BaseTestApiResponse apiResponse = mapper.readValue(result, BaseTestApiResponse.class);
            Assert.assertEquals(apiResponse.getCode().intValue(), ApiStatus.API_JAVA_EXCEPTION);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
