package com.example.realisationgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

public class TestGame extends View {
    int len=5; // ширина и высота сетки
    int numMines=7; // число мин на поле
    boolean isEmpty = true;
    Paint paintText, paintCell;
    int[][] mines; // двумерный массив, 1 если есть мина, 0 если мины нет
    boolean[][] cells; //наличие клеточки
    int x=40, y=40;
    float touchX, touchY;

    public TestGame(Context context) {
        super(context);
        paintText = new Paint();
        paintCell = new Paint();
        cells = new boolean[7][7];
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
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
        }
        for (int i = 0; i < mines.length; i++) {
            for (int j = 0; j < mines[i].length; j++) {
                if (mines[i][j]!=-1) mines[i][j]=calcNear(i,j);
            }
        }
        paintText.setStyle((Paint.Style.FILL_AND_STROKE));
        paintText.setTextSize(60);
        paintCell.setStyle(Paint.Style.FILL_AND_STROKE);
        paintCell.setColor(Color.rgb(34, 67, 12));
        for (int i = 0; i < mines.length; i++) {
            for (int j = 0; j < mines[i].length; j++) {
                canvas.drawText(Integer.toString(mines[i][j]), x-15, y+20, paintText);
                if(cells[i][j]){
                    paintCell.setStyle(Paint.Style.FILL_AND_STROKE);
                } else{
                    paintCell.setStyle((Paint.Style.STROKE));
                }
                canvas.drawRect(x-40, y-40, x+40, y+40, paintCell);
                x+=80;
            }
            y+=80;
            x=40;
        }
        y=40;
    }

    public int calcNear(int i, int j) {
        int count = 0;

        int height = mines.length;
        int width = mines[0].length;
        for (int k = i - 1; k <= i + 1; ++k) {
            for (int l = j - 1; l <= j + 1; ++l) {
                // проверка на выход за границы массива
                // и проверка на то, что обрабатываемая ячейка не равна изначальной ячейке
                if (0 <= k && k < height && 0 <= l && l < width && (k != i || l != j)) {
                    // любая операция с соседним элементом
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
        //координаты центра окружности
        float x0, y0;
        //номера строк и столбца
        int i,j;
        i=(int)touchY/80;
        j=(int)touchX/80;
        if (j<7&& i<7){
            if (cells[i][j]) cells[i][j]=!cells[i][j];
            invalidate();

        }
    }
}
