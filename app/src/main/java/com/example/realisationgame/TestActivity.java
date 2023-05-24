package com.example.realisationgame;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class TestActivity extends AppCompatActivity {

    Intent intent, reintent;
    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int col = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("my_shared", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        setContentView(new TestGame(this, this));
        intent = new Intent(TestActivity.this, MainActivity.class);
        reintent = new Intent(this, TestActivity.class);
    }
    public void goInRoom(Boolean iswin){
        startActivity(intent);
        if (iswin){
            editor.putBoolean("inf_room_test", true);
            col=sharedPreferences.getInt("coll", 0);
            col = col+1;
            editor.putInt("coll", col);
            editor.commit();
        }
        overridePendingTransition(R.anim.slide_right_animation, R.anim.slide_animation_left);

    }
    public void reGame(){
        startActivity(reintent);
        overridePendingTransition(1, 0);
    }
}