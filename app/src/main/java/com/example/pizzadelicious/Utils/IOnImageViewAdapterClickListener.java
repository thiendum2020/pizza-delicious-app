package com.example.pizzadelicious.Utils;

import android.view.View;

public interface IOnImageViewAdapterClickListener {
    void onCalculatePriceListener(View view, int position, boolean isDecrease, boolean isIncrease);
}
