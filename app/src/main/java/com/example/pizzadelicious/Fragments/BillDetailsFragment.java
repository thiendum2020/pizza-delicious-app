package com.example.pizzadelicious.Fragments;

import android.graphics.Color;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pizzadelicious.Adapters.BillDetailsAdapter;
import com.example.pizzadelicious.Adapters.CartAdapter;
import com.example.pizzadelicious.Models.BillDetail;
import com.example.pizzadelicious.Models.JSONResponseBill;
import com.example.pizzadelicious.Models.JSONResponseBillDetail;
import com.example.pizzadelicious.R;
import com.example.pizzadelicious.Retrofit.ApiInterface;
import com.example.pizzadelicious.Retrofit.Common;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BillDetailsFragment extends Fragment {
    RecyclerView recyclerView_billDetails;
    ArrayList<BillDetail> billDetails;
    ApiInterface service;
    TextView tv_billId, tv_count_item, tv_name_user, tv_phone_user, tv_address_user, tv_total_bill, tv_status;
    Button btn_received;
    String billId, name, phone, address, total_prices, status;


    public BillDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        service = Common.getGsonService();
        setControl(view);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            billId = bundle.getString("billId");
            name = bundle.getString("name");
            phone = bundle.getString("phone");
            address = bundle.getString("address");
            total_prices = bundle.getString("total_prices");
            status = bundle.getString("status");
            loadData(billId);
        }
        setEvent();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_bill_details, container, false);
    }

    private void loadData(String billId) {
        if (Common.isConnectedToInternet(getActivity().getBaseContext())) {

            service.getBillDetailByBillId("" + billId).enqueue(new Callback<JSONResponseBillDetail>() {
                @Override
                public void onResponse(Call<JSONResponseBillDetail> call, Response<JSONResponseBillDetail> response) {
                    JSONResponseBillDetail jsonResponseBillDetail = response.body();
                    billDetails = jsonResponseBillDetail.getData();
                    tv_count_item.setText("" + billDetails.size());
                    BillDetailsAdapter billDetailsAdapter = new BillDetailsAdapter(billDetails, BillDetailsFragment.this);
                    recyclerView_billDetails.setAdapter(billDetailsAdapter);
                    billDetailsAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<JSONResponseBillDetail> call, Throwable t) {

                }
            });

        } else {
            Toast.makeText(getActivity(), "Please check your internet!!", Toast.LENGTH_SHORT).show();
        }

        tv_billId.setText("" + billId);
        tv_name_user.setText("" + name);
        tv_phone_user.setText("" + phone);
        tv_address_user.setText("" + address);
        tv_total_bill.setText("" + total_prices);
        tv_status.setText("" + status);

        if (tv_status.getText().equals("waiting")) {
            tv_status.setTextColor(Color.parseColor("#039BE5"));
        }
        if (tv_status.getText().equals("delivering")) {
            tv_status.setTextColor(Color.parseColor("#F4511E"));
            btn_received.setVisibility(View.VISIBLE);
        }
        if (tv_status.getText().equals("completed")) {
            tv_status.setTextColor(Color.parseColor("#00FF0B"));
        }

    }

    private void setControl(View view) {

        tv_billId = view.findViewById(R.id.tv_billId);
        tv_total_bill = view.findViewById(R.id.tv_total_bill);
        tv_count_item = view.findViewById(R.id.tv_count_item);
        tv_name_user = view.findViewById(R.id.tv_name_user);
        tv_phone_user = view.findViewById(R.id.tv_phone_user);
        tv_address_user = view.findViewById(R.id.tv_address_user);
        tv_status = view.findViewById(R.id.tv_status);

        btn_received = view.findViewById(R.id.btn_received);

        recyclerView_billDetails = view.findViewById(R.id.recyclerView_billDetails);
        recyclerView_billDetails.setHasFixedSize(true);
        recyclerView_billDetails.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

    private void setEvent() {
        btn_received.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                service.receivedBill("" + billId, "completed").enqueue(new Callback<JSONResponseBill>() {
                    @Override
                    public void onResponse(Call<JSONResponseBill> call, Response<JSONResponseBill> response) {
                        Toast.makeText(getContext(), "Completed", Toast.LENGTH_SHORT).show();
                        Fragment someFragment = new OrderHistoryFragment();
                        FragmentTransaction transaction = BillDetailsFragment.this.getFragmentManager().beginTransaction();
                        transaction.replace(R.id.frameLayout, someFragment); // give your fragment container id in first parameter
                        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                        transaction.commit();
                    }

                    @Override
                    public void onFailure(Call<JSONResponseBill> call, Throwable t) {

                    }
                });
            }
        });
    }
}