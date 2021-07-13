package com.example.pizzadelicious.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzadelicious.Adapters.CakeAdapter;
import com.example.pizzadelicious.Adapters.PizzaAdapter;
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

public class CakeFragment extends Fragment {
    RecyclerView gridView_cake;
    ArrayList<Product> cakeList;

    ApiInterface service;

    public CakeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setControl(view);

        service = Common.getGsonService();

        loadData();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cake, container, false);
    }

    private void loadData(){
        //Pizza Recycler View
        if (Common.isConnectedToInternet(getActivity().getBaseContext())){
            service.getProductByType("2").enqueue(new Callback<JSONResponseProduct>() {
                @Override
                public void onResponse(Call<JSONResponseProduct> call, Response<JSONResponseProduct> response) {
                    JSONResponseProduct jsonResponseCake = response.body();
                    cakeList = jsonResponseCake.getData();
                    CakeAdapter cakeAdapter  = new CakeAdapter( cakeList, CakeFragment.this);

                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
                    gridView_cake.setLayoutManager(gridLayoutManager);
                    gridView_cake.setAdapter(cakeAdapter);
                    cakeAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<JSONResponseProduct> call, Throwable t) {

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

        gridView_cake = view.findViewById(R.id.gridView_cake);
        gridView_cake.setHasFixedSize(true);
        gridView_cake.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }
}