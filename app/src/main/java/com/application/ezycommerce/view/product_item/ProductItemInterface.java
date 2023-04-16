package com.application.ezycommerce.view.product_item;

import com.application.ezycommerce.models.BookModel;

public interface ProductItemInterface {
    void onSuccessGetProductItem(BookModel item);
    void onFailed(String message);
}
