package com.application.ezycommerce.view.product_list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.application.ezycommerce.R;
import com.application.ezycommerce.models.BookModel;
import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    final List<BookModel> list;
    final ClickEvent clickEvent;

    public ProductAdapter(List<BookModel> list, ClickEvent clickEvent) {
        this.list = list;
        this.clickEvent = clickEvent;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvProductName.setText(list.get(position).getName());
        holder.tvPrice.setText("$" + list.get(position).getPrice());
        Glide.with(holder.itemView).load(list.get(position).getImg()).into(holder.ivProduct);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickEvent.onProductClicked(list.get(position).getId());
            }
        };

        holder.layoutProduct.setOnClickListener(onClickListener);
        holder.tvPrice.setOnClickListener(onClickListener);
        holder.ivProduct.setOnClickListener(onClickListener);
        holder.tvProductName.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName, tvPrice;
        ImageView ivProduct;
        LinearLayout layoutProduct;


        public ViewHolder(View view) {
            super(view);
            tvProductName = view.findViewById(R.id.tvProductName);
            tvPrice = view.findViewById(R.id.tvPrice);
            ivProduct = view.findViewById(R.id.ivProduct);
            layoutProduct = view.findViewById(R.id.layoutProduct);
        }
    }
}
