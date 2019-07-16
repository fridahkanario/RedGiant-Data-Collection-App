package com.keredgiantaio.techsavanna.redgiantaio.helpers;

import com.keredgiantaio.techsavanna.redgiantaio.methods.DetailsOneResponse;
import com.keredgiantaio.techsavanna.redgiantaio.methods.DoortoDoorResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiDoorToDoorService {

    @FormUrlEncoded
    @POST("POST/createdoortodoor.php")
    Call<DoortoDoorResponse> sendRegister(@Field("openingstock") String openingstock,
                                          @Field("closingstock") String closingstock,
                                          @Field("comments") String comments,
                                          @Field("route") String route,
                                          @Field("product_focus") String product_focus,
                                          @Field("lat") String lat,
                                          @Field("lon") String lon);
}
