package com.example.pizzadelicious.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


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
        holder.tv_address.setText(""+model.getUser().getAddress());

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
                Toast.makeText(orderHistoryFragment.getContext(), "Selected " + model.getId(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_id, tv_note, tv_date, tv_prices, tv_name, tv_address;
        Button btn_received;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.tv_id);
            tv_note = itemView.findViewById(R.id.tv_note);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_prices = itemView.findViewById(R.id.tv_prices);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_address = itemView.findViewById(R.id.tv_address);
            btn_received = itemView.findViewById(R.id.btn_received);

            order_history_item = itemView.findViewById(R.id.order_history_item);
        }
    }
}
