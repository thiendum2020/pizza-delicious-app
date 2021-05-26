package com.example.pizzadelicious.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzadelicious.Fragments.AdminAccountsFragment;
import com.example.pizzadelicious.Models.User;
import com.example.pizzadelicious.R;

import java.util.ArrayList;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.ViewHolder> {


    private ArrayList<User> list;
    AdminAccountsFragment adminAccountsFragment;
    private Context context;
    LinearLayout account_item;

    public AccountAdapter(ArrayList<User> list, AdminAccountsFragment adminAccountsFragment) {
        this.list = list;
        this.adminAccountsFragment = adminAccountsFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(adminAccountsFragment.getContext()).inflate(R.layout.account_item, parent, false);
        context = parent.getContext();
        return new AccountAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountAdapter.ViewHolder holder, int position) {
        User model = list.get(position);

        holder.tv_id.setText(model.getId());
        holder.tv_name.setText(model.getName());
        holder.tv_email.setText(model.getEmail());
        holder.tv_role.setText(model.getRole());

        account_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(adminAccountsFragment.getContext(), "Đã chọn: " + model.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(adminAccountsFragment.getContext(), "Đã xóa: " + model.getName(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_id, tv_name, tv_email, tv_role;
        ImageButton btn_delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.tv_id);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_email = itemView.findViewById(R.id.tv_email);
            tv_role = itemView.findViewById(R.id.tv_role);
            btn_delete = itemView.findViewById(R.id.btn_delete);

            account_item = itemView.findViewById(R.id.account_item);
        }
    }
}
