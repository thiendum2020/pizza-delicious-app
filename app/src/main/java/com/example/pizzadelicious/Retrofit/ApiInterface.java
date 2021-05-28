package com.example.pizzadelicious.Retrofit;


import com.example.pizzadelicious.Models.JSONResponseAccounts;
import com.example.pizzadelicious.Models.JSONResponseBill;
import com.example.pizzadelicious.Models.JSONResponseBillDetail;
import com.example.pizzadelicious.Models.JSONResponseProduct;
import com.example.pizzadelicious.Models.JSONResponseUser;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @POST("login")
    Call<JSONResponseUser> getUserLogin(@Query("username") String username, @Query("password") String password);


    /*---USER--*/
    @GET("user")
    Call<JSONResponseAccounts> getAllUser();

    @GET("user/{id}")
    Call<JSONResponseAccounts> getUserById(@Path("id") String id);


    /*---PRODUCT--*/
    @GET("product/type/{id}")
    Call<JSONResponseProduct> getProductByType(@Path("id") String id);

    @GET("product/{id}")
    Call<JSONResponseProduct> getProductById(@Path("id") String id);

    @POST("product")
    @Multipart
    Call<JSONResponseProduct> postProduct(@Query("name") String content, @Query("price") String price,
                                          @Query("type_id") String type_id, @Part MultipartBody.Part file);

    @PUT("product/{id}")
    Call<JSONResponseProduct> updateProduct(@Path("id") String id, @Query("name") String name, @Query("price") String price,
                                            @Query("type_id") String type_id);

    @DELETE("product/{id}")
    Call<JSONResponseProduct> deleteBProduct(@Path("id") String id);


    /*---BILL--*/
    @GET("bill/user/{id}")
    Call<JSONResponseBill> getBillByUserId(@Path("id") String id);

    @GET("bill/note/created")
    Call<JSONResponseBill> getBillByNoteCreated();
    @GET("bill/note/waiting")
    Call<JSONResponseBill> getBillByNoteWaiting();
    @GET("bill/note/delivering")
    Call<JSONResponseBill> getBillByNoteDelivering();
    @GET("bill/note/success")
    Call<JSONResponseBill> getBillByNoteSuccess();

    @GET("bill")
    Call<JSONResponseBill> getAllBill();

    @POST("bill")
    Call<JSONResponseBill> postBill(@Query("prices") String prices, @Query("note") String note,
                                    @Query("date") String date, @Query("user_id") String user_id);

    @PUT("bill/{id}")
    Call<JSONResponseBill> updateBill(@Path("id") String id, @Query("note") String note, @Query("prices") String prices);

    @DELETE("bill/{id}")
    Call<JSONResponseBill> deleteBill(@Path("id") String id);


    /*---BILL DETAIL--*/
    @GET("bill_detail/bill/{id}")
    Call<JSONResponseBillDetail> getBillDetailByBillId(@Path("id") String id);

    @POST("bill_detail")
    Call<JSONResponseBillDetail> postBillDetail(@Query("quantity") String quantity, @Query("prices") String prices,
                                                @Query("product_id") String product_id, @Query("bill_id") String bill_id);

    @PUT("bill_detail/{id}")
    Call<JSONResponseBillDetail> updateBillDetail(@Path("id") String id, @Query("quantity") String quantity, @Query("prices") String prices);

    @DELETE("bill_detail/{id}")
    Call<JSONResponseBillDetail> deleteBillDetail(@Path("id") String id);


//
//
//    @POST("post")
//    @Multipart
//    Call<JSONResponsePost> addPost(@Header("APIKEY") String key, @Query("content") String content,
//                                   @Part MultipartBody.Part file, @Query("user_id") String user_id);
//
//    @PUT("post/{id}")
//    Call<JSONResponsePost> updatePost(@Header("APIKEY") String key, @Path("id") String id, @Query("content") String content);
//
//
//    @GET("post/{id}")
//    Call<JSONResponsePost> getDetailPost(@Header("APIKEY") String key, @Path("id") String id);
//
//    @DELETE("post/{id}")
//    Call<JSONResponsePost> deletePost(@Header("APIKEY") String key, @Path("id") String id);

}
