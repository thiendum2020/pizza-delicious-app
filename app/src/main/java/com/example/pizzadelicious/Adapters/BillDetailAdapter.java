package com.example.pizzadelicious.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzadelicious.Fragments.CartFragment;
import com.example.pizzadelicious.Fragments.PizzaFragment;
import com.example.pizzadelicious.Models.Bill;
import com.example.pizzadelicious.Models.BillDetail;
import com.example.pizzadelicious.Models.JSONResponseBillDetail;
import com.example.pizzadelicious.Models.Product;
import com.example.pizzadelicious.R;
import com.example.pizzadelicious.Retrofit.ApiInterface;
import com.example.pizzadelicious.Retrofit.Common;
import com.example.pizzadelicious.Utils.IOnImageViewAdapterClickListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillDetailAdapter extends RecyclerView.Adapter<BillDetailAdapter.ViewHolder> {
    private ArrayList<BillDetail> list;
    CartFragment cartFragment;
    private Context context;
    ApiInterface service;


    public BillDetailAdapter(ArrayList<BillDetail> list, CartFragment cartFragment) {
        this.list = list;
        this.cartFragment = cartFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(cartFragment.getContext()).inflate(R.layout.cart_item, parent, false);
        context = parent.getContext();
        service = Common.getGsonService();
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

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog;
                progressDialog = new ProgressDialog(cartFragment.getContext());
                progressDialog.setMessage("Deleting...");
                progressDialog.show();

                service.deleteBillDetail("" + model.getId()).enqueue(new Callback<JSONResponseBillDetail>() {
                    @Override
                    public void onResponse(Call<JSONResponseBillDetail> call, Response<JSONResponseBillDetail> response) {
                        Toast.makeText(cartFragment.getContext(), "Đã xóa: " + model.getProduct().getName(), Toast.LENGTH_SHORT).show();
                        cartFragment.getFragmentManager().beginTransaction().detach(cartFragment).attach(cartFragment).commit();
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<JSONResponseBillDetail> call, Throwable t) {

                    }
                });
            }
        });
        //Common.currentBill.setPrices(""+(Integer.parseInt(model.getProduct().getPrice())*Integer.parseInt(model.getQuantity())));
        //event
        holder.setListener((view, position1, isDecrease, isIncrease) -> {
            if (isDecrease) {
                if (Integer.parseInt(holder.tv_quantity_item.getText().toString()) > 1) {
//                    Common.totalBill = Common.totalBill - Integer.parseInt(model.getPrices());
                    holder.tv_quantity_item.setText("" + (Integer.parseInt(holder.tv_quantity_item.getText().toString()) - 1));
                    Log.d("1: ", "" + (Integer.parseInt(holder.tv_quantity_item.getText().toString()) - 1));
                }
            } else {
                if (isIncrease) {
                    if (Integer.parseInt(holder.tv_quantity_item.getText().toString()) < 99) {
//                        Common.totalBill = Common.totalBill + Integer.parseInt(model.getPrices());
                        holder.tv_quantity_item.setText("" + (Integer.parseInt(holder.tv_quantity_item.getText().toString()) + 1));

                        Log.d("2: ", "" + (Integer.parseInt(holder.tv_quantity_item.getText().toString()) + 1));
                    }
                }
            }
            //Update total prices
            holder.tv_total_prices_item.setText("" + (Integer.parseInt(holder.tv_quantity_item.getText().toString()) * Integer.parseInt(model.getProduct().getPrice())));
            //Run API update bill detail

        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView img_item;
        TextView tv_name_item, tv_price_item, tv_quantity_item, tv_total_prices_item;
        ImageButton btn_delete;
        ImageView btn_decrease, btn_increase;

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

            btn_increase.setOnClickListener(this);
            btn_decrease.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == btn_increase) {
                listener.onCalculatePriceListener(v, getAbsoluteAdapterPosition(), false, true);
            } else if (v == btn_decrease) {
                listener.onCalculatePriceListener(v, getAbsoluteAdapterPosition(), true, false);
            }
        }

        IOnImageViewAdapterClickListener listener;

        public void setListener(IOnImageViewAdapterClickListener listener) {
            this.listener = listener;
        }
    }
}
