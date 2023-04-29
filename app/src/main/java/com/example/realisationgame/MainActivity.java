package com.example.realisationgame;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {
    Resources resources;
    Bitmap room, gg, student1, student2, teacher;
    Intent intent;
    String[] text, inf_student1_dialog, inf_student2_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resources = getResources();

        text = resources.getStringArray(R.array.test);
        inf_student1_dialog = resources.getStringArray(R.array.inf_student_1);
        inf_student2_dialog = resources.getStringArray(R.array.inf_student_2);
        intent = new Intent(MainActivity.this, TestActivity.class);

        room = BitmapFactory.decodeResource(resources, R.drawable.map_inf);
        gg = BitmapFactory.decodeResource(resources, R.drawable.gg_base1);
        student1 = BitmapFactory.decodeResource(resources, R.drawable.inf_bolvanchik1);
        student2 = BitmapFactory.decodeResource(resources, R.drawable.inf_bolvanchik2);
        teacher = BitmapFactory.decodeResource(resources, R.drawable.inf_teach);


        Room map = new Room(this, room, teacher, student1, student2, gg, text, inf_student1_dialog, inf_student2_dialog, MainActivity.this);
        setContentView(map);
//        bolvanchikTouched = map.isOnBolvanchik(map.touchX, map.touchY);
//        if (bolvanchikTouched.equals("")){
//            DialogForCharacter.getDialog(this,text );
//
//            Toast.makeText(this, "text", Toast.LENGTH_SHORT).show();
//        }
    }
    public void goOnTest(){
        startActivity(intent);
    }
    public void goAgainOnTest(){
        finish();
        startActivity(intent);
    }
}