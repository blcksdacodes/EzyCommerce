package com.application.ezycommerce.view.cart;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.application.ezycommerce.R;
import com.application.ezycommerce.db.DBCartHelper;
import com.application.ezycommerce.view.MainActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;


public class CartFragment extends Fragment {
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DBCartHelper db = new DBCartHelper(context);
        List<CartModel> listCart = new ArrayList<>();

        Cursor cursor = db.getAllData();
        while (cursor.moveToNext()) {
            CartModel model = new CartModel();
            model.setId(cursor.getString(0));
            model.setName(cursor.getString(1));
            model.setPrice(cursor.getDouble(2));
            model.setAmount(cursor.getInt(3));
            model.setImg(cursor.getString(4));
            listCart.add(model);
        }

        TextView tvNoItem = view.findViewById(R.id.tvNoItem);
        ScrollView layoutCart = view.findViewById(R.id.layoutCart);

        if (listCart.isEmpty()) {
            tvNoItem.setVisibility(View.VISIBLE);
            layoutCart.setVisibility(View.GONE);
        } else {
            tvNoItem.setVisibility(View.GONE);
            layoutCart.setVisibility(View.VISIBLE);

            RecyclerView rvCart = view.findViewById(R.id.rvCart);
            rvCart.setAdapter(new CartAdapter(listCart));
            TextView tvSubTotal, tvTaxes, tvTotal;
            Button btBuy, btCancel;

            tvSubTotal = view.findViewById(R.id.tvSubTotal);
            tvTaxes = view.findViewById(R.id.tvTaxes);
            tvTotal = view.findViewById(R.id.tvTotal);

            btBuy = view.findViewById(R.id.btBuy);
            btCancel = view.findViewById(R.id.btCancel);

            double subTotal = 0;
            for (int i = 0; i < listCart.size(); i++) {
                subTotal += (listCart.get(i).getPrice() * listCart.get(i).getAmount());
            }
            double taxes = (5 * subTotal) / 100;
            double total = subTotal + taxes;

            tvSubTotal.setText("$" + String.format("%.2f", subTotal));
            tvTaxes.setText("$" + String.format("%.2f", taxes));
            tvTotal.setText("$" + String.format("%.2f", total));

            btCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db.deleteAllData();
                    ((MainActivity) getActivity()).onBackPressed();
                }
            });

            btBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db.deleteAllData();
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                    ((MainActivity) getActivity()).onBackPressed();
                }
            });
        }


    }
}