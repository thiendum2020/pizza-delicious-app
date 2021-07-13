package com.example.pizzadelicious.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.pizzadelicious.Adapters.AdminCakeAdapter;
import com.example.pizzadelicious.Models.JSONResponseProduct;
import com.example.pizzadelicious.Models.Product;
import com.example.pizzadelicious.R;
import com.example.pizzadelicious.Retrofit.ApiInterface;
import com.example.pizzadelicious.Retrofit.Common;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminCakeFragment extends Fragment {
    RecyclerView recyclerView_cake;
    ArrayList<Product> cakeList;
    ApiInterface service;
    ImageButton btn_back, btn_add;
    public AdminCakeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        service = Common.getGsonService();
        setControl(view);
        loadData();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_cake, container, false);
    }

    private void loadData() {
        //Pizza Recycler View
        if (Common.isConnectedToInternet(getActivity().getBaseContext())) {
            service.getProductByType("2").enqueue(new Callback<JSONResponseProduct>() {
                @Override
                public void onResponse(Call<JSONResponseProduct> call, Response<JSONResponseProduct> response) {
                    JSONResponseProduct jsonResponseCake = response.body();
                    cakeList = jsonResponseCake.getData();
                    AdminCakeAdapter adminCakeAdapter = new AdminCakeAdapter(cakeList, AdminCakeFragment.this);
                    recyclerView_cake.setAdapter(adminCakeAdapter);
                    adminCakeAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<JSONResponseProduct> call, Throwable t) {

                }
            });
        } else {
            Toast.makeText(getActivity(), "Please check your internet!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void setControl(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        if (getActivity() != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        }

        recyclerView_cake = view.findViewById(R.id.recyclerView_cake);
        recyclerView_cake.setHasFixedSize(true);
        recyclerView_cake.setLayoutManager(new LinearLayoutManager(view.getContext()));
        btn_back = view.findViewById(R.id.btn_back);
        btn_add = view.findViewById(R.id.btn_add);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment someFragment = new AdminInsertProductFragment();
                FragmentTransaction transaction = AdminCakeFragment.this.getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, someFragment); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment someFragment = new DashboardFragment();
                FragmentTransaction transaction = AdminCakeFragment.this.getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, someFragment); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }
        });

    }
}