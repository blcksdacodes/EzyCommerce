package com.application.ezycommerce.view.product_list;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.ezycommerce.R;
import com.application.ezycommerce.db.DBProductHelper;
import com.application.ezycommerce.models.BookModel;
import com.application.ezycommerce.service.ProductListService;
import com.application.ezycommerce.view.MainActivity;
import com.application.ezycommerce.view.product_item.ProductItemFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;


import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

public class ProductListFragment extends Fragment implements ClickEvent {
    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_product_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton btCart = view.findViewById(R.id.btCart);
        btCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).loadCartFragment();
            }
        });

        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                context.stopService(new Intent(context, ProductListService.class));
                DBProductHelper db = new DBProductHelper(context);
                String message = intent.getStringExtra("message");
                if (message.equals("getData")) {
                    RecyclerView rvProduct, rvCategory;
                    rvProduct = view.findViewById(R.id.rvProduct);
                    rvCategory = view.findViewById(R.id.rvCategory);

                    List<BookModel> listBook = new ArrayList<>();

                    Cursor cursor = db.getAllData();

                    while (cursor.moveToNext()) {
                        BookModel model = new BookModel();
                        model.setId(cursor.getString(0));
                        model.setName(cursor.getString(1));
                        model.setDescription(cursor.getString(2));
                        model.setAuthor(cursor.getString(3));
                        model.setType(cursor.getString(4));
                        model.setImg(cursor.getString(5));
                        model.setInCart(cursor.getString(6));
                        model.setCategory(cursor.getString(7));
                        model.setPrice(cursor.getDouble(8));

                        listBook.add(model);
                    }
                    rvProduct.setAdapter(new ProductAdapter(listBook, ProductListFragment.this));

                    List<String> listCategory = new ArrayList<>();
                    for (int i = 0; i < listBook.size(); i++) {
                        if (!listCategory.contains(listBook.get(i).getCategory())) {
                            listCategory.add(listBook.get(i).getCategory());
                        }
                    }
                    rvCategory.setAdapter(new CategoryAdapter(listCategory, ProductListFragment.this));
                }
            }
        };
        LocalBroadcastManager.getInstance(context).registerReceiver(receiver, new IntentFilter("ProductListService"));
        context.startService(new Intent(context, ProductListService.class));
    }

    @Override
    public void onCategoryClicked(String category) {
        RecyclerView rvProduct = getView().findViewById(R.id.rvProduct);
        List<BookModel> listBook = new ArrayList<>();
        DBProductHelper db = new DBProductHelper(context);
        Cursor cursor = db.getAllData();

        while (cursor.moveToNext()) {
            if (cursor.getString(7).equals(category)) {
                BookModel model = new BookModel();
                model.setId(cursor.getString(0));
                model.setName(cursor.getString(1));
                model.setDescription(cursor.getString(2));
                model.setAuthor(cursor.getString(3));
                model.setType(cursor.getString(4));
                model.setImg(cursor.getString(5));
                model.setInCart(cursor.getString(6));
                model.setCategory(cursor.getString(7));
                model.setPrice(cursor.getDouble(8));

                listBook.add(model);
            }
        }
        rvProduct.setAdapter(new ProductAdapter(listBook, ProductListFragment.this));
    }

    @Override
    public void onProductClicked(String id) {
        ((MainActivity) getActivity()).loadProductItemFragment(id);
    }
}