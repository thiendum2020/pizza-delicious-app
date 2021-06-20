package com.example.pizzadelicious.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzadelicious.Fragments.AdminCakeFragment;
import com.example.pizzadelicious.Fragments.AdminEditAccountFragment;
import com.example.pizzadelicious.Fragments.AdminEditProductFragment;
import com.example.pizzadelicious.Fragments.AdminPizzaFragment;
import com.example.pizzadelicious.Models.JSONResponseAccounts;
import com.example.pizzadelicious.Models.JSONResponseProduct;
import com.example.pizzadelicious.Models.Product;
import com.example.pizzadelicious.R;
import com.example.pizzadelicious.Retrofit.ApiInterface;
import com.example.pizzadelicious.Retrofit.Common;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminCakeAdapter extends RecyclerView.Adapter<AdminCakeAdapter.ViewHolder> {
    private ArrayList<Product> list;
    AdminCakeFragment adminCakeFragment;
    private Context context;
    LinearLayout admin_product_item;
    ApiInterface service;

    public AdminCakeAdapter(ArrayList<Product> list, AdminCakeFragment adminCakeFragment) {
        this.list = list;
        this.adminCakeFragment = adminCakeFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(adminCakeFragment.getContext()).inflate(R.layout.admin_product_item, parent, false);
        context = parent.getContext();
        service = Common.getGsonService();
        return new AdminCakeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product model = list.get(position);

        holder.tv_id_item.setText(model.getId());
        holder.tv_name_item.setText(model.getName());
        holder.tv__price_item.setText(model.getPrice());
        Picasso.get().load(model.getImage())
                .into(holder.img_item);

        admin_product_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(adminCakeFragment.getContext(), "Đã chọn: " + model.getName(), Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                Fragment someFragment = new AdminEditProductFragment();
                ///chưa hiểu về id
                bundle.putString("productId", String.valueOf(model.getId()));
                someFragment.setArguments(bundle);
                Toast.makeText(adminCakeFragment.getContext(), "Đã chọn: " + model.getName(), Toast.LENGTH_SHORT).show();
                Log.d("id", "onResponse: " +model.getId().toString());
                FragmentTransaction transaction = adminCakeFragment.getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, someFragment ); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }
        });
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(adminCakeFragment.getContext());
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        dialog.dismiss();
                        service.deleteProduct("" + model.getId()).enqueue(new Callback<JSONResponseProduct>() {
                            @Override
                            public void onResponse(Call<JSONResponseProduct> call, Response<JSONResponseProduct> response) {
                                Toast.makeText(adminCakeFragment.getContext(), "Đã xóa: " + model.getName(), Toast.LENGTH_SHORT).show();
                                adminCakeFragment.getFragmentManager().beginTransaction().detach(adminCakeFragment).attach(adminCakeFragment).commit();
                            }

                            @Override
                            public void onFailure(Call<JSONResponseProduct> call, Throwable t) {
                                Toast.makeText(adminCakeFragment.getContext(), "Xóa thất bại!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_id_item, tv_name_item, tv__price_item;
        ImageButton btn_delete;
        ImageView img_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_id_item = itemView.findViewById(R.id.tv_id_item);
            tv_name_item = itemView.findViewById(R.id.tv_name_item);
            tv__price_item = itemView.findViewById(R.id.tv__price_item);
            img_item = itemView.findViewById(R.id.img_item);
            btn_delete = itemView.findViewById(R.id.btn_delete);

            admin_product_item = itemView.findViewById(R.id.admin_product_item);

        }
    }
}
