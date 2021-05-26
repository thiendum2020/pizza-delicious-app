package com.example.pizzadelicious.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzadelicious.Fragments.CakeFragment;
import com.example.pizzadelicious.Models.BillDetail;
import com.example.pizzadelicious.Models.JSONResponseBill;
import com.example.pizzadelicious.Models.JSONResponseBillDetail;
import com.example.pizzadelicious.Models.Product;
import com.example.pizzadelicious.R;
import com.example.pizzadelicious.Retrofit.ApiInterface;
import com.example.pizzadelicious.Retrofit.Common;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CakeAdapter extends RecyclerView.Adapter<CakeAdapter.ViewHolder> {

    private ArrayList<Product> list;
    CakeFragment cakeFragment;
    private Context context;
    ApiInterface service;
    ConstraintLayout product_item;

    public CakeAdapter(ArrayList<Product> list, CakeFragment cakeFragment) {
        this.list = list;
        this.cakeFragment = cakeFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(cakeFragment.getContext()).inflate(R.layout.product_item, parent, false);
        context = parent.getContext();
        service = Common.getGsonService();
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CakeAdapter.ViewHolder holder, int position) {
        Product model = list.get(position);

        Picasso.get().load(model.getImage())
                .into(holder.product_img);
        holder.product_name.setText(model.getName());
        holder.product_price.setText(model.getPrice());

        product_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(cakeFragment.getContext(), "Đã chọn: " + model.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.btn_addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(cakeFragment.getContext(), "Đã thêm vào giỏi hàng thành công!", Toast.LENGTH_SHORT).show();

                if (Common.currentBill == null) {
                    service.postBill("0", "created", "" + Calendar.getInstance().getTime(), "" + Common.currentUser.getId()).enqueue(new Callback<JSONResponseBill>() {
                        @Override
                        public void onResponse(Call<JSONResponseBill> call, Response<JSONResponseBill> response) {
                            Log.d("bill_ok ", "Create Bill OK!");
                            JSONResponseBill jsonResponseBill = response.body();
                            Common.currentBill = jsonResponseBill.getData().get(0);

                            service.postBillDetail("1", "" + model.getPrice(), "" + model.getId(), "" + Common.currentBill.getId()).enqueue(new Callback<JSONResponseBillDetail>() {
                                @Override
                                public void onResponse(Call<JSONResponseBillDetail> call, Response<JSONResponseBillDetail> response) {
                                    Log.e("status ", "Create Bill Detail OK!");
//                                    JSONResponseBillDetail jsonResponseBillDetail = response.body();
//                                    listDetail = new ArrayList<>(Arrays.asList(jsonResponseBillDetail.getData()));
//                                    Common.totalBill = Common.totalBill + Integer.parseInt(listDetail.get(0).getPrices());
                                }

                                @Override
                                public void onFailure(Call<JSONResponseBillDetail> call, Throwable t) {

                                }
                            });
                        }

                        @Override
                        public void onFailure(Call<JSONResponseBill> call, Throwable t) {

                            Log.d("bill_fail ", "Failed");
                        }
                    });

                } else {
                    service.postBillDetail("1", "" + model.getPrice(), "" + model.getId(), "" + Common.currentBill.getId()).enqueue(new Callback<JSONResponseBillDetail>() {
                        @Override
                        public void onResponse(Call<JSONResponseBillDetail> call, Response<JSONResponseBillDetail> response) {
                            Log.e("status ", "Create Bill Detail OK!");
                        }

                        @Override
                        public void onFailure(Call<JSONResponseBillDetail> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView product_img;
        TextView product_name, product_description, product_price;
        Button btn_addToCart;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            product_img = itemView.findViewById(R.id.product_img);
            product_name = itemView.findViewById(R.id.product_name);
//            product_description = itemView.findViewById(R.id.product_description);
            product_price = itemView.findViewById(R.id.product_price);
            btn_addToCart = itemView.findViewById(R.id.btn_addToCart);
            product_item = itemView.findViewById(R.id.product_item);
        }
    }
}
