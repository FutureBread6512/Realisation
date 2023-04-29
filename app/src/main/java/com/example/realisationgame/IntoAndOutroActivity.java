package com.example.realisationgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;

public class IntoAndOutroActivity extends AppCompatActivity {
    TextView MainText, MainText2, WarningText, MainRules;
    Button StartButton;
    String[] rulesText;
    Animation anim;
    int WhatPhraseId = 0, onClick=0;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_into_and_outro);
        MainText = findViewById(R.id.MainName);
        MainText2 = findViewById(R.id.MainName2);
        WarningText = findViewById(R.id.WarningText);
        StartButton = findViewById(R.id.buttonStart);
        MainRules = findViewById(R.id.maintext_rules);
        //rulesText = Resources.getSystem().getStringArray(R.array.into_text);
        intent = new Intent(IntoAndOutroActivity.this, IntoAndOutroActivity.class);
       // StartButton.setVisibility(View.INVISIBLE);

//        anim = AnimationUtils.loadAnimation(this,R.anim.fromleft_toright_element);
//        MainText.setAnimation(anim);
//        MainText2.setAnimation(anim);


        StartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(onClick==0){
//                    startActivity(intent);
//                }
//
//                if (WhatPhraseId == 1){
//                    MainRules.setText(rulesText[WhatPhraseId]);
//                    WarningText.setText(rulesText[WhatPhraseId+1]);
//                    WhatPhraseId = WhatPhraseId +2;
//                } else if (WhatPhraseId==0) {
//                    MainRules.setText(rulesText[WhatPhraseId]);
//                } else {
//                    //TODO переход
//                }

            }
        });
    }
    public  void startAnim(Button button){
        button.setVisibility(View.VISIBLE);
        button.setAnimation(anim);
    }
}