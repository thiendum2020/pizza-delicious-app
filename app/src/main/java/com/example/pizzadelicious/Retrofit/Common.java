package com.example.pizzadelicious.Retrofit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.pizzadelicious.Models.Bill;
import com.example.pizzadelicious.Models.User;

import java.util.ArrayList;

public class Common {

    public static User currentUser = new User();
    public static Bill currentBill = new Bill();
    public static Integer totalBill = 0;
    public static String DELETE ="Delete";
    public static String USER_KEY ="User";
    public static String PWD_KEY ="Password";


    public static boolean isConnectedToInternet(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager !=null){
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();

            if(info != null){
                for(int i = 0; i<info.length; i++){
                    if(info[i].getState() == NetworkInfo.State.CONNECTED){
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public static final String BASE_URL = "https://pizza-toryo.herokuapp.com/api/";


    public static ApiInterface getScalarsService(){
        return RetrofitClient.getRetrofitInstanceScalars(BASE_URL).create(ApiInterface.class);
    }

    public static ApiInterface getGsonService(){
        return RetrofitClient.getRetrofitInstanceGson(BASE_URL).create(ApiInterface.class);
    }
}
