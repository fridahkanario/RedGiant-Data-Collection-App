package com.keredgiantaio.techsavanna.redgiantaio.helpers;

import com.keredgiantaio.techsavanna.redgiantaio.methods.DetailsOneResponse;
import com.keredgiantaio.techsavanna.redgiantaio.methods.MerchandisingResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiMerchandisingService {

    @FormUrlEncoded
    @POST("POST/createmerchandising.php")
    Call<MerchandisingResponse> sendRegister(@Field("registeredby") String banames,
                                             @Field("structure_name") String outletname,
                                             @Field("instock") String openingstock,
                                             @Field("outofstock") String closingstock,
                                             @Field("actiontaken") String comments,
                                             @Field("route") String route,
                                             @Field("campaign_name") String campaign,
                                             @Field("id_user") String id_users,
                                             @Field("lat") String lat,
                                             @Field("lon") String lon);
}
