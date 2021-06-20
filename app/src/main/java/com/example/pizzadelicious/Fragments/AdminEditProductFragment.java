package com.example.pizzadelicious.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.pizzadelicious.Models.JSONResponseProduct;
import com.example.pizzadelicious.Models.Product;
import com.example.pizzadelicious.R;
import com.example.pizzadelicious.Retrofit.ApiInterface;
import com.example.pizzadelicious.Retrofit.Common;
import com.example.pizzadelicious.Utils.ReadPathUtil;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class AdminEditProductFragment extends Fragment {
    EditText et_name, et_price, et_type;
    Button btn_chooseImage, btn_edit;
    ImageButton btn_back;
    ImageView imageView;

    String IMAGE_PATH = "", id_Product = "";
    Uri saveUri;
    ApiInterface service;
    private ArrayList<Product> products;
    private final int PICK_IMAGE_REQUEST = 1;

    public AdminEditProductFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_edit_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setControl(view);
        setEvent();
        service = Common.getGsonService();

    }

    private void setControl(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        if (getActivity() != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        }
        et_name = view.findViewById(R.id.et_name);
        et_price = view.findViewById(R.id.et_price);
        et_type = view.findViewById(R.id.et_type);
        btn_chooseImage = view.findViewById(R.id.btn_chooseImage);
        btn_edit = view.findViewById(R.id.btn_edit);
        btn_back = view.findViewById(R.id.btn_back);
        imageView = view.findViewById(R.id.imageView_editProduct);
    }

    private void setEvent() {

        ///by Dang
        service = Common.getGsonService();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            id_Product = bundle.getString("productId");
            Log.d("id", "onResponse: " + id_Product);
            load(id_Product);
        }
        //// by Dang
    }

    public void load(String id_Product){
        //by Dang
        service.getProductById(id_Product).enqueue(new Callback<JSONResponseProduct>() {
            @Override
            public void onResponse(Call<JSONResponseProduct> call, Response<JSONResponseProduct> response) {
                JSONResponseProduct jsonResponseProduct = response.body();
                products = new ArrayList<> (Arrays.asList(jsonResponseProduct.getData()));
                Log.d("id", "onResponse: " + id_Product);
                et_name.setText("" + products.get(0).getName());
                et_price.setText("" + products.get(0).getPrice());
                et_type.setText("" + products.get(0).getType().getId());
                Log.d("typeID", "onResponse: " +products.get(0).getType().getName()); //
                Picasso.get().load(products.get(0).getImage())
                        .into(imageView);

                btn_chooseImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chooseImage();
                    }
                });

                btn_edit.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        //Kiểm tra validate
                        if (!et_name.getText().toString().equals("") && !et_price.getText().toString().equals("")
                                && !et_type.getText().toString().equals("") && !et_type.getText().toString().equals("1")
                                && !et_type.getText().toString().equals("2"))
                        {
                            //Tạo cái load
                                final ProgressDialog progressDialog;
                                progressDialog = new ProgressDialog(getContext());
                                progressDialog.setMessage("Uploading...");
                                progressDialog.show();
                                //Call api update
                                service.updateProduct("" + id_Product, "" + et_name.getText(),""+ et_price, "" + et_type).enqueue(new Callback<JSONResponseProduct>() {
                                    @Override
                                    public void onResponse(Call<JSONResponseProduct> call, Response<JSONResponseProduct> response) {

                                        progressDialog.dismiss();
                                        Log.d("update", "onResponse: " + id_Product);
                                        Toast.makeText(getActivity(), "Updated Product successfully!", Toast.LENGTH_SHORT).show();
                                        //Loại 1 thì quay về pizza
                                        if(et_type.getText().toString().equals("1")){
                                            Fragment someFragment = new AdminPizzaFragment();
                                            FragmentTransaction transaction = AdminEditProductFragment.this.getFragmentManager().beginTransaction();
                                            transaction.replace(R.id.frameLayout, someFragment); // give your fragment container id in first parameter
                                            transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                                            transaction.commit();
                                        }
                                        else{
                                            Fragment someFragment = new AdminCakeFragment();
                                            FragmentTransaction transaction = AdminEditProductFragment.this.getFragmentManager().beginTransaction();
                                            transaction.replace(R.id.frameLayout, someFragment); // give your fragment container id in first parameter
                                            transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                                            transaction.commit();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<JSONResponseProduct> call, Throwable t) {
                                        progressDialog.dismiss();
                                        Toast.makeText(getActivity(), "Updated Product failed!", Toast.LENGTH_SHORT).show();
                                    }
                                });

                        } else {
                            Toast.makeText(getActivity(), "Updated Product failed!", Toast.LENGTH_SHORT).show();
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
                                Fragment someFragment = new DashboardFragment();
                                FragmentTransaction transaction = AdminEditProductFragment.this.getFragmentManager().beginTransaction();
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

            //not do something
            @Override
            public void onFailure(Call<JSONResponseProduct> call, Throwable t) {

            }
        });

    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {
            saveUri = data.getData();
            Log.d("uri2", "" + saveUri);
            IMAGE_PATH = ReadPathUtil.getPath(getContext(), saveUri);

            Picasso.get().load(saveUri)
                    .into(imageView);

        }
    }
}