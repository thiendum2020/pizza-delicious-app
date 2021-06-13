package com.example.pizzadelicious.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.pizzadelicious.Models.JSONResponseAccounts;
import com.example.pizzadelicious.R;
import com.example.pizzadelicious.Retrofit.ApiInterface;
import com.example.pizzadelicious.Retrofit.Common;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdminInsertAccountFragment extends Fragment {
    ApiInterface service;
    EditText et_username, et_password, et_name, et_email, et_address, et_phone, et_role;
    Button btn_insert;
    ImageButton btn_back;


    public AdminInsertAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        service = Common.getGsonService();
        setControl(view);
        setEvent();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_insert_account, container, false);
    }

    private void setControl(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        if (getActivity() != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        }
        et_username = view.findViewById(R.id.et_username);
        et_password = view.findViewById(R.id.et_password);
        et_name = view.findViewById(R.id.et_name);
        et_email = view.findViewById(R.id.et_email);
        et_address = view.findViewById(R.id.et_address);
        et_phone = view.findViewById(R.id.et_phone);
        et_role = view.findViewById(R.id.et_role);
        btn_insert = view.findViewById(R.id.btn_insert);
        btn_back = view.findViewById(R.id.btn_back);
    }

    private void setEvent() {
        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!et_username.getText().toString().equals("") && !et_password.getText().toString().equals("")
                        && !et_name.getText().toString().equals("") && !et_email.getText().toString().equals("")
                        && !et_address.getText().toString().equals("") && !et_phone.getText().toString().equals("") &&
                        !et_role.getText().toString().equals("")) {
                    Log.d("TAG", "" + et_username.getText() + et_password.getText());
                    //Call API insert User
                    service.postUser("" + et_username.getText().toString(), "" + et_password.getText().toString(),
                            "" + et_name.getText().toString(), "" + et_email.getText().toString(),
                            "" + et_address.getText().toString(), "" + et_phone.getText().toString(),
                            "" + et_role.getText().toString()).enqueue(new Callback<JSONResponseAccounts>() {
                        @Override
                        public void onResponse(Call<JSONResponseAccounts> call, Response<JSONResponseAccounts> response) {
                            Toast.makeText(getActivity(), "Insert Account successfully!", Toast.LENGTH_SHORT).show();
                            Fragment someFragment = new AdminAccountsFragment();
                            FragmentTransaction transaction = AdminInsertAccountFragment.this.getFragmentManager().beginTransaction();
                            transaction.replace(R.id.frameLayout, someFragment); // give your fragment container id in first parameter
                            transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                            transaction.commit();
                        }

                        @Override
                        public void onFailure(Call<JSONResponseAccounts> call, Throwable t) {
                            Toast.makeText(getActivity(), "Insert Account failed!", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Toast.makeText(getActivity(), "Insert Account failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        dialog.dismiss();
                        Fragment someFragment = new AdminAccountsFragment();
                        FragmentTransaction transaction = AdminInsertAccountFragment.this.getFragmentManager().beginTransaction();
                        transaction.replace(R.id.frameLayout, someFragment); // give your fragment container id in first parameter
                        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                        transaction.commit();
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

}