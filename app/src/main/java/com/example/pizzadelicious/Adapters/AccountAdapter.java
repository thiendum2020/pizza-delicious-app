package com.example.pizzadelicious.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzadelicious.Fragments.AdminAccountsFragment;
import com.example.pizzadelicious.Fragments.AdminEditAccountFragment;
import com.example.pizzadelicious.Fragments.ProductDetailFragment;
import com.example.pizzadelicious.Models.JSONResponseAccounts;
import com.example.pizzadelicious.Models.User;
import com.example.pizzadelicious.OnBoardActivity;
import com.example.pizzadelicious.R;
import com.example.pizzadelicious.Retrofit.ApiInterface;
import com.example.pizzadelicious.Retrofit.Common;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.ViewHolder> {


    private ArrayList<User> list;
    AdminAccountsFragment adminAccountsFragment;
    private Context context;
    LinearLayout account_item;
    ApiInterface service;


    public AccountAdapter(ArrayList<User> list, AdminAccountsFragment adminAccountsFragment) {
        this.list = list;
        this.adminAccountsFragment = adminAccountsFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(adminAccountsFragment.getContext()).inflate(R.layout.account_item, parent, false);
        context = parent.getContext();
        service = Common.getGsonService();
        return new AccountAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountAdapter.ViewHolder holder, int position) {
        User model = list.get(position);

//        holder.tv_id.setText(model.getId());
        holder.tv_name.setText(model.getName());
        holder.tv_email.setText(model.getEmail());
        holder.tv_role.setText(model.getRole());

        account_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Fragment someFragment = new AdminEditAccountFragment();
                bundle.putString("accountId", String.valueOf(model.getId()));
                someFragment.setArguments(bundle);
                Toast.makeText(adminAccountsFragment.getContext(), "Đã chọn: " + model.getName(), Toast.LENGTH_SHORT).show();
                Log.d("id", "onResponse: " +model.getId().toString());
                FragmentTransaction transaction = adminAccountsFragment.getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, someFragment ); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }
        });

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(adminAccountsFragment.getContext());
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        dialog.dismiss();
                        service.deleteUser("" +model.getId()).enqueue(new Callback<JSONResponseAccounts>() {
                            @Override
                            public void onResponse(Call<JSONResponseAccounts> call, Response<JSONResponseAccounts> response) {
                                Toast.makeText(adminAccountsFragment.getContext(), "Đã xóa: " + model.getName(), Toast.LENGTH_SHORT).show();
                                adminAccountsFragment.getFragmentManager().beginTransaction().detach(adminAccountsFragment).attach(adminAccountsFragment).commit();
                            }

                            @Override
                            public void onFailure(Call<JSONResponseAccounts> call, Throwable t) {

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
        TextView tv_id, tv_name, tv_email, tv_role;
        ImageButton btn_delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.tv_id);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_email = itemView.findViewById(R.id.tv_email);
            tv_role = itemView.findViewById(R.id.tv_role);
            btn_delete = itemView.findViewById(R.id.btn_delete);

            account_item = itemView.findViewById(R.id.account_item);
        }
    }
}
