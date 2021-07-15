package com.example.pizzadelicious.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;


import com.example.pizzadelicious.Fragments.BillDetailsFragment;
import com.example.pizzadelicious.Fragments.ProductDetailFragment;
import com.example.pizzadelicious.Models.Bill;
import com.example.pizzadelicious.Fragments.OrderHistoryFragment;
import com.example.pizzadelicious.Models.JSONResponseBill;
import com.example.pizzadelicious.R;
import com.example.pizzadelicious.Retrofit.ApiInterface;
import com.example.pizzadelicious.Retrofit.Common;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder> {

    ApiInterface service;
    private ArrayList<Bill> list;
    OrderHistoryFragment orderHistoryFragment;
    private Context context;
    LinearLayout order_history_item;

    public OrderHistoryAdapter(ArrayList<Bill> list, OrderHistoryFragment orderHistoryFragment) {
        this.list = list;
        this.orderHistoryFragment = orderHistoryFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(orderHistoryFragment.getContext()).inflate(R.layout.order_history_item, parent, false);
        context = parent.getContext();
        service = Common.getGsonService();
        return new OrderHistoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryAdapter.ViewHolder holder, int position) {
        Bill model = list.get(position);

        holder.tv_id.setText(""+model.getId());
        holder.tv_note.setText(""+model.getNote());
        holder.tv_date.setText(""+model.getDate());
        holder.tv_prices.setText(""+model.getPrices());
        holder.tv_name.setText(""+model.getUser().getName());
        holder.tv_phone.setText(""+model.getUser().getPhone());
        holder.tv_address.setText(""+model.getUser().getAddress());

        if(holder.tv_note.getText().equals("waiting")){
            holder.tv_note.setTextColor(Color.parseColor("#039BE5"));
        }
        if(holder.tv_note.getText().equals("delivering")){
            holder.tv_note.setTextColor(Color.parseColor("#F4511E"));
        }
        if(holder.tv_note.getText().equals("completed")){
            holder.tv_note.setTextColor(Color.parseColor("#00FF0B"));
        }

        if(model.getNote().equals("delivering")){
            holder.btn_received.setVisibility(View.VISIBLE);
            holder.btn_received.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    service.receivedBill(""+ model.getId(), "completed").enqueue(new Callback<JSONResponseBill>() {
                        @Override
                        public void onResponse(Call<JSONResponseBill> call, Response<JSONResponseBill> response) {
                            Toast.makeText(orderHistoryFragment.getContext(), "Completed", Toast.LENGTH_SHORT).show();
                            orderHistoryFragment.getFragmentManager().beginTransaction().detach(orderHistoryFragment).attach(orderHistoryFragment).commit();
                        }

                        @Override
                        public void onFailure(Call<JSONResponseBill> call, Throwable t) {

                        }
                    });
                }
            });
        }
        order_history_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Fragment someFragment = new BillDetailsFragment();
                bundle.putString("billId", String.valueOf(list.get(position).getId()));
                bundle.putString("name", holder.tv_name.getText().toString());
                bundle.putString("phone", holder.tv_phone.getText().toString());
                bundle.putString("address", holder.tv_address.getText().toString());
                bundle.putString("total_prices", holder.tv_prices.getText().toString());
                bundle.putString("status", holder.tv_note.getText().toString());

                someFragment.setArguments(bundle);

                FragmentTransaction transaction = orderHistoryFragment.getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, someFragment ); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_id, tv_note, tv_date, tv_prices, tv_name, tv_phone, tv_address;
        Button btn_received;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.tv_id);
            tv_note = itemView.findViewById(R.id.tv_note);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_prices = itemView.findViewById(R.id.tv_prices);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_phone = itemView.findViewById(R.id.tv_phone);
            tv_address = itemView.findViewById(R.id.tv_address);
            btn_received = itemView.findViewById(R.id.btn_received);

            order_history_item = itemView.findViewById(R.id.order_history_item);
        }
    }
}
