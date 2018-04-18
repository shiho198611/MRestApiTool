package com.shiho.mtool.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shiho.mtool.ApiStatus;
import com.shiho.mtool.ErrorHandler;
import com.shiho.mtool.convert.JsonStringConverter;
import com.shiho.mtool.convert.StringConverter;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

abstract public class BaseApiUtils {

    protected String url;
    protected Retrofit retrofit;
    protected boolean isDebug;
    protected ObjectMapper objectMapper;

    private ErrorHandler errorHandler;
    private int readTimeOut;
    private int connectTimeOut;

    public BaseApiUtils(String url, StringConverter stringConverter){
        this.url = url;

        readTimeOut = 30;
        connectTimeOut = 30;

        OkHttpClient okHttpClient = configHttpClient();
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(stringConverter)
                .client(okHttpClient)
                .build();
        objectMapper = new ObjectMapper();
    }

    public <T> T createRetrofitApiService(Class<T> service){
        T cloudService = retrofit.create(service);
        return cloudService;
    }

    public String doApi(Call<String> response) {
        try {
            Response<String> apiResponse = response.execute();
            int status = apiResponse.code();
            if(status == ApiStatus.HTTP_SUCCESS){
                String rtnResponse = apiResponse.body();
                if(isDebug){
                    System.out.printf("response body: %s%n", rtnResponse);
                }
                return rtnResponse;
            }
            else{
                if(isDebug){
                    System.out.printf("err status: %d%n", status);
                }
                return handleErrorCase(apiResponse);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return handleErrorCase(genExtErrorResponse(ApiStatus.API_JAVA_EXCEPTION, e.getMessage()));
        } catch (IllegalArgumentException ie){
            ie.printStackTrace();
            return handleErrorCase(genExtErrorResponse(ApiStatus.API_JAVA_EXCEPTION, ie.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return  handleErrorCase(genExtErrorResponse(ApiStatus.API_JAVA_EXCEPTION, e.getMessage()));
        }
    }

    protected OkHttpClient configHttpClient(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(readTimeOut, TimeUnit.SECONDS)
                .connectTimeout(connectTimeOut, TimeUnit.SECONDS)
                .build();
        return okHttpClient;
    }

    protected String defaultErrorHandler(int statusCode, Response<String> response){
        com.fasterxml.jackson.databind.node.ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("code", statusCode);
        objectNode.put("message", response.message());

        return objectNode.toString();
    }

    private String handleErrorCase(Response<String> response){
        if(errorHandler != null){
            return errorHandler.handleErrorResponse(response);
        }
        return defaultErrorHandler(response.code(), response);
    }

    private Response<String> genExtErrorResponse(int statusCode, String errMsg){
        ResponseBody errBody = ResponseBody.create(null, errMsg);
        okhttp3.Response errRsp = new okhttp3.Response.Builder()
                .code(statusCode)
                .message(errMsg)
                .protocol(Protocol.HTTP_1_1)
                .request(new Request.Builder().url(url).build())
                .build();
        return Response.error(errBody, errRsp);
    }

    public boolean isDebug() {
        return isDebug;
    }

    public void setDebug(boolean debug) {
        isDebug = debug;
    }

    public ErrorHandler getErrorHandler() {
        return errorHandler;
    }

    public void setErrorHandler(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    public int getReadTimeOut() {
        return readTimeOut;
    }

    public void setReadTimeOut(int readTimeOut) {
        this.readTimeOut = readTimeOut;
    }

    public int getConnectTimeOut() {
        return connectTimeOut;
    }

    public void setConnectTimeOut(int connectTimeOut) {
        this.connectTimeOut = connectTimeOut;
    }
}
