package com.example.pizzadelicious.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pizzadelicious.Adapters.AccountAdapter;
import com.example.pizzadelicious.Adapters.PizzaAdapter;
import com.example.pizzadelicious.Models.JSONResponseAccounts;
import com.example.pizzadelicious.Models.JSONResponseUser;
import com.example.pizzadelicious.Models.User;
import com.example.pizzadelicious.R;
import com.example.pizzadelicious.Retrofit.ApiInterface;
import com.example.pizzadelicious.Retrofit.Common;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdminAccountsFragment extends Fragment {
    RecyclerView recyclerView_accounts;
    ArrayList<User> accountList;
    ApiInterface service;

    public AdminAccountsFragment() {
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
        return inflater.inflate(R.layout.fragment_admin_accounts, container, false);
    }

    private void loadData() {
        //Pizza Recycler View
        if (Common.isConnectedToInternet(getActivity().getBaseContext())){
            service.getAllUser().enqueue(new Callback<JSONResponseAccounts>() {
                @Override
                public void onResponse(Call<JSONResponseAccounts> call, Response<JSONResponseAccounts> response) {
                    JSONResponseAccounts jsonResponseAccounts = response.body();
                    accountList = new ArrayList<>(Arrays.asList(jsonResponseAccounts.getData()));

                    Log.e("status ",""+jsonResponseAccounts.getData().length);

                    AccountAdapter accountAdapter  = new AccountAdapter( accountList, AdminAccountsFragment.this);

                    recyclerView_accounts.setAdapter(accountAdapter);
                    accountAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<JSONResponseAccounts> call, Throwable t) {

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

        recyclerView_accounts = view.findViewById(R.id.recyclerView_accounts);
        recyclerView_accounts.setHasFixedSize(true);
        recyclerView_accounts.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }
}