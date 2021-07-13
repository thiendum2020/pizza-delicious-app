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
import com.example.pizzadelicious.Models.JSONResponseUser;
import com.example.pizzadelicious.R;
import com.example.pizzadelicious.Retrofit.ApiInterface;
import com.example.pizzadelicious.Retrofit.Common;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInFragment extends Fragment {

    private TextView tv_create;
    private EditText et_username, et_password;
    private Button btn_signIn;

    ApiInterface service;

    public SignInFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setControl(view);
        setEvent();
    }

    private void setControl(View view) {
        tv_create = view.findViewById(R.id.tv_create);
        et_username = view.findViewById(R.id.et_username);
        et_password = view.findViewById(R.id.et_password);
        btn_signIn = view.findViewById(R.id.btn_signIn);

    }

    private void setEvent() {
        service = Common.getGsonService();

        tv_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentReplaceActivity) getActivity()).setFragment(new SignUpFragment());
            }
        });

        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();


                if(Common.isConnectedToInternet(getActivity().getBaseContext())){
                    ProgressDialog mDialog = new ProgressDialog(getActivity().getBaseContext().getApplicationContext());

                    service.getUserLogin(username, password).enqueue(new Callback<JSONResponseUser>() {
                        @Override
                        public void onResponse(Call<JSONResponseUser> call, Response<JSONResponseUser> response) {
                            Log.d("loginF", ""+ response.body().getStatus());
                            if(Integer.parseInt(response.body().getStatus())==1){
                                Toast.makeText(getContext(), "Tên tài khoản chưa được đăng ký!", Toast.LENGTH_SHORT).show();

                            }
                            Log.d("loginA", ""+ response.body().getStatus());
                            if(Integer.parseInt(response.body().getStatus())==1){
                                Toast.makeText(getContext(), "Sai mật khẩu!", Toast.LENGTH_SHORT).show();
                            }

                            if(Integer.parseInt(response.body().getStatus())==1){
                                Common.currentUser = response.body().getData();
                                Common.currentBill = null;
                                Toast.makeText(getContext(), "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();


                                if(Common.currentUser.getRole().equals("admin")){
                                    Log.d("role", "" + Common.currentUser.getRole());
                                    startActivity(new Intent(getContext(), AdminActivity.class));
                                }
                                if(Common.currentUser.getRole().equals("customer")){
                                    Log.d("role", "" + Common.currentUser.getRole());
                                    startActivity(new Intent(getContext(), MainActivity.class));
                                }

                            }

                        }
                        @Override
                        public void onFailure(Call<JSONResponseUser> call, Throwable t) {
                            Toast.makeText(getActivity().getBaseContext().getApplicationContext(), ""+t.getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    Toast.makeText(getActivity().getBaseContext(), "Vui lòng kiểm tra lại kết nối!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}