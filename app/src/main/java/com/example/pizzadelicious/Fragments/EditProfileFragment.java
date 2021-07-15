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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.pizzadelicious.Models.JSONResponseAccounts;
import com.example.pizzadelicious.Models.User;
import com.example.pizzadelicious.R;
import com.example.pizzadelicious.Retrofit.ApiInterface;
import com.example.pizzadelicious.Retrofit.Common;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditProfileFragment extends Fragment {

    ApiInterface service;
    EditText et_password, et_name, et_email, et_address, et_phone;
    Button btn_save;
    ImageButton btn_back;
    private User user = new User();

    public EditProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        if (getActivity() != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        }
        et_password = view.findViewById(R.id.et_password);
        et_name = view.findViewById(R.id.et_name);
        et_email = view.findViewById(R.id.et_email);
        et_address = view.findViewById(R.id.et_address);
        et_phone = view.findViewById(R.id.et_phone);
        btn_save = view.findViewById(R.id.btn_save);
        btn_back = view.findViewById(R.id.btn_back);

        et_name.setText(Common.currentUser.getName());
        et_password.setText(Common.currentUser.getPassword());
        et_email.setText(Common.currentUser.getEmail());
        et_address.setText(Common.currentUser.getAddress());
        et_phone.setText(Common.currentUser.getPhone());
        service = Common.getGsonService();
        setEvent();
        return view;
    }

    private void setEvent() {
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                service.updateUser("" + Common.currentUser.getId(), "" + Common.currentUser.getUsername(), "" + et_password.getText().toString(),
                        "" + et_name.getText().toString(), "" + et_email.getText().toString(), "" + et_address.getText().toString(),
                        "" + et_phone.getText().toString(), "" + Common.currentUser.getRole()).enqueue(new Callback<JSONResponseAccounts>() {
                    @Override
                    public void onResponse(Call<JSONResponseAccounts> call, Response<JSONResponseAccounts> response) {
                        Toast.makeText(getActivity(), "Update successfully!", Toast.LENGTH_SHORT).show();
                        Common.currentUser.setPassword(et_password.getText().toString());
                        Common.currentUser.setName(et_name.getText().toString());
                        Common.currentUser.setEmail(et_email.getText().toString());
                        Common.currentUser.setAddress(et_address.getText().toString());
                        Common.currentUser.setPhone(et_phone.getText().toString());
                        Fragment someFragment = new ProfileFragment();
                        FragmentTransaction transaction = EditProfileFragment.this.getFragmentManager().beginTransaction();
                        transaction.replace(R.id.frameLayout, someFragment); // give your fragment container id in first parameter
                        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                        transaction.commit();
                    }

                    @Override
                    public void onFailure(Call<JSONResponseAccounts> call, Throwable t) {
                        Toast.makeText(getActivity(), "Update failed!", Toast.LENGTH_SHORT).show();
                    }
                });
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
                        Fragment someFragment = new ProfileFragment();
                        FragmentTransaction transaction = EditProfileFragment.this.getFragmentManager().beginTransaction();
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