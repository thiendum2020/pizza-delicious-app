package com.example.pizzadelicious.Fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.pizzadelicious.R;

public class AdminOrdersFragment extends Fragment {
    TextView tv_waiting, tv_delivering, tv_completed;
    ImageButton btn_back, btn_add;

    public AdminOrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setControl(view);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fragment someFragment = new WaitingBillFragment();
        FragmentTransaction transaction = AdminOrdersFragment.this.getFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout_order, someFragment); // give your fragment container id in first parameter
        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
        transaction.commit();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_orders, container, false);
    }

    private void setControl(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        if (getActivity() != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        }
        btn_back = view.findViewById(R.id.btn_back);
        btn_add = view.findViewById(R.id.btn_add);
        tv_waiting = view.findViewById(R.id.tv_waiting);
        tv_delivering = view.findViewById(R.id.tv_delivering);
        tv_completed = view.findViewById(R.id.tv_completed);

        tv_waiting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_waiting.setTextColor(Color.parseColor("#000000"));
                tv_delivering.setTextColor(Color.parseColor("#F4511E"));
                tv_completed.setTextColor(Color.parseColor("#00FF0B"));

                Fragment someFragment = new WaitingBillFragment();
                FragmentTransaction transaction = AdminOrdersFragment.this.getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout_order, someFragment); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }
        });
        tv_delivering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_delivering.setTextColor(Color.parseColor("#000000"));
                tv_waiting.setTextColor(Color.parseColor("#039BE5"));
                tv_completed.setTextColor(Color.parseColor("#00FF0B"));

                Fragment someFragment = new DeliveringBillFragment();
                FragmentTransaction transaction = AdminOrdersFragment.this.getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout_order, someFragment); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }
        });
        tv_completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_completed.setTextColor(Color.parseColor("#000000"));
                tv_waiting.setTextColor(Color.parseColor("#039BE5"));
                tv_delivering.setTextColor(Color.parseColor("#F4511E"));

                Fragment someFragment = new CompletedBillFragment();
                FragmentTransaction transaction = AdminOrdersFragment.this.getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout_order, someFragment); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment someFragment = new DashboardFragment();
                FragmentTransaction transaction = AdminOrdersFragment.this.getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, someFragment); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }
        });
    }
}