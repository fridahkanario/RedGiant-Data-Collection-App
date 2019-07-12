package com.keredgiantaio.techsavanna.redgiantaio.helpers;

import com.keredgiantaio.techsavanna.redgiantaio.methods.DetailsOneResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiRoadShowService {

    @FormUrlEncoded
    @POST("POST/createroadshow.php")
    Call<DetailsOneResponse> sendRegister(@Field("merchandise") String merchandise,
                                          @Field("crowdsize") String crowdsize,
                                          @Field("comments") String comments,
                                          @Field("route") String route,
                                          @Field("lat") String lat,
                                          @Field("lon") String lon);
}