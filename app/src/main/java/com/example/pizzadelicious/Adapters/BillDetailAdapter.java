package com.example.pizzadelicious.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzadelicious.Fragments.CartFragment;
import com.example.pizzadelicious.Fragments.PizzaFragment;
import com.example.pizzadelicious.Models.Bill;
import com.example.pizzadelicious.Models.BillDetail;
import com.example.pizzadelicious.Models.Product;
import com.example.pizzadelicious.R;
import com.example.pizzadelicious.Retrofit.Common;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BillDetailAdapter extends RecyclerView.Adapter<BillDetailAdapter.ViewHolder> {
    private ArrayList<BillDetail> list;
    CartFragment cartFragment;
    private Context context;

    public BillDetailAdapter(ArrayList<BillDetail> list, CartFragment cartFragment) {
        this.list = list;
        this.cartFragment = cartFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(cartFragment.getContext()).inflate(R.layout.cart_item, parent, false);
        context = parent.getContext();
        return new BillDetailAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillDetailAdapter.ViewHolder holder, int position) {
        BillDetail model = list.get(position);
        Picasso.get().load(model.getProduct().getImage())
                .into(holder.img_item);
        holder.tv_name_item.setText(model.getProduct().getName());
        holder.tv_price_item.setText(model.getProduct().getPrice());
        holder.tv_quantity_item.setText(model.getQuantity());
        holder.tv_total_prices_item.setText(model.getPrices());

        //Common.currentBill.setPrices(""+(Integer.parseInt(model.getProduct().getPrice())*Integer.parseInt(model.getQuantity())));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_item;
        TextView tv_name_item, tv_price_item, tv_quantity_item, tv_total_prices_item;
        ImageButton btn_delete, btn_decrease, btn_increase;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_item = itemView.findViewById(R.id.img_item);
            tv_name_item = itemView.findViewById(R.id.tv_name_item);
            tv_price_item = itemView.findViewById(R.id.tv_price_item);
            tv_quantity_item = itemView.findViewById(R.id.tv_quantity_item);
            tv_total_prices_item = itemView.findViewById(R.id.tv_total_prices_item);
            btn_delete = itemView.findViewById(R.id.btn_delete);
            btn_decrease = itemView.findViewById(R.id.btn_decrease);
            btn_increase = itemView.findViewById(R.id.btn_increase);
        }
    }
}
