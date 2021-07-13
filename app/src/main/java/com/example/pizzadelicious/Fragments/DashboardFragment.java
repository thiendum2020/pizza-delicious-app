package com.example.pizzadelicious.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pizzadelicious.MainActivity;
import com.example.pizzadelicious.Models.JSONResponseAccounts;
import com.example.pizzadelicious.Models.JSONResponseBill;
import com.example.pizzadelicious.Models.JSONResponseProduct;
import com.example.pizzadelicious.Models.User;
import com.example.pizzadelicious.R;
import com.example.pizzadelicious.Retrofit.ApiInterface;
import com.example.pizzadelicious.Retrofit.Common;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DashboardFragment extends Fragment {
    LinearLayout layout_pizza, layout_cake, layout_orders, layout_accounts;
    ImageButton btn_back_to_home;
    TextView tv_name_admin, tv_count_accounts, tv_count_pizza, tv_count_cake, tv_count_orders;
    ArrayList<User> accountList;
    ApiInterface service;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        service = Common.getGsonService();
        setControl(view);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    private void setControl(View view) {

        layout_pizza = view.findViewById(R.id.layout_pizza);
        layout_cake = view.findViewById(R.id.layout_cake);
        layout_orders = view.findViewById(R.id.layout_orders);
        layout_accounts = view.findViewById(R.id.layout_accounts);
        tv_count_accounts = view.findViewById(R.id.tv_count_accounts);
        tv_count_pizza = view.findViewById(R.id.tv_count_pizza);
        tv_count_cake = view.findViewById(R.id.tv_count_cake);
        tv_count_orders = view.findViewById(R.id.tv_count_orders);

        btn_back_to_home = view.findViewById(R.id.btn_back_to_home);
        tv_name_admin = view.findViewById(R.id.tv_name_admin);
        tv_name_admin.setText(Common.currentUser.getName());

        service.getAllUser().enqueue(new Callback<JSONResponseAccounts>() {
            @Override
            public void onResponse(Call<JSONResponseAccounts> call, Response<JSONResponseAccounts> response) {
                JSONResponseAccounts jsonResponseAccounts = response.body();
                String count_accounts = jsonResponseAccounts.getData().size() + "";
                tv_count_accounts.setText(count_accounts);
            }

            @Override
            public void onFailure(Call<JSONResponseAccounts> call, Throwable t) {

            }
        });
        service.getProductByType("1").enqueue(new Callback<JSONResponseProduct>() {
            @Override
            public void onResponse(Call<JSONResponseProduct> call, Response<JSONResponseProduct> response) {
                JSONResponseProduct jsonResponseProduct = response.body();
                String count_pizza = jsonResponseProduct.getData().size() + "";
                tv_count_pizza.setText(count_pizza);
            }

            @Override
            public void onFailure(Call<JSONResponseProduct> call, Throwable t) {

            }
        });
        service.getProductByType("2").enqueue(new Callback<JSONResponseProduct>() {
            @Override
            public void onResponse(Call<JSONResponseProduct> call, Response<JSONResponseProduct> response) {
                JSONResponseProduct jsonResponseProduct = response.body();
                String count_cake = jsonResponseProduct.getData().size()+ "";
                tv_count_cake.setText(count_cake);
            }

            @Override
            public void onFailure(Call<JSONResponseProduct> call, Throwable t) {

            }
        });
        service.getAllBill().enqueue(new Callback<JSONResponseBill>() {
            @Override
            public void onResponse(Call<JSONResponseBill> call, Response<JSONResponseBill> response) {
                JSONResponseBill jsonResponseBill = response.body();
                String count_order = jsonResponseBill.getData().size() + "";
                tv_count_orders.setText(count_order);
            }

            @Override
            public void onFailure(Call<JSONResponseBill> call, Throwable t) {

            }
        });

        btn_back_to_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });

        layout_pizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment someFragment = new AdminPizzaFragment();
                FragmentTransaction transaction = DashboardFragment.this.getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, someFragment); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }
        });
        layout_cake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment someFragment = new AdminCakeFragment();
                FragmentTransaction transaction = DashboardFragment.this.getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, someFragment); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }
        });
        layout_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment someFragment = new AdminOrdersFragment();
                FragmentTransaction transaction = DashboardFragment.this.getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, someFragment); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }
        });
        layout_accounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment someFragment = new AdminAccountsFragment();
                FragmentTransaction transaction = DashboardFragment.this.getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, someFragment); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }
        });

    }
}