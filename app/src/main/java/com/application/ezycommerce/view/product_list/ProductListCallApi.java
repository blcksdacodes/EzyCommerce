package com.application.ezycommerce.view.product_list;

import com.application.ezycommerce.api.MyRetrofitServices;
import com.application.ezycommerce.models.MainModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListCallApi {
    ProductListInterface view;
    MyRetrofitServices services;

    public ProductListCallApi(ProductListInterface view, MyRetrofitServices services) {
        this.view = view;
        this.services = services;
    }

    public void getBookList(String nim, String nama) {
        Call<MainModel> callResponse = services.getBookList(nim, nama);
        callResponse.enqueue(new Callback<MainModel>() {
            @Override
            public void onResponse(Call<MainModel> call, Response<MainModel> response) {
                if (response.isSuccessful() && response.body().getProducts().size() != 0) {
                    view.onSuccessGetBookList(response.body().getProducts());
                } else {
                    view.onFailed("Failed");
                }
            }

            @Override
            public void onFailure(Call<MainModel> call, Throwable t) {
                view.onFailed("Failed : " + t.getMessage());
            }
        });
    }
}
