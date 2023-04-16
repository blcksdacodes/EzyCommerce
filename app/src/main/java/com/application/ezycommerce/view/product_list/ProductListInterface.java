package com.application.ezycommerce.view.product_list;

import com.application.ezycommerce.models.BookModel;

import java.util.List;

public interface ProductListInterface {
    void onSuccessGetBookList(List<BookModel> listBook);

    void onFailed(String message);
}
