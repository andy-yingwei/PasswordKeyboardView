package com.example.andy.myutils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.passwordkeyboardview.PayDialogFragment;



public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void pay(View view) {
        PayDialogFragment payDialogFragment = new PayDialogFragment();
        payDialogFragment.show(getSupportFragmentManager(), "payFragment");
    }

}
