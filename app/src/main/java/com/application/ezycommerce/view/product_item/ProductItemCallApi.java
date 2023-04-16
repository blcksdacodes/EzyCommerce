package com.application.ezycommerce.view.product_item;

import com.application.ezycommerce.api.MyRetrofitServices;
import com.application.ezycommerce.models.MainModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductItemCallApi {
    ProductItemInterface view;
    MyRetrofitServices services;

    public ProductItemCallApi(ProductItemInterface view, MyRetrofitServices services) {
        this.view = view;
        this.services = services;
    }

    public void getItemDetail(String id, String nim, String nama) {
        Call<MainModel> callResponse = services.getBookDetail(id, nim, nama);
        callResponse.enqueue(new Callback<MainModel>() {
            @Override
            public void onResponse(Call<MainModel> call, Response<MainModel> response) {
                if (response.isSuccessful() && response.body().getProducts().size() != 0) {
                    view.onSuccessGetProductItem(response.body().getProducts().get(0));
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
