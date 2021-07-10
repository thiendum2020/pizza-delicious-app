package com.example.pizzadelicious.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pizzadelicious.EditProfileFragment;
import com.example.pizzadelicious.OnBoardActivity;
import com.example.pizzadelicious.R;
import com.example.pizzadelicious.Retrofit.Common;

public class ProfileFragment extends Fragment {
    TextView tv_name, tv_logout, tv_username, tv_email, tv_phone, tv_address;
    LinearLayout btn_order_history;
    ImageButton btn_edit, btn_back;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setControl(view);
        setEvent();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    private void setControl(View view) {
        tv_name = view.findViewById(R.id.tv_name);
        tv_logout = view.findViewById(R.id.tv_logout);
        tv_username = view.findViewById(R.id.tv_username);
        tv_email = view.findViewById(R.id.tv_email);
        tv_phone = view.findViewById(R.id.tv_phone);
        tv_address = view.findViewById(R.id.tv_address);
        btn_order_history = view.findViewById(R.id.btn_order_history);
        btn_edit = view.findViewById(R.id.btn_edit);
        btn_back = view.findViewById(R.id.btn_back);


    }

    private void setEvent() {
        tv_name.setText(Common.currentUser.getName());
        tv_username.setText(Common.currentUser.getUsername());
        tv_email.setText(Common.currentUser.getEmail());
        tv_phone.setText(Common.currentUser.getPhone());
        tv_address.setText(Common.currentUser.getAddress());

        btn_order_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getBaseContext(), "Order History", Toast.LENGTH_SHORT).show();
                Fragment someFragment = new OrderHistoryFragment();
                FragmentTransaction transaction = ProfileFragment.this.getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, someFragment); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }
        });
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment someFragment = new EditProfileFragment();
                FragmentTransaction transaction = ProfileFragment.this.getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, someFragment); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }
        });
        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getBaseContext(), "Log out", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        dialog.dismiss();

                        Common.currentUser = null;

                        startActivity(new Intent(getContext(), OnBoardActivity.class));
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