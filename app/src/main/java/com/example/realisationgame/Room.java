package com.example.realisationgame;


import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;

public class Room extends View {
    Bitmap map, teach, student1, student2, gg;
    Paint paint;
    String[] inf_student1_dialog, inf_student2_dialog;
    int procentx, procenty, k, margingx, margingy;
    boolean isDraw=true;
    Context context;
    MainActivity mainActivity;
    String id;


    public Room(Context context, Bitmap map, Bitmap teach, Bitmap student1,
                Bitmap student2, Bitmap gg, String[]  inf_student2_dialog, String[]  inf_student1_dialog,
                MainActivity mainActivity, String ID) {
        super(context);
        this.map = map;
        this.gg=gg;
        this.inf_student2_dialog = inf_student2_dialog;
        this.inf_student1_dialog= inf_student1_dialog;
        this.teach = teach;
        this.student1 = student1;
        this.student2 = student2;
        this.context=context;
        paint=new Paint();
        this.mainActivity = mainActivity;
        id=ID;
    }


    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        procentx = getProcentx();
        procenty = getProcenty();


        if (isDraw){
            k = getKoeff();
            if (k<0){
                k=1;
            }
            map= Bitmap.createScaledBitmap(map, map.getWidth()*k, map.getHeight()*k, true);

            gg = Bitmap.createScaledBitmap(gg, gg.getWidth()*k/2, gg.getHeight()*k/2, true);
            teach = Bitmap.createScaledBitmap(teach, teach.getWidth()*k/2, teach.getHeight()*k/2, true);
            student1 = Bitmap.createScaledBitmap(student1, student1.getWidth()*k/2, student1.getHeight()*k/2, true);
            student2 = Bitmap.createScaledBitmap(student2, student2.getWidth()*k/2, student2.getHeight()*k/2, true);

            isDraw = !isDraw;
        }

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.rgb(183, 65, 0));
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
        margingx = (getWidth()-map.getWidth())/2;
        margingy = (getHeight()-map.getHeight())/2;

        canvas.drawBitmap(map,margingx , margingy, paint);
        canvas.drawBitmap(gg, (getWidth()-gg.getWidth())/2, (getHeight()-gg.getHeight())/2, paint);
        canvas.drawBitmap(teach, map.getWidth()/4+margingx, map.getHeight()/4+margingy-procenty*5, paint);
        canvas.drawBitmap(student1,getWidth()-2*(procentx+student2.getWidth()+margingx) ,getHeight()-(2*procenty+student1.getHeight()+margingy) , paint);
        canvas.drawBitmap(student2,getWidth()-(2*procentx+student2.getWidth()+margingx),getHeight()-(2*procenty+student1.getHeight()+margingy) , paint);
    }
    public void isOnBolvanchik(float touchX, float touchy) {
                if ((getWidth()-gg.getWidth())/2 < touchX && touchX < (getWidth()-gg.getWidth())/2 + gg.getWidth() && (getHeight()-gg.getHeight())/2< touchy &&  touchy<(getHeight()-gg.getHeight())/2+ gg.getHeight())  {
                    AlertDialog dialog = getMainDialog(context);
                    dialog.show();
                }
                if (map.getWidth() / 4 + margingx < touchX && touchX < map.getWidth() / 4 + margingx + teach.getWidth() && map.getHeight() / 4 + margingy - procenty * 5 < touchy && map.getHeight() / 4 + margingy - procenty * 5 + teach.getHeight() > touchy) {
                    AlertDialog dialog = getDialogTeacher(context);
                    dialog.show();
                }
                if (getWidth()-2*(procentx+student2.getWidth()+margingx) < touchX && touchX < getWidth()-2*(procentx+student2.getWidth()+margingx)+ student1.getWidth() && (getHeight()-(2*procenty+student1.getHeight()+margingy) < touchy && getHeight()-(2*procenty+student1.getHeight()+margingy) + teach.getHeight() > touchy)) {
                    AlertDialog dialog = getDialog(inf_student1_dialog, context);
                    dialog.show();
                }
                if (getWidth()-(2*procentx+student2.getWidth()+margingx)< touchX && touchX < getWidth()-(2*procentx+student2.getWidth()+margingx) + student2.getWidth() && getHeight()-(2*procenty+student1.getHeight()+margingy) < touchy && getHeight()-(2*procenty+student1.getHeight()+margingy) + student2.getHeight() > touchy) {
                    AlertDialog dialog = getDialog(inf_student2_dialog, context);
                    dialog.show();
                }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction()==MotionEvent.ACTION_DOWN){
            isOnBolvanchik(event.getX(),event.getY() );
        }

        return true;
    }
    public AlertDialog getDialog(String[] text, Context context1) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context1);

        final View view = LayoutInflater.from(context1).inflate(R.layout.dialog_pattern, null);
        builder.setView(view);

        TextView textView = view.findViewById(R.id.textView);
        textView.setText(text[0]);
        Button button = view.findViewById(R.id.button);
        final int[] count = {1};
        if (text.length == 1){
            button.setVisibility(View.INVISIBLE);
        } else {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count[0] <text.length-1) {textView.setText(text[count[0]]);
                    count[0]++;}
                else if (count[0]==text.length-1) {
                    textView.setText(text[count[0]]);
                    button.setVisibility(View.INVISIBLE);
                } else  button.setVisibility(View.INVISIBLE);
            }
        });}
        builder.setCancelable(true);
        return builder.create();
    }
    public AlertDialog getDialogTeacher(Context context1){
        AlertDialog.Builder builder = new AlertDialog.Builder(context1);

        final View view = LayoutInflater.from(context1).inflate(R.layout.dialog_teacher, null);
        builder.setView(view);
        TextView teachername = view.findViewById(R.id.teachers_name);
        teachername.setText("Учитель");
        Button button = view.findViewById(R.id.yes_button);
        button.setOnClickListener(view1 -> {
            switch (id){
                case "inf": mainActivity.goOnInfTest(); break;
                case "bio": mainActivity.goOnBioTest(); break;
                default: mainActivity.goOnLitTest(); break;
            }
        });
        return builder.create();
    }
    public AlertDialog getMainDialog(Context context1) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context1);

        final View view = LayoutInflater.from(context1).inflate(R.layout.dialog_main, null);
        builder.setView(view);

        TextView textViewMin = view.findViewById(R.id.textViewMin);
        TextView textViewSn = view.findViewById(R.id.textViewSn);
        TextView textViewTet = view.findViewById(R.id.textViewTet);
        int col = mainActivity.getWins("inf");
        textViewMin.setText("Побед в сапере: " + col);
        col = mainActivity.getWins("bio");
        textViewSn.setText("Побед в змейке: " + col);
        col = mainActivity.getWins("lit");
        textViewTet.setText("Побед в тетрисе: " + col);
        TextView nameText = view.findViewById(R.id.name);
        nameText.setText("Это я");
        Button buttonInf = view.findViewById(R.id.inf_map_teleport);
        Button buttonLit = view.findViewById(R.id.lit_map_teleport);
        Button buttonBio = view.findViewById(R.id.bio_map_teleport);
        switch (id) {
            case "inf":
                buttonInf.setVisibility(View.GONE);
                break;
            case "bio":
                buttonBio.setVisibility(View.GONE);
                break;
            case "lit":
                buttonLit.setVisibility(View.GONE);
                break;
        }
        buttonInf.setOnClickListener(view12 -> mainActivity.teleport("inf"));
        buttonBio.setOnClickListener(view1 -> mainActivity.teleport("bio"));
        buttonLit.setOnClickListener(view13 -> mainActivity.teleport("lit"));
        return builder.create();
    }
    public  int getKoeff(){
        return getWidth()/map.getWidth();
    }
    public  int getProcentx(){
        return getWidth() / 100;
    }
    public  int getProcenty(){
        return getHeight() / 100;
    }
}