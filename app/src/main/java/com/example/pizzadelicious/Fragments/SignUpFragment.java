package com.example.pizzadelicious.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pizzadelicious.AdminActivity;
import com.example.pizzadelicious.FragmentReplaceActivity;
import com.example.pizzadelicious.MainActivity;
import com.example.pizzadelicious.Models.JSONResponseAccounts;
import com.example.pizzadelicious.Models.JSONResponseUser;
import com.example.pizzadelicious.R;
import com.example.pizzadelicious.Retrofit.ApiInterface;
import com.example.pizzadelicious.Retrofit.Common;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignUpFragment extends Fragment {
    private TextView tv_login;
    private EditText et_username, et_password, et_name, et_email, et_address, et_phone;
    private Button btn_signUp;
    ApiInterface service;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setControl(view);
        setEvent();
    }

    private void setControl(View view) {
        tv_login = view.findViewById(R.id.tv_login);
        et_username = view.findViewById(R.id.et_username);
        et_password = view.findViewById(R.id.et_password);
        et_name = view.findViewById(R.id.et_name);
        et_email = view.findViewById(R.id.et_email);
        et_address = view.findViewById(R.id.et_address);
        et_phone = view.findViewById(R.id.et_phone);
        btn_signUp = view.findViewById(R.id.btn_signUp);

    }

    private void setEvent() {
        service = Common.getGsonService();

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentReplaceActivity) getActivity()).setFragment(new SignInFragment());
            }
        });

        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();
                String name = et_name.getText().toString();
                String email = et_email.getText().toString();
                String address = et_address.getText().toString();
                String phone = et_phone.getText().toString();
                if (username.trim().equals("")) {
                    Toast.makeText(getContext(), "Username không được để trống!", Toast.LENGTH_SHORT).show();
                }
                if (password.trim().equals("")) {
                    Toast.makeText(getContext(), "Password không được để trống!", Toast.LENGTH_SHORT).show();
                }
                if (name.trim().equals("")) {
                    Toast.makeText(getContext(), "Name không được để trống!", Toast.LENGTH_SHORT).show();
                }
                if (email.trim().equals("")) {
                    Toast.makeText(getContext(), "Email không được để trống!", Toast.LENGTH_SHORT).show();
                }
                if (address.trim().equals("")) {
                    Toast.makeText(getContext(), "Address không được để trống!", Toast.LENGTH_SHORT).show();
                }
                if (phone.trim().equals("")) {
                    Toast.makeText(getContext(), "Phone không được để trống!", Toast.LENGTH_SHORT).show();
                }

                if (Common.isConnectedToInternet(getActivity().getBaseContext())) {
                    final ProgressDialog progressDialog;
                    progressDialog = new ProgressDialog(getContext());
                    progressDialog.setMessage("Waiting...");
                    progressDialog.show();

                    service.postUser(""+username, ""+password, ""+name, ""+email, ""+address, ""+phone, "customer").enqueue(new Callback<JSONResponseAccounts>() {
                        @Override
                        public void onResponse(Call<JSONResponseAccounts> call, Response<JSONResponseAccounts> response) {
                            progressDialog.dismiss();
                            Log.d("sign up", "onResponse: " + response.body());
                            Toast.makeText(getContext(), "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                            ((FragmentReplaceActivity) getActivity()).setFragment(new SignInFragment());
                           
                        }

                        @Override
                        public void onFailure(Call<JSONResponseAccounts> call, Throwable t) {

                            Toast.makeText(getContext(), "Đăng ký Không thành công. Vui lòng kiểm tra lại thông tin!", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
                } else {
                    Toast.makeText(getActivity().getBaseContext(), "Vui lòng kiểm tra lại kết nối!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}