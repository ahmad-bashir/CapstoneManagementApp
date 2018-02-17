package com.ahmedbashir.capstonemanagementapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final ProgressBar progressBar =  findViewById(R.id.my_progressBar);
        progressBar.setSecondaryProgress(100);
        progressBar.setProgress(0);
        final Thread thread = new Thread(){
            @Override
            public void run(){
                try{
                    for (int i = 0; i < 100; i++){
                        progressBar.setProgress(i);
                        sleep(20);

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    startLogin();
                    finish();
                }
            }
        };
        thread.start();


    }

    private void startLogin() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();

    }


}
