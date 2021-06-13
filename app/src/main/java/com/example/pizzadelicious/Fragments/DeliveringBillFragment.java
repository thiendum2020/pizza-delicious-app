package com.example.pizzadelicious.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pizzadelicious.Adapters.DeliveringBillAdapter;
import com.example.pizzadelicious.Adapters.WaitingBillAdapter;
import com.example.pizzadelicious.Models.Bill;
import com.example.pizzadelicious.Models.JSONResponseBill;
import com.example.pizzadelicious.R;
import com.example.pizzadelicious.Retrofit.ApiInterface;
import com.example.pizzadelicious.Retrofit.Common;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveringBillFragment extends Fragment {
    RecyclerView recyclerView_deliveringBill;
    ArrayList<Bill> bills;
    ApiInterface service;

    public DeliveringBillFragment() {
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
        return inflater.inflate(R.layout.fragment_delivering_bill, container, false);
    }

    private void loadData() {
        //Pizza Recycler View
        if (Common.isConnectedToInternet(getActivity().getBaseContext())){
            service.getBillByNoteDelivering().enqueue(new Callback<JSONResponseBill>() {
                @Override
                public void onResponse(Call<JSONResponseBill> call, Response<JSONResponseBill> response) {
                    JSONResponseBill jsonResponseBill = response.body();
                    bills = jsonResponseBill.getData();
                    DeliveringBillAdapter deliveringBillAdapter  = new DeliveringBillAdapter( bills, DeliveringBillFragment.this);

                    recyclerView_deliveringBill.setAdapter(deliveringBillAdapter);
                    deliveringBillAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<JSONResponseBill> call, Throwable t) {

                }
            });
        }else {
            Toast.makeText(getActivity(), "Please check your internet!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void setControl(View view) {
        //        Toolbar toolbar = view.findViewById(R.id.toolbar);
//        if (getActivity() != null) {
//            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//        }

        recyclerView_deliveringBill = view.findViewById(R.id.recyclerView_deliveringBill);
        recyclerView_deliveringBill.setHasFixedSize(true);
        recyclerView_deliveringBill.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }
}