package com.shiho.sample.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SampleService {
    @GET("repos/{owner}/{repo}")
    Call<String> getMyRepoInfomation(@Path("owner") String owner, @Path("repo") String repo);

    @GET("users")
    Call<String> getAllSignUpUsers(@Query("since") String userId);

}
