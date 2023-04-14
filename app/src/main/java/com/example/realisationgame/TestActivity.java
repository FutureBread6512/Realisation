package com.example.realisationgame;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class TestActivity extends AppCompatActivity {
    FrameLayout container;
    Button currentTask;

    FragmentManager fm;
    FragmentTransaction ft;
   TestFragment testFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        container=findViewById(R.id.contaner);

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        testFragment = new TestFragment();
        ft.add(R.id.contaner, testFragment);
        ft.commit();
    }
}