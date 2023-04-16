package com.application.ezycommerce.view.product_item;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.application.ezycommerce.R;
import com.application.ezycommerce.db.DBCartHelper;
import com.application.ezycommerce.db.DBProductHelper;
import com.application.ezycommerce.models.BookModel;
import com.application.ezycommerce.service.ProductItemService;
import com.application.ezycommerce.service.ProductListService;
import com.bumptech.glide.Glide;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class ProductItemFragment extends Fragment {
    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_item, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btMin, btPlus;
        btMin = view.findViewById(R.id.btMin);
        btPlus = view.findViewById(R.id.btPlus);
        EditText etAmount = view.findViewById(R.id.etAmount);
        btMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int amount = Integer.parseInt(etAmount.getText().toString());
                if (amount != 1) {
                    amount = amount - 1;
                    etAmount.setText(String.valueOf(amount));
                }
            }
        });
        btPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int amount = Integer.parseInt(etAmount.getText().toString());
                amount = amount + 1;
                etAmount.setText(String.valueOf(amount));
            }
        });

        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                context.stopService(new Intent(context, ProductItemService.class));
                DBProductHelper db = new DBProductHelper(context);
                String message = intent.getStringExtra("message");
                if (message.equals("getData")) {
                    ImageView ivProduct = view.findViewById(R.id.ivProduct);
                    TextView tvProductName, tvPrice, tvDescription;
                    tvProductName = view.findViewById(R.id.tvProductName);
                    tvPrice = view.findViewById(R.id.tvPrice);
                    tvDescription = view.findViewById(R.id.tvDescription);
                    Button btCart = view.findViewById(R.id.btCart);

                    BookModel model = new BookModel();
                    Cursor cursor = db.getAllData();
                    cursor.moveToFirst();
                    model.setId(cursor.getString(0));
                    model.setName(cursor.getString(1));
                    model.setDescription(cursor.getString(2));
                    model.setAuthor(cursor.getString(3));
                    model.setType(cursor.getString(4));
                    model.setImg(cursor.getString(5));
                    model.setInCart(cursor.getString(6));
                    model.setCategory(cursor.getString(7));
                    model.setPrice(cursor.getDouble(8));

                    Glide.with(context).load(model.getImg()).into(ivProduct);
                    tvProductName.setText(model.getName());
                    tvPrice.setText("$" + model.getPrice());
                    tvDescription.setText(model.getDescription());
                    btCart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            DBCartHelper db = new DBCartHelper(context);

                            boolean isExist = false;

                            Cursor cursor2 = db.getAllData();
                            while (cursor2.moveToNext()) {
                                if (cursor2.getString(0).equals(model.getId())) {
                                    isExist = true;
                                    break;
                                }
                            }
                            if (isExist) {
                                int lastAmount = cursor2.getInt(3);
                                int newAmount = Integer.parseInt(etAmount.getText().toString());
                                db.setAmount(model.getId(), (lastAmount + newAmount));
                                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                            } else {
                                if (db.insertdata(model.getId(), model.getName(), model.getPrice(), Integer.parseInt(etAmount.getText().toString()), model.getImg())) {
                                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                    });
                }
            }
        };
        Bundle args = getArguments();

        LocalBroadcastManager.getInstance(context).registerReceiver(receiver, new IntentFilter("ProductItemService"));
        Intent service = new Intent(context, ProductItemService.class);
        service.putExtra("bookId", args.getString("bookId"));
        context.startService(service);
    }
}