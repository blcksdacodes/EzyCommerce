package com.application.ezycommerce.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MyRetrofit {
    Retrofit retrofit;

    public Retrofit getMyRetrofit(){
        final String BaseURL = "https://u73olh7vwg.execute-api.ap-northeast-2.amazonaws.com";
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BaseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
