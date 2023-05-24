package com.example.realisationgame;

public class Point {
    public final int x, y;
    public enum PointType{EMPTY, SNAKE, APPLE}
    public PointType type;
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        this.type = PointType.EMPTY;
    }
}
