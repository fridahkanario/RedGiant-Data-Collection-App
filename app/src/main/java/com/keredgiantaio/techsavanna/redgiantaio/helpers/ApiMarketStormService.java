package com.keredgiantaio.techsavanna.redgiantaio.helpers;

import com.keredgiantaio.techsavanna.redgiantaio.methods.DetailsOneResponse;
import com.keredgiantaio.techsavanna.redgiantaio.methods.MarketStormResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiMarketStormService {

    @FormUrlEncoded
    @POST("POST/createmarketstorm.php")
    Call<MarketStormResponse> sendRegister(@Field("registeredby") String banames,
                                           @Field("structure_name") String outletname,
                                           @Field("openingstock") String openingstock,
                                           @Field("closingstock") String closingstock,
                                           @Field("comments") String comments,
                                           @Field("route") String route,
                                           @Field("campaign_name") String campaign,
                                           @Field("id_user") String id_users,
                                           @Field("lat") String lat,
                                           @Field("lon") String lon);
}
