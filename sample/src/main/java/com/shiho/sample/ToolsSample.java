package com.shiho.sample;

import com.shiho.mtool.utils.JsonApiUtils;
import com.shiho.sample.api.JsonErrorHandler;
import com.shiho.sample.api.SampleService;
import retrofit2.Call;

public class ToolsSample {
    public static void main(String[] args) {
        JsonApiUtils baseApiUtils = new JsonApiUtils(SampleConst.SERVICE_URL);
        SampleService sampleService = baseApiUtils.createRetrofitApiService(SampleService.class);
        baseApiUtils.setErrorHandler(new JsonErrorHandler());

        pathSample(baseApiUtils, sampleService);
        querySample(baseApiUtils, sampleService);

    }

    private static void pathSample(JsonApiUtils utils, SampleService sampleService){
        Call<String> response = sampleService.getMyRepoInfomation("shiho198611", "MRestApiTool");
        String result = utils.doApi(response);
        System.out.println(result);
    }

    private static void querySample(JsonApiUtils utils, SampleService sampleService){
        Call<String> response = sampleService.getAllSignUpUsers("5583958");
        String result = utils.doApi(response);
        System.out.println(result);
    }
}
