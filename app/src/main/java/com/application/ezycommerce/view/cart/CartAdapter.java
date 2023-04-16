package com.application.ezycommerce.view.cart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.ezycommerce.R;
import com.application.ezycommerce.models.BookModel;
import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    final List<CartModel> list;

    public CartAdapter(List<CartModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(holder.itemView).load(list.get(position).getImg()).into(holder.ivProduct);
        holder.tvProductName.setText(list.get(position).getName());
        holder.tvAmount.setText(list.get(position).getAmount() + " Pcs");
        holder.tvPrice.setText("$" + list.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName, tvAmount, tvPrice;
        ImageView ivProduct;

        public ViewHolder(View view) {
            super(view);
            tvAmount = view.findViewById(R.id.tvAmount);
            tvPrice = view.findViewById(R.id.tvPrice);
            tvProductName = view.findViewById(R.id.tvProductName);
            ivProduct = view.findViewById(R.id.ivProduct);
        }
    }
}
