package com.example.pizzadelicious.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


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

public class PizzaFragment extends Fragment {
    RecyclerView gridView_pizza;
    ArrayList<Product> pizzaList;
    ApiInterface service;

    public PizzaFragment() {
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
        View view = inflater.inflate(R.layout.fragment_pizza, container, false);

        return view;
    }

    private void loadData(){
        //Pizza Recycler View
        if (Common.isConnectedToInternet(getActivity().getBaseContext())){
            service.getProductByType("1").enqueue(new Callback<JSONResponseProduct>() {
                @Override
                public void onResponse(Call<JSONResponseProduct> call, Response<JSONResponseProduct> response) {
                    JSONResponseProduct jsonResponsePizza = response.body();
                    pizzaList = new ArrayList<>(Arrays.asList(jsonResponsePizza.getData()));
                    PizzaAdapter pizzaAdapter  = new PizzaAdapter( pizzaList, PizzaFragment.this);

                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
                    gridView_pizza.setLayoutManager(gridLayoutManager);
                    gridView_pizza.setAdapter(pizzaAdapter);
                    pizzaAdapter.notifyDataSetChanged();
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

        gridView_pizza = view.findViewById(R.id.gridView_pizza);
        gridView_pizza.setHasFixedSize(true);
        gridView_pizza.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

}