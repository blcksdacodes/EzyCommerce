package com.application.ezycommerce.api;

import com.application.ezycommerce.models.MainModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MyRetrofitServices {
    @GET("/staging/book")
    Call<MainModel> getBookList(@Query("nim") String nim, @Query("nama") String nama);

    @GET("/staging/book/{bookId}/")
    Call<MainModel> getBookDetail(@Path("bookId") String bookId, @Query("nim") String nim, @Query("nama") String nama);
}
