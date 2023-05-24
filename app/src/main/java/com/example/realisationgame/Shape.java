package com.example.realisationgame;

import java.util.Random;

public class Shape {
    int x, y;
    int id = 0;
    public boolean[][] shape;
    public Shape() {
        this.x = 5;
        this.y = 1;
    }

    public void SetShape(){
        Random x = new Random();
        switch (x.nextInt(6)){
            case 0: shape = new boolean[][] {
                    {true, true},
                    {true, true} }; break; // O
            case 1: shape = new boolean[][] {
                    { true, true, true },
                    { false, false, true } }; break; // L
            case 2: shape = new boolean[][] {
                    { true, true, true},
                    { false, true, false} }; break; // T
            case 3: shape = new boolean[][] {
                    { false, false, true },
                    { true, true, true } }; break; // J
            case 5: shape = new boolean[][] {
                    { true, true, false },
                    { false, true, true } }; break; // S
            case 4: shape = new boolean[][] {
                    { false, true, true},
                    { true, true, false } }; break; // Z
        }
    }
    public boolean canFlip(){
//        if (x<9 && y<14){
//        if (!(gameView.mPoints[x+2][y] ||
//                gameView.mPoints[x+2][y+1] ||
//                gameView.mPoints[x][y+2] ||
//                gameView.mPoints[x+1][y+2]
//        )){
        switch (id){
            case 0:
                return getRightX() < 9;
            case 1:
                return getX() != 1 || getY()< 14;
            case 2:
                return getRightX() < 9;
            case 3:
                return getX() != 1 || getY() < 14;
            default: break;
//        }}
        }
        return false;
    }
    public void setX(int x) {
        this.x = x;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getRightX() {
        return x+shape.length-1;
    }

    public int getY() {
        return y;
    }

    public void flip() {
        if (canFlip()){
            int x = shape.length;
            int y = shape[0].length;
            boolean[][] newShape = new boolean[y][x];

            // assign values from shape to newShape
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    newShape[j][i] = shape[x-1-i][j];
                }
            }
            shape = newShape;
            if  (id!=3) id ++;
            else id=0;}
    }
}
