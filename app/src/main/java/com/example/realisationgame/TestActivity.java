package com.example.realisationgame;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class TestActivity extends AppCompatActivity {

    Intent intent, reintent;
    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String s = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("my_shared", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        setContentView(new TestGame(this, this));
        intent = new Intent(TestActivity.this, MainActivity.class);
        reintent = new Intent(TestActivity.this, TestActivity.class);
    }
    public void goInRoom(Boolean iswin){
        startActivity(intent);
        if (iswin){
            editor.putBoolean("inf_room_test", true);
            s = sharedPreferences.getString("col", "0");
            s = s+"1";
            editor.putString("col", s);
            editor.commit();
        }
        overridePendingTransition(R.anim.slide_right_animation, R.anim.slide_animation_left);

    }
    public void reGame(){
        startActivity(reintent);
        overridePendingTransition(1, 0);
    }
}