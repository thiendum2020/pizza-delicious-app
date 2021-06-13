package com.example.pizzadelicious.Fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pizzadelicious.Models.JSONResponseBill;
import com.example.pizzadelicious.Models.JSONResponseBillDetail;
import com.example.pizzadelicious.Models.JSONResponseProduct;
import com.example.pizzadelicious.Models.Product;
import com.example.pizzadelicious.R;
import com.example.pizzadelicious.Retrofit.ApiInterface;
import com.example.pizzadelicious.Retrofit.Common;
import com.example.pizzadelicious.Utils.IOnImageViewAdapterClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailFragment extends Fragment {

    CircleImageView product_img;
    TextView product_name, product_price, tv_quantity_item;
    Button btn_addToCart;
    ImageView btn_decrease, btn_increase;
    ApiInterface service;
    private Toolbar toolbar;
    private ArrayList<Product> products;
    String productId;
    Integer totalPrices = 0;

    public ProductDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    private void load(String productId) {

        service.getProductById("" + productId).enqueue(new Callback<JSONResponseProduct>() {
            @Override
            public void onResponse(Call<JSONResponseProduct> call, Response<JSONResponseProduct> response) {
                JSONResponseProduct jsonResponseProduct = response.body();
                products = new ArrayList<>(Arrays.asList(jsonResponseProduct.getData()));
                Picasso.get().load("" + products.get(0).getImage()).into(product_img);
                product_name.setText(products.get(0).getName());
                product_price.setText(products.get(0).getPrice());
            }

            @Override
            public void onFailure(Call<JSONResponseProduct> call, Throwable t) {

            }
        });

        btn_decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(tv_quantity_item.getText().toString()) > 1) {
                    tv_quantity_item.setText("" + (Integer.parseInt(tv_quantity_item.getText().toString()) - 1));
                }
            }
        });
        btn_increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(tv_quantity_item.getText().toString()) < 99) {
                    tv_quantity_item.setText("" + (Integer.parseInt(tv_quantity_item.getText().toString()) + 1));
                }
            }
        });

        btn_addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Đã thêm vào giỏi hàng thành công!", Toast.LENGTH_SHORT).show();

                totalPrices = Integer.parseInt(product_price.getText().toString()) * Integer.parseInt(tv_quantity_item.getText().toString());


                if (Common.currentBill == null) {
                    service.postBill("0", "created", "" + Calendar.getInstance().getTime(), "" + Common.currentUser.getId()).enqueue(new Callback<JSONResponseBill>() {
                        @Override
                        public void onResponse(Call<JSONResponseBill> call, Response<JSONResponseBill> response) {
                            Log.d("bill_ok ", "Create Bill OK!");
                            JSONResponseBill jsonResponseBill = response.body();
                            Common.currentBill = jsonResponseBill.getData().get(0);
                            Log.d("bill ", "" + Common.currentBill.getNote());

                            service.postBillDetail("" + tv_quantity_item.getText(), "" + totalPrices,
                                    "" + products.get(0).getId(), "" + Common.currentBill.getId()).enqueue(new Callback<JSONResponseBillDetail>() {
                                @Override
                                public void onResponse(Call<JSONResponseBillDetail> call, Response<JSONResponseBillDetail> response) {
                                    Log.e("status ", "Create Bill Detail OK!");
                                }

                                @Override
                                public void onFailure(Call<JSONResponseBillDetail> call, Throwable t) {
                                    Log.e("bill_detail ", "Fail");
                                }
                            });
                        }

                        @Override
                        public void onFailure(Call<JSONResponseBill> call, Throwable t) {

                            Log.d("bill_fail ", "Failed");
                        }
                    });


                } else {
                    service.postBillDetail("" + tv_quantity_item.getText(), "" + totalPrices,
                            "" + products.get(0).getId(), "" + Common.currentBill.getId()).enqueue(new Callback<JSONResponseBillDetail>() {
                        @Override
                        public void onResponse(Call<JSONResponseBillDetail> call, Response<JSONResponseBillDetail> response) {
                            Log.e("status ", "Create Bill Detail OK!");
                        }

                        @Override
                        public void onFailure(Call<JSONResponseBillDetail> call, Throwable t) {
                            Log.e("bill_detail ", "Fail");
                        }
                    });
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);

        service = Common.getGsonService();

        product_img = view.findViewById(R.id.product_img);
        product_name = view.findViewById(R.id.product_name);
        product_price = view.findViewById(R.id.product_price);
        btn_addToCart = view.findViewById(R.id.btn_addToCart);
        btn_decrease = view.findViewById(R.id.btn_decrease);
        btn_increase = view.findViewById(R.id.btn_increase);
        tv_quantity_item = view.findViewById(R.id.tv_quantity_item);


        Bundle bundle = this.getArguments();
        if (bundle != null) {
            productId = bundle.getString("productId");
            load(productId);
        }

        return view;
    }
}