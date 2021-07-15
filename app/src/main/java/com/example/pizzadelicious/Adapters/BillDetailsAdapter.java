package com.example.pizzadelicious.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzadelicious.Fragments.BillDetailsFragment;
import com.example.pizzadelicious.Models.BillDetail;
import com.example.pizzadelicious.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BillDetailsAdapter extends RecyclerView.Adapter<BillDetailsAdapter.ViewHolder> {
    private ArrayList<BillDetail> list;
    private BillDetailsFragment billDetailsFragment;
    private Context context;


    public BillDetailsAdapter(ArrayList<BillDetail> list, BillDetailsFragment billDetailsFragment) {
        this.list = list;
        this.billDetailsFragment = billDetailsFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(billDetailsFragment.getContext()).inflate(R.layout.bill_details_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BillDetail model = list.get(position);
        Picasso.get().load(model.getProduct().getImage())
                .into(holder.img_item);
        holder.tv_name_item.setText(model.getProduct().getName());
        holder.tv_price_item.setText(model.getProduct().getPrice());
        holder.tv_quantity_item.setText(model.getQuantity());
        holder.tv_total_prices_item.setText(model.getPrices());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_item;
        TextView tv_name_item, tv_price_item, tv_quantity_item, tv_total_prices_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_item = itemView.findViewById(R.id.img_item);
            tv_name_item = itemView.findViewById(R.id.tv_name_item);
            tv_price_item = itemView.findViewById(R.id.tv_price_item);
            tv_quantity_item = itemView.findViewById(R.id.tv_quantity_item);
            tv_total_prices_item = itemView.findViewById(R.id.tv_total_prices_item);

        }

    }
}
