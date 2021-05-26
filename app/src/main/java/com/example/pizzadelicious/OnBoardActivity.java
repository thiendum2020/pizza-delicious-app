package com.example.pizzadelicious;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OnBoardActivity extends AppCompatActivity {
//    ProgressBar progressBar;
//    TextView tv_percent;
//    Animation left_anim, right_anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboard);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(OnBoardActivity.this, FragmentReplaceActivity.class);
                startActivity(intent);
            }
        },3500);
    }

}
