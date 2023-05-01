package com.example.realisationgame;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    TextView MainText, MainText2, WarningText, MainRules, versionText;
    Button StartButton, FinalButton;
    String[] rulesText, devText;
    Resources resources;
    Animation anim, disanim;
    int WhatPhraseId = 0;
    boolean onClick=false;
    Intent intent, againIntent;
    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_into_and_outro);
        MainText = findViewById(R.id.MainName);
        MainText2 = findViewById(R.id.MainName2);
        WarningText = findViewById(R.id.WarningText);
        StartButton = findViewById(R.id.buttonStart);
        MainRules = findViewById(R.id.maintext_rules);
        versionText = findViewById(R.id.version);
        FinalButton = findViewById(R.id.buttonNextFinal);
        FinalButton.setVisibility(View.INVISIBLE);
        resources = getResources();
        rulesText = resources.getStringArray(R.array.into_text);
        devText = resources.getStringArray(R.array.delopers_text);
        intent = new Intent(IntoAndOutroActivity.this, MainActivity.class);
        againIntent = new Intent(IntoAndOutroActivity.this, IntoAndOutroActivity.class);
        StartButton.setVisibility(View.INVISIBLE);
        anim = AnimationUtils.loadAnimation(this,R.anim.appear_element);
        disanim = AnimationUtils.loadAnimation(this, R.anim.dissapear_element);
        sharedPreferences = getSharedPreferences("my_shared", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        if (sharedPreferences.getBoolean("inf_room_test",false)){
            StartButton.setAlpha(0f);
            MainText.setAlpha(0f);
            MainText2.setAlpha(0f);
            FinalButton.setVisibility(View.VISIBLE);
            versionText.setAlpha(0f);
            MainRules.setText(devText[WhatPhraseId]);
            WhatPhraseId++;
            editor.putBoolean("inf_room_test", false);
            editor.commit();
            FinalButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (WhatPhraseId >= 1 && WhatPhraseId != devText.length) {
                        MainRules.setText(devText[WhatPhraseId]);
                        WhatPhraseId ++;
                    } else {
                        startActivity(againIntent);
                        overridePendingTransition(R.anim.flash_in, R.anim.flash_out);
                        WhatPhraseId = 0;
                    }
                }
            });
        } else {
            MainText.setAnimation(anim);
            MainText2.setAnimation(anim);
            StartButton.setVisibility(View.VISIBLE);
            StartButton.setAnimation(anim);
            versionText.setAnimation(anim);



            editor.putBoolean("inf_room_test", false);
            editor.commit();
            disanim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    MainText.setAlpha(0f);
                    MainText2.setAlpha(0f);
                    StartButton.setAlpha(1f);
                    versionText.setAlpha(0f);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    MainRules.setText(rulesText[WhatPhraseId]);
                    WhatPhraseId++;
                    StartButton.setText("Дальше");
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            StartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!onClick) {
                        MainText.setAlpha(0f);
                        MainText2.setAlpha(0f);
                        StartButton.setAlpha(1f);
                        versionText.setAlpha(0f);
                        StartButton.setAnimation(disanim);
                        onClick = !onClick;
                    } else {
                        if (WhatPhraseId >= 1 && WhatPhraseId != rulesText.length) {
                            StartButton.setVisibility(View.VISIBLE);
                            StartButton.setText("Дальше");
                            MainRules.setText(rulesText[WhatPhraseId]);
                            WarningText.setText(rulesText[WhatPhraseId + 1]);
                            WhatPhraseId = WhatPhraseId + 2;
                        } else {
                            startActivity(intent);
                            overridePendingTransition(R.anim.flash_in, R.anim.flash_out);
                            WhatPhraseId = 0;
                        }
                    }
                }
            });
        }
}}