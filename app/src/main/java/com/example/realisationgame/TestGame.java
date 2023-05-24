package com.example.realisationgame;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.util.Random;

public class TestGame extends View {
    int numMines=0;
    int closedCells=49;
    boolean isEmpty = true;
    Paint paintText, paintCell, paintTextView;
    int[][] mines;
    boolean[][] cells;
    int x=40, y=40;
    int paddingx=0, paddingy=0;
    Context context;
    TestActivity testActivity;
    float touchX, touchY;

    public TestGame(Context context, TestActivity testActivity) {
        super(context);
        paintText = new Paint();
        paintCell = new Paint();
        paintTextView = new Paint();
        cells = new boolean[7][7];
        this.context=context;
        this.testActivity=testActivity;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Drawable shape = context.getResources().getDrawable(R.drawable.main_background);
        shape.setBounds(0, 0, getWidth(), getHeight());
        shape.draw(canvas);
        if(isEmpty){
            mines = new int[7][7];
            for (int i = 0; i < 7; i++) {
                mines[placeMines()][placeMines()]=-1;
                for (int j = 0; j < 7; j++) {
                    cells[i][j]=true;
                }

            }

            for (int i = 0; i < mines.length; i++) {
                for (int j = 0; j < mines[i].length; j++) {
                    if (mines[i][j]!=-1) mines[i][j]=0;
                }
            }
            isEmpty=!isEmpty;

            for (int i = 0; i < mines.length; i++) {
                for (int j = 0; j < mines[i].length; j++) {
                    if (mines[i][j]!=-1) mines[i][j]=calcNear(i,j);
                    else numMines+=1;
                }
            }
        }
        paintText.setStyle((Paint.Style.FILL_AND_STROKE));
        paintText.setTextSize(60);
        paintCell.setStyle(Paint.Style.FILL_AND_STROKE);
        paintCell.setColor(Color.rgb(66, 59, 141));
        paddingx = getWidth()/8;
        paddingy = getHeight()/2;
        for (int i = 0; i < mines.length; i++) {
            for (int j = 0; j < mines[i].length; j++) {
                canvas.drawText(Integer.toString(mines[i][j]), x-15+paddingx, y+20+paddingy, paintText);
                if(cells[i][j]){
                    paintCell.setStyle(Paint.Style.FILL_AND_STROKE);
                } else{
                    paintCell.setStyle((Paint.Style.STROKE));
                }
                canvas.drawRect(x-40+paddingx, y-40+paddingy, x+40+paddingx, y+40+paddingy, paintCell);
                x+=80;
            }
            y+=80;
            x=40;
        }
        y=40;
        paintTextView.setTextSize(100);
        paintTextView.setColor(Color.rgb(235, 252, 255));
        paintTextView.setStyle(Paint.Style.FILL_AND_STROKE);

        canvas.drawText("Сапер", getWidth()/3, paddingy/4, paintTextView);
        paintTextView.setTextSize(40);

        paintText.setStyle((Paint.Style.FILL));
        canvas.drawText("Количество мин: "+numMines, paddingx-40, paddingy-paddingx/2, paintTextView);

    }

    public int calcNear(int i, int j) {
        int count = 0;
        int height = mines.length;
        int width = mines[0].length;
        for (int k = i - 1; k <= i + 1; ++k) {
            for (int l = j - 1; l <= j + 1; ++l) {
                if (0 <= k && k < height && 0 <= l && l < width && (k != i || l != j)) {
                    if (mines[k][l] == -1) count++;
                }
            }
        }
        return count;
    }
    public int placeMines(){
        Random random = new Random();
        int randomNumber = random.nextInt(7);
        return randomNumber;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction()==MotionEvent.ACTION_DOWN){
            touchX = event.getX();
            touchY = event.getY();
            calculate();
        }
        return true;
    }
    private void calculate(){
        int i,j;
        i=(int)(touchY-paddingy)/80;
        j=(int)(touchX-paddingx)/80;
        if (j<7&& j>-1 && i>-1 && i<7){
        if(mines[i][j]==-1){
            AlertDialog alertDialog = loseDialog(context);
            alertDialog.show();
        } else {
            if (cells[i][j]) {
                cells[i][j]=!cells[i][j];
                closedCells--;
            }
            invalidate();
        }
        if (closedCells == numMines){
            AlertDialog alertDialog = winDialog(context);
            alertDialog.show();
        }
    }}
    public AlertDialog loseDialog(Context context1){
        AlertDialog.Builder builder = new AlertDialog.Builder(context1);

        final View view = LayoutInflater.from(context1).inflate(R.layout.lost_dialog, null);
        TextView textView = view.findViewById(R.id.lost_text);
        textView.setText("Ба-бах!");
        builder.setView(view);
        Button buttonYes = view.findViewById(R.id.yes_button);
        Button buttonNo = view.findViewById(R.id.no_button);
        builder.setCancelable(false);
        buttonYes.setOnClickListener(view1 -> testActivity.reGame());
        buttonNo.setOnClickListener(view12 -> testActivity.goInRoom(false));
        return builder.create();
    }
    public AlertDialog winDialog(Context context1){
        AlertDialog.Builder builder = new AlertDialog.Builder(context1);

        final View view = LayoutInflater.from(context1).inflate(R.layout.dialog_win, null);
        builder.setView(view);
        Button buttonYes = view.findViewById(R.id.win_button);
        builder.setCancelable(false);
        buttonYes.setOnClickListener(view1 -> testActivity.goInRoom(true));

        return builder.create();
    }
}
