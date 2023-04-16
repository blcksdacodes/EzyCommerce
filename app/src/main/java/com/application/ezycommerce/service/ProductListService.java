package com.application.ezycommerce.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.application.ezycommerce.api.MyRetrofit;
import com.application.ezycommerce.api.MyRetrofitServices;
import com.application.ezycommerce.db.DBProductHelper;
import com.application.ezycommerce.models.BookModel;
import com.application.ezycommerce.view.product_list.ProductListCallApi;
import com.application.ezycommerce.view.product_list.ProductListInterface;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class ProductListService extends Service implements ProductListInterface {
    MyRetrofitServices services;
    ProductListCallApi callApi;
    DBProductHelper db;

    @Override
    public void onCreate() {
        super.onCreate();
        services = new MyRetrofit().getMyRetrofit().create(MyRetrofitServices.class);
        callApi = new ProductListCallApi(this, services);
        db = new DBProductHelper(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        callApi.getBookList("2201813320", "fachriadam");
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        services = null;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onSuccessGetBookList(List<BookModel> listBook) {
        db.deleteAllData();
        for (int i = 0; i < listBook.size(); i++) {
            db.insertdata(listBook.get(i));
        }
        Intent intent = new Intent("ProductListService");
        intent.putExtra("message", "getData");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    public void onFailed(String message) {
        Intent intent = new Intent("ProductListService");
        intent.putExtra("message", message);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
