package com.keredgiantaio.techsavanna.redgiantaio.helpers;

import com.keredgiantaio.techsavanna.redgiantaio.methods.DetailsOneResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiMerchandisingService {

    @FormUrlEncoded
    @POST("POST/createmerchandising.php")
    Call<DetailsOneResponse> sendRegister(@Field("instock") String openingstock,
                                          @Field("outofstock") String closingstock,
                                          @Field("actiontaken") String comments,
                                          @Field("route") String route,
                                          @Field("lat") String lat,
                                          @Field("lon") String lon);
}
