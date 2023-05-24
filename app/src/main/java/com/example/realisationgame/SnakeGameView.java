package com.example.realisationgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.LinkedList;
import java.util.Random;

public class SnakeGameView extends View {

        public enum Direction {LEFT, RIGHT, UP, DOWN}

        Point[][] mPoints = new Point[20][20];
        LinkedList<Point> mSnake = new LinkedList<>();
        Direction mDir;
        int score;
        private ScoreUpdatedListener mScoreUpdatedListener;

        boolean mGameOver = false;
    public SnakeGameView(Context context) {
        super(context);
    }

    public SnakeGameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SnakeGameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

        public void newGame() {
            mGameOver = false;
            mDir = Direction.RIGHT;
            initMap();
            updateScore();
        }

        public void setGameScoreUpdatedListener(ScoreUpdatedListener scoreUpdatedListener) {
            mScoreUpdatedListener = scoreUpdatedListener;
        }

        private void initMap() {
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 20; j++) {
                    mPoints[i][j] = new Point(j, i);
                }
            }
            mSnake.clear();
            for (int i = 0; i < 3; i++) {
                Point point = getPoint(5 + i,10);
                point.type = Point.PointType.SNAKE;
                mSnake.addFirst(point);
            }
            randomApple();
        }

        private void randomApple() {
            Random random = new Random();
            while (true) {
                Point point = getPoint(random.nextInt(20),
                        random.nextInt(20));
                if (point.type == Point.PointType.EMPTY) {
                    point.type = Point.PointType.APPLE;
                    break;
                }
            }
        }

        private Point getPoint(int x, int y) {
            return mPoints[y][x];
        }

        public void next() {
            Point first = mSnake.getFirst();
            Point next = getNext(first);

            switch (next.type) {
                case EMPTY:
                    next.type = Point.PointType.SNAKE;
                    mSnake.addFirst(next);
                    mSnake.getLast().type = Point.PointType.EMPTY;
                    mSnake.removeLast();
                    break;
                case APPLE:
                    next.type = Point.PointType.SNAKE;
                    mSnake.addFirst(next);
                    randomApple();
                    updateScore();
                    score++;
                    break;
                case SNAKE:
                    mGameOver = true;
                    break;
            }
            if (score==20){
                mGameOver=true;
            }
        }

        public void updateScore() {
            mScoreUpdatedListener.onScoreUpdated(mSnake.size() - 3);
        }

        public void setDirection(Direction dir) {
            if ((dir == Direction.LEFT || dir == Direction.RIGHT) &&
                    (mDir == Direction.LEFT || mDir == Direction.RIGHT)) {
                return;
            }
            if ((dir == Direction.UP || dir == Direction.DOWN) &&
                    (mDir == Direction.UP || mDir == Direction.DOWN)) {
                return;
            }
            mDir = dir;
        }

        private Point getNext(Point point) {
            int x = point.x;
            int y = point.y;

            switch (mDir) {
                case UP:
                    y = y == 0 ? 20 - 1 : y - 1;
                    break;
                case DOWN:
                    y = y == 20 - 1 ? 0 : y + 1;
                    break;
                case LEFT:
                    x = x == 0 ? 20 - 1 : x - 1;
                    break;
                case RIGHT:
                    x = x == 20 - 1 ? 0 : x + 1;
                    break;
            }
            return getPoint(x, y);
        }

        public boolean isGameOver() {
            return mGameOver;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Paint mPaint = new Paint();
            final int mBoxSize = 30;
            final int mBoxPadding = 3;
            for (int y = 0; y < 20; y++) {
                for (int x = 0; x < 20; x++) {
                    int left = mBoxSize * x;
                    int right = left + mBoxSize;
                    int top = mBoxSize * y;
                    int bottom = top + mBoxSize;
                    switch (getPoint(x, y).type) {
                        case APPLE:
                            mPaint.setColor(Color.RED);
                            break;
                        case SNAKE:
                            mPaint.setColor(Color.rgb(12,12,12));
                            canvas.drawRect(left, top, right, bottom, mPaint);
                            mPaint.setColor(Color.WHITE);
                            left += mBoxPadding;
                            right -= mBoxPadding;
                            top += mBoxPadding;
                            bottom -= mBoxPadding;
                            break;
                        case EMPTY:
                            mPaint.setColor(Color.rgb(12,12,12));
                            break;
                    }
                    canvas.drawRect(left, top, right, bottom, mPaint);
                }
            }
        }
    }