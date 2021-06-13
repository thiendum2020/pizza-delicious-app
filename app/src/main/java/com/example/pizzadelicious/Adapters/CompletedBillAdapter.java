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

import com.example.pizzadelicious.Fragments.CompletedBillFragment;
import com.example.pizzadelicious.Models.Bill;
import com.example.pizzadelicious.R;
import com.example.pizzadelicious.Retrofit.ApiInterface;
import com.example.pizzadelicious.Retrofit.Common;

import java.util.ArrayList;

public class CompletedBillAdapter extends RecyclerView.Adapter<CompletedBillAdapter.ViewHolder> {

    ApiInterface service;
    private ArrayList<Bill> list;
    CompletedBillFragment completedBillFragment;
    private Context context;
    LinearLayout bill_item;

    public CompletedBillAdapter(ArrayList<Bill> list, CompletedBillFragment completedBillFragment) {
        this.list = list;
        this.completedBillFragment = completedBillFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(completedBillFragment.getContext()).inflate(R.layout.bill_item, parent, false);
        context = parent.getContext();
        service = Common.getGsonService();
        return new CompletedBillAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompletedBillAdapter.ViewHolder holder, int position) {
        Bill model = list.get(position);

        holder.tv_id.setText("" + model.getId());
        holder.tv_note.setText("" + model.getNote());
        holder.tv_date.setText("" + model.getDate());
        holder.tv_prices.setText("" + model.getPrices());
        holder.tv_name.setText("" + model.getUser().getName());
        holder.tv_address.setText("" + model.getUser().getAddress());

//        if (model.getNote().equals("waiting")) {
//            holder.btn_accept.setVisibility(View.VISIBLE);
//
//            holder.btn_accept.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    service.deliveringBill("" + model.getId(), "delivering").enqueue(new Callback<JSONResponseBill>() {
//                        @Override
//                        public void onResponse(Call<JSONResponseBill> call, Response<JSONResponseBill> response) {
//                            Toast.makeText(deliveringBillFragment.getContext(), "Delivering!", Toast.LENGTH_SHORT).show();
//                            deliveringBillFragment.getFragmentManager().beginTransaction().detach(deliveringBillFragment).attach(deliveringBillFragment).commit();
//                        }
//
//                        @Override
//                        public void onFailure(Call<JSONResponseBill> call, Throwable t) {
//
//                        }
//                    });
//                }
//            });
//        }
        bill_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(completedBillFragment.getContext(), "Selected: " + model.getId(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_id, tv_note, tv_date, tv_prices, tv_name, tv_address;
        Button btn_accept;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.tv_id);
            tv_note = itemView.findViewById(R.id.tv_note);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_prices = itemView.findViewById(R.id.tv_prices);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_address = itemView.findViewById(R.id.tv_address);
//            btn_accept = itemView.findViewById(R.id.btn_accept);

            bill_item = itemView.findViewById(R.id.bill_item);
        }
    }
}
