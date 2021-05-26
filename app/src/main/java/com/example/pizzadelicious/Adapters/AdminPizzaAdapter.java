package com.example.pizzadelicious.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzadelicious.Fragments.AdminPizzaFragment;
import com.example.pizzadelicious.Models.Product;
import com.example.pizzadelicious.R;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminPizzaAdapter extends RecyclerView.Adapter<AdminPizzaAdapter.ViewHolder> {
    private ArrayList<Product> list;
    AdminPizzaFragment adminPizzaFragment;
    private Context context;
    LinearLayout admin_product_item;

    public AdminPizzaAdapter(ArrayList<Product> list, AdminPizzaFragment adminPizzaFragment) {
        this.list = list;
        this.adminPizzaFragment = adminPizzaFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(adminPizzaFragment.getContext()).inflate(R.layout.admin_product_item, parent, false);
        context = parent.getContext();
        return new AdminPizzaAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product model = list.get(position);

        holder.tv_id_item.setText(model.getId());
        holder.tv_name_item.setText(model.getName());
        holder.tv__price_item.setText(model.getPrice());
        Picasso.get().load(model.getImage())
                .into(holder.img_item);
        admin_product_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(adminPizzaFragment.getContext(), "Đã chọn: " + model.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(adminPizzaFragment.getContext(), "Đã xóa: " + model.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_id_item, tv_name_item, tv__price_item;
        ImageButton btn_delete;
        ImageView img_item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_id_item = itemView.findViewById(R.id.tv_id_item);
            tv_name_item = itemView.findViewById(R.id.tv_name_item);
            tv__price_item = itemView.findViewById(R.id.tv__price_item);
            img_item = itemView.findViewById(R.id.img_item);
            btn_delete = itemView.findViewById(R.id.btn_delete);

            admin_product_item = itemView.findViewById(R.id.admin_product_item);

        }
    }
}
