package com.example.realisationgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;


public class TetrisGameView extends View {

    boolean[][] mPoints = new boolean[12][16];
    Shape shape = new Shape();
    ScoreUpdatedListener mScoreUpdatedListener;
    Paint mPaint = new Paint();
    boolean mGameOver = false;
    int score = 0;
    public TetrisGameView(Context context) {
        super(context);
    }

    public TetrisGameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TetrisGameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void newGame() {
        mGameOver = false;
        updateScore();
        initMap();
        newFigureSpawn();
    }
    private void initMap() {
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 16; j++) {
                mPoints[i][j] = false;
            }
        }
    }
    public boolean isGameOverFull(){
        boolean brick = false;
        for (int i = 5; i < 5+shape.shape.length; i++) {
            for (int j = 1; j <shape.shape[i-5].length+1; j++) {
                brick = brick || mPoints[i][j];
            }
        }
        if (score==993) return true;
        return !brick;
    }

    public void newFigureSpawn(){
        shape.SetShape();
        for (int i = 5; i < 5+shape.shape.length; i++) {
            for (int j = 1; j <shape.shape[i-5].length+1; j++) {
                mPoints[i][j]=shape.shape[i-5][j-1];
            }
        }
        invalidate();
        shape.setY(1);
        shape.setX(5);
    }
    public void moveDown() {
        int y = shape.getY();
        int x = shape.getX();
        int col = shape.shape.length;

        if (col ==2){
            mPoints[x][y + shape.shape[0].length] = mPoints[x][y + shape.shape[0].length - 1]
                    || mPoints[x][y + shape.shape[0].length];
            mPoints[x+1][y + shape.shape[0].length] = mPoints[x+1][y + shape.shape[0].length - 1]
                    || mPoints[x+1][y + shape.shape[0].length];

            for (int i = shape.shape[0].length - 1; i > 0; i--) {
                mPoints[x][y + i] = mPoints[x][y + i - 1];
                mPoints[x+1][y + i] = mPoints[x+1][y + i - 1];
            }
            mPoints[x][y] = false;
            mPoints[x+1][y] = false;
            invalidate();
            shape.setY(y + 1);
        } else {
            mPoints[x][y + shape.shape[0].length] = mPoints[x][y + shape.shape[0].length - 1]
                    || mPoints[x][y + shape.shape[0].length];
            mPoints[x+1][y + shape.shape[0].length] = mPoints[x+1][y + shape.shape[0].length - 1]
                    || mPoints[x+1][y + shape.shape[0].length];
            mPoints[x+2][y + shape.shape[0].length] = mPoints[x+2][y + shape.shape[0].length - 1]
                    || mPoints[x+2][y + shape.shape[0].length];
            for (int i = shape.shape[0].length - 1; i > 0; i--) {
                mPoints[x][y + i] = mPoints[x][y + i - 1];
                mPoints[x+1][y + i] = mPoints[x+1][y + i - 1];
                mPoints[x+2][y + i] = mPoints[x+2][y + i - 1];
            }
            mPoints[x][y] = false;
            mPoints[x+1][y] = false;
            mPoints[x+2][y] = false;
            invalidate();
            shape.setY(y + 1);
        }
    }

    public boolean isLanded(){
        return shape.getY() + shape.shape[0].length == 16 ||
                (mPoints[shape.getX()][shape.getY() + shape.shape[0].length-1] && mPoints[shape.getX()][shape.getY() + shape.shape[0].length])
                || ( mPoints[shape.getX() + 1][shape.getY() + shape.shape[1].length-1] && mPoints[shape.getX() + 1][shape.getY() + shape.shape[1].length]);
    }

    public void flipInMatrix(){
        int y = shape.getY();
        int x = shape.getX();
        if (shape.canFlip()){

            for (int i = 0; i < shape.shape.length; i++) {
                for (int j = 0; j < shape.shape[0].length; j++) {
                    mPoints[x + i][y + j] = false;
                }
            }
            shape.flip();
            Log.i("flip", shape.id+"");
            for (int i = 0; i < shape.shape.length; i++) {
                for (int j = 0; j < shape.shape[0].length; j++) {
                    mPoints[x + i][y + j] = shape.shape[i][j];
                }
            }
            invalidate();}
    }


    public void moveRight() {
        int y = shape.getY();
        int x = shape.getX();
        int col = shape.shape[0].length;
        boolean block=false;

        if (shape.shape.length==2){
            if (x==9){
                for (int i = 0; i < col; i++) {
                    block = block || mPoints[10][y+i];
                }} else {
                for (int i = 0; i < col ; i++) {
                    block = block || mPoints[x+2][y+i];
                }
            }

            if (shape.getRightX()<10 && !block ){
                for (int i = 0; i < col; i++) {
                    mPoints[x+2][y+i]= mPoints[x+1][y+i];
                }
                for (int i = shape.shape[0].length-1; i > 0 ; i--) {
                    for (int j = 0; j < shape.shape[0].length; j++) {
                        mPoints[x+i][y+j]= mPoints[x+i-1][y+j];
                    }
                }for (int i = 0; i < col; i++) {
                    mPoints[x][y+i]= false;
                }
                invalidate();
                shape.setX(x+1);}
        } else {
            for (int i = 0; i < shape.shape[0].length; i++) {
                block = block || mPoints[shape.getRightX()+1][y + i];
            }
            Log.i("coord of 3l shape", x+" "+y);

            if (x<8 && !block && shape.getRightX() < 10 ){
                mPoints[shape.getRightX()+1][y]= mPoints[shape.getRightX()][y];
                mPoints[shape.getRightX()+1][y+1]= mPoints[shape.getRightX()][y+1];
                for (int i = 2; i > 0 ; i--) {
                    mPoints[x+i][y]= mPoints[x+i-1][y];
                    mPoints[x+i][y+1]= mPoints[x+i-1][y+1];
                }

                mPoints[x][y]= false;
                mPoints[x][y+1]= false;
                invalidate();
                shape.setX(x+1);}}

    }
    public void moveLeft() {
        int y = shape.getY();
        int x = shape.getX();
        int col = shape.shape[0].length;
        boolean block=false;

        if (shape.shape.length==2){
            for (int i = 0; i < col ; i++) {
                block = block || mPoints[x-1][y+i];
            }
            if (x>1 && !block){

                for (int i = 0; i < col; i++) {
                    mPoints[x-1][y+i]= mPoints[x][y+i];
                }
                for (int i = 0; i < shape.shape.length; i++) {
                    for (int j = 0; j < col; j++) {
                        mPoints[x+i][y+j]= mPoints[x+i+1][y+j];
                    }
                }
                for (int i = 0; i < col; i++) {
                    mPoints[x+1][y+i]= false;
                }
                invalidate();
                shape.setX(x-1);
            }} else {
            for (int i = 0; i < col ; i++) {
                block = block || mPoints[x-1][y+i];
            }
            if (x>1 && !block){

                for (int i = 0; i < col; i++) {
                    mPoints[x-1][y+i]= mPoints[x][y+i];
                }
                for (int i = 0; i < shape.shape.length; i++) {
                    mPoints[x+i][y]= mPoints[x+i+1][y];
                    mPoints[x+i][y+1]= mPoints[x+i+1][y+1];
                }
                mPoints[x+2][y]= false;
                mPoints[x+2][y+1]= false;
                invalidate();
                shape.setX(x-1);
            }
        }
    }

    public void setGameScoreUpdatedListener(ScoreUpdatedListener scoreUpdatedListener) {
        mScoreUpdatedListener = scoreUpdatedListener;

    }


    public void updateScore() {
        mScoreUpdatedListener.onScoreUpdated(score);
    }

    public boolean isFull(){
        boolean bri = false;
        for (int i = 15; i > 0; i--) {
            for (int j = 1; j < 11; j++) {
                bri = bri || mPoints[j][i];
                if (!mPoints[j][i]) {
                    Log.i("innit", "break");
                    break;
                }
                if (j == 10){
                    Log.i("innit", j + " "+ bri);
                    score=993;
                    updateScore();
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        final int mBoxSize = 40;
        final int mBoxPadding = 3;
        for (int y = 1; y < 16; y++) {
            for (int x = 1; x < 11; x++) {
                int left = mBoxSize * (x-1);
                int right = left + mBoxSize;
                int top = mBoxSize * (y-1);
                int bottom = top + mBoxSize;
                if (mPoints[x][y]) {
                    mPaint.setColor(Color.rgb(12,12,12));
                    canvas.drawRect(left, top, right, bottom, mPaint);
                    mPaint.setColor(Color.WHITE);
                    left += mBoxPadding;
                    right -= mBoxPadding;
                    top += mBoxPadding;
                    bottom -= mBoxPadding;
                } else {
                    mPaint.setColor(Color.rgb(12,12,12));
                }
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }
}
