package com.example.realisationgame;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;


public class MainActivity extends AppCompatActivity {
    Resources resources;
    Intent intentInfTest, intentBioTest, intentLitTest, intentFinal, redrawintent;
    Room bio_map, lit_map, inf_map;
    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resources = getResources();

        intentInfTest = new Intent(MainActivity.this, TestActivity.class);
        intentBioTest = new Intent(MainActivity.this, SnakeActivity.class);
        intentLitTest = new Intent(MainActivity.this, TetrisActivity.class);
        intentFinal = new Intent(MainActivity.this, IntoAndOutroActivity.class);
        redrawintent = new Intent(this, MainActivity.class);
        sharedPreferences = getSharedPreferences("my_shared", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        bio_map = new Room(this, BitmapFactory.decodeResource(resources, R.drawable.map_bio),
                BitmapFactory.decodeResource(resources, R.drawable.bio_teach),
                BitmapFactory.decodeResource(resources, R.drawable.bio_bolvanchik1),
                BitmapFactory.decodeResource(resources, R.drawable.bio_bolvanchik2),
                BitmapFactory.decodeResource(resources, R.drawable.gg_base1),
                resources.getStringArray(R.array.bio_student_1),
                resources.getStringArray(R.array.bio_student_2),
                MainActivity.this, "bio");
        lit_map = new Room(this, BitmapFactory.decodeResource(resources, R.drawable.map_lit),
                BitmapFactory.decodeResource(resources, R.drawable.lit_teach),
                BitmapFactory.decodeResource(resources, R.drawable.lit_bolvanchik1),
                BitmapFactory.decodeResource(resources, R.drawable.lit_bolvanchik2),
                BitmapFactory.decodeResource(resources, R.drawable.gg_base1),
                resources.getStringArray(R.array.lit_student_1),
                resources.getStringArray(R.array.lit_student_2),
                MainActivity.this, "lit");
        inf_map = new Room(this, BitmapFactory.decodeResource(resources, R.drawable.map_inf),
                BitmapFactory.decodeResource(resources, R.drawable.inf_teach),
                BitmapFactory.decodeResource(resources, R.drawable.inf_bolvanchik1),
                BitmapFactory.decodeResource(resources, R.drawable.inf_bolvanchik2),
                BitmapFactory.decodeResource(resources, R.drawable.gg_base1),
                resources.getStringArray(R.array.inf_student_1),
                resources.getStringArray(R.array.inf_student_2),
                MainActivity.this, "inf");

        switch (sharedPreferences.getString("id", "inf")){
            case "bio":
                setContentView(bio_map);
                break;
            case "lit":
                setContentView(lit_map);
                break;
            default:
                setContentView(inf_map);
                break;
        }

    }
    public void goOnInfTest(){
        startActivity(intentInfTest);
    }
    public void goOnBioTest(){
        startActivity(intentBioTest);
    }
    public void goOnLitTest(){
        startActivity(intentLitTest);
    }

    public void isTime(){
        if (sharedPreferences.getBoolean("inf_room_test", false)
                && sharedPreferences.getBoolean("bio_room_test", false) &&
                sharedPreferences.getBoolean("inf_room_test", false)){
            startActivity(intentFinal);
            overridePendingTransition(R.anim.flash_in, R.anim.flash_out);
        }
    }
    public int getWins(String ID) {
        int s = 0;
        switch (ID){
            case "inf": s = sharedPreferences.getInt("coll", 0); break;
            case "bio": s = sharedPreferences.getInt("collbio", 0); break;
            case "lit": s = sharedPreferences.getInt("colllit", 0); break;
        }
        isTime();
        return s;
    }

    public void teleport(String ID){
        if (ID.equals("inf")) {
           editor.putString("id", "inf");
           editor.commit();
        }
        if (ID.equals("bio")) {
            editor.putString("id", "bio");
            editor.commit();
        }
        if (ID.equals("lit")) {
            editor.putString("id", "lit");
            editor.commit();
        }
        startActivity(redrawintent);
        overridePendingTransition(R.anim.flash_in, R.anim.flash_out);
    }
}