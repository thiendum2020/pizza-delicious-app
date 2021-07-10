package com.example.pizzadelicious.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.pizzadelicious.Adapters.AccountAdapter;
import com.example.pizzadelicious.Adapters.OrderHistoryAdapter;
import com.example.pizzadelicious.Models.Bill;
import com.example.pizzadelicious.Models.JSONResponseAccounts;
import com.example.pizzadelicious.Models.JSONResponseBill;
import com.example.pizzadelicious.Models.User;
import com.example.pizzadelicious.R;
import com.example.pizzadelicious.Retrofit.ApiInterface;
import com.example.pizzadelicious.Retrofit.Common;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderHistoryFragment extends Fragment {
    RecyclerView recyclerView_orderHistory;
    ArrayList<Bill> bills;
    ApiInterface service;
    ImageButton btn_back;
    public OrderHistoryFragment() {
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
        return inflater.inflate(R.layout.fragment_order_history, container, false);
    }

    private void loadData() {
        //Pizza Recycler View
        if (Common.isConnectedToInternet(getActivity().getBaseContext())){
            service.getBillByUserId(""+Common.currentUser.getId()).enqueue(new Callback<JSONResponseBill>() {
                @Override
                public void onResponse(Call<JSONResponseBill> call, Response<JSONResponseBill> response) {
                    JSONResponseBill jsonResponseBill = response.body();
                    bills = jsonResponseBill.getData();

                    OrderHistoryAdapter orderHistoryAdapter = new OrderHistoryAdapter( bills, OrderHistoryFragment.this);

                    recyclerView_orderHistory.setAdapter(orderHistoryAdapter);
                    orderHistoryAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<JSONResponseBill> call, Throwable t) {

                }
            });
        }else {
            Toast.makeText(getActivity(), "Please check your internet!!", Toast.LENGTH_SHORT).show();
        }
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment someFragment = new ProfileFragment();
                FragmentTransaction transaction = OrderHistoryFragment.this.getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, someFragment); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }
        });
    }

    private void setControl(View view) {
        btn_back = view.findViewById(R.id.btn_back);
        recyclerView_orderHistory = view.findViewById(R.id.recyclerView_orderHistory);
        recyclerView_orderHistory.setHasFixedSize(true);
        recyclerView_orderHistory.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

}