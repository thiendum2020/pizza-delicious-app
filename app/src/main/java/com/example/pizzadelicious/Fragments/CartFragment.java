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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pizzadelicious.Adapters.AccountAdapter;
import com.example.pizzadelicious.Adapters.BillDetailAdapter;
import com.example.pizzadelicious.Models.BillDetail;
import com.example.pizzadelicious.Models.JSONResponseAccounts;
import com.example.pizzadelicious.Models.JSONResponseBill;
import com.example.pizzadelicious.Models.JSONResponseBillDetail;
import com.example.pizzadelicious.R;
import com.example.pizzadelicious.Retrofit.ApiInterface;
import com.example.pizzadelicious.Retrofit.Common;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CartFragment extends Fragment {
    RecyclerView recyclerView_cart;
    ArrayList<BillDetail> billDetails;
    ApiInterface service;
    TextView tv_total_bill, tv_count_item;
    Button btn_place_order;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        service = Common.getGsonService();
        setControl(view);
        loadData();
        setEvent();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    private void loadData() {
        if (Common.isConnectedToInternet(getActivity().getBaseContext())) {
            Common.totalBill = 0;
            if (Common.currentBill == null) {
                Toast.makeText(getActivity(), "Giỏ hàng trống!", Toast.LENGTH_SHORT).show();
            } else {
                service.getBillDetailByBillId("" + Common.currentBill.getId()).enqueue(new Callback<JSONResponseBillDetail>() {
                    @Override
                    public void onResponse(Call<JSONResponseBillDetail> call, Response<JSONResponseBillDetail> response) {
                        JSONResponseBillDetail jsonResponseBillDetail = response.body();
                        billDetails = new ArrayList<>(Arrays.asList(jsonResponseBillDetail.getData()));
                        Log.e("status ", "" + jsonResponseBillDetail.getData().length);

                        BillDetailAdapter billDetailAdapter = new BillDetailAdapter(billDetails, CartFragment.this);
                        recyclerView_cart.setAdapter(billDetailAdapter);
                        billDetailAdapter.notifyDataSetChanged();
                        for (int i = 0; i < billDetails.size(); i++) {
                            Log.e("prices " + i, "" + billDetails.get(i).getPrices());
                            Common.totalBill = Common.totalBill + Integer.parseInt(billDetails.get(i).getPrices());
                        }

                        Log.e("total ", Common.totalBill.toString());
                        tv_total_bill.setText(Common.totalBill.toString());
                    }

                    @Override
                    public void onFailure(Call<JSONResponseBillDetail> call, Throwable t) {

                    }
                });
            }
        } else {
            Toast.makeText(getActivity(), "Please check your internet!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void setControl(View view) {
//        Toolbar toolbar = view.findViewById(R.id.toolbar);
//        if (getActivity() != null) {
//            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//        }
        tv_total_bill = view.findViewById(R.id.tv_total_bill);
        tv_count_item = view.findViewById(R.id.tv_count_item);
        btn_place_order = view.findViewById(R.id.btn_place_order);

        recyclerView_cart = view.findViewById(R.id.recyclerView_cart);
        recyclerView_cart.setHasFixedSize(true);
        recyclerView_cart.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

    private void setEvent() {
        if(Common.currentBill != null){
            tv_count_item.setText(billDetails.size());
        }

        btn_place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Common.currentBill == null) {
                    Toast.makeText(getContext(), "Are you kidding me?! Thêm hàng vô đi rồi đặt thoải mái!", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < billDetails.size(); i++) {
                        service.updateBillDetail("" + billDetails.get(i).getId(), "" + billDetails.get(i).getQuantity(),
                                "" + billDetails.get(i).getPrices()).enqueue(new Callback<JSONResponseBillDetail>() {
                            @Override
                            public void onResponse(Call<JSONResponseBillDetail> call, Response<JSONResponseBillDetail> response) {
                                Log.e("update sp", "OK!");
                            }

                            @Override
                            public void onFailure(Call<JSONResponseBillDetail> call, Throwable t) {

                            }
                        });
                    }
                    service.updateBill("" + Common.currentBill.getId(), "waiting", "" + tv_total_bill.getText()).enqueue(new Callback<JSONResponseBill>() {
                        @Override
                        public void onResponse(Call<JSONResponseBill> call, Response<JSONResponseBill> response) {
                            Log.e("update bill", "OK!");
                            Toast.makeText(getContext(), "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();
                            billDetails.clear();
                            Common.currentBill = null;
                            Log.e("delete cart", "" + billDetails.size());
                            CartFragment.this.getFragmentManager().beginTransaction().detach(CartFragment.this).attach(CartFragment.this).commit();
                        }

                        @Override
                        public void onFailure(Call<JSONResponseBill> call, Throwable t) {

                        }
                    });
                }

            }
        });
    }
}