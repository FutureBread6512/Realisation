package com.example.realisationgame;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Bolvanchik {
    String id;
    String[] text;
    Bitmap texture;


    public Bolvanchik(String id, String[] text, Bitmap texture) {
        this.id = id;
        this.text = text;
        this.texture = texture;
    }
}
