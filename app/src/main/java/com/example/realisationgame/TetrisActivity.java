package com.example.realisationgame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicInteger;

public class TetrisActivity extends AppCompatActivity implements Runnable {

    private static final int STATUS_PAUSED = 1;
    private static final int STATUS_START = 2;
    private static final int STATUS_OVER = 3;
    private static final int STATUS_PLAYING = 4;

    TetrisGameView gameViewC;
    TextView gameStatusText;
    TextView gameScoreText;
    Button gameBtn;
    boolean running = false;
    private final Handler mHandler = new Handler();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Intent intent, reintent;


    private final AtomicInteger mGameStatus = new AtomicInteger(STATUS_START);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tetris);

        gameViewC = findViewById(R.id.game_view);
        gameStatusText = findViewById(R.id.game_status);
        gameBtn = findViewById(R.id.game_control_btn);
        gameScoreText = findViewById(R.id.game_score);
        sharedPreferences = getSharedPreferences("my_shared", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        intent = new Intent(this, MainActivity.class);
        reintent = new Intent(this, TetrisActivity.class);
        gameViewC.setGameScoreUpdatedListener(score -> {
            mHandler.post(() -> gameScoreText.setText("Счет: " + score));
        });
        findViewById(R.id.left_btn).setOnClickListener(v -> {
            if (mGameStatus.get() == STATUS_PLAYING) {
                gameViewC.moveLeft();
            }
        });
        findViewById(R.id.right_btn).setOnClickListener(v -> {
            if (mGameStatus.get() == STATUS_PLAYING) {
                gameViewC.moveRight();
            }
        });

        findViewById(R.id.flip_btn).setOnClickListener(v -> {
            if (mGameStatus.get() == STATUS_PLAYING) {
                gameViewC.flipInMatrix();
            }
        });
        gameBtn.setOnClickListener(v -> {
            if (mGameStatus.get() == STATUS_PLAYING) {
                setGameStatus(STATUS_PAUSED);
            } else {
                setGameStatus(STATUS_PLAYING);
            }
        });

        setGameStatus(STATUS_START);
    }

    @Override
    protected void onPause() {
        super.onPause();
        setGameStatus(STATUS_PAUSED);
    }

    private void setGameStatus(int gameStatus) {
        int prevStatus = mGameStatus.get();
        gameStatusText.setVisibility(View.VISIBLE);
        gameBtn.setText("начать");
        mGameStatus.set(gameStatus);
        switch (gameStatus) {
            case STATUS_OVER:
                gameStatusText.setText("Конец игры");
                stop();
                AlertDialog alertDialog;
                if(gameViewC.score==993){
                    alertDialog = winDialog(this);
                } else {
                    alertDialog = loseDialog(this);
                }
                alertDialog.show();
                break;
            case STATUS_START:
                gameViewC.newGame();
                gameStatusText.setText("НАЧАТЬ ИГРУ");
                break;
            case STATUS_PAUSED:
                gameStatusText.setText("ПАУЗА");
                stop();
                break;
            case STATUS_PLAYING:
                if (prevStatus == STATUS_OVER) {
                    gameViewC.newGame();
                }
                start();
                gameStatusText.setVisibility(View.INVISIBLE);
                gameBtn.setText("пауза");
                break;
        }
    }


    public void start() {
        running = true;
        new Thread(this).start();
    }


    public void stop() {
        running = false;
    }
    @Override
    public void run() {
        while (running) {
            mHandler.post(gameViewC::invalidate);
            if (!gameViewC.isLanded()) gameViewC.moveDown();
            else {
                if (gameViewC.score==993){
                    stop();
                    gameBtn.setText("закончить");
                setGameStatus(STATUS_OVER);}
                if (gameViewC.isGameOverFull()){
                    gameViewC.isFull();
                    gameViewC.shape.setId(0);
                    gameViewC.newFigureSpawn();
                } else {
                    mHandler.post(() -> setGameStatus(STATUS_OVER));
                }
            }
            try {
                Thread.sleep(250);
            } catch (InterruptedException ex) {}
        }
        if (gameViewC.isGameOverFull()) {
            mHandler.post(() -> setGameStatus(STATUS_OVER));
        }
    }
    public AlertDialog loseDialog(Context context1){
        AlertDialog.Builder builder = new AlertDialog.Builder(context1);

        final View view = LayoutInflater.from(context1).inflate(R.layout.lost_dialog, null);
        builder.setView(view);
        TextView textView = view.findViewById(R.id.lost_text);
        textView.setText("Куда блоки ставить?");
        Button buttonYes = view.findViewById(R.id.yes_button);
        Button buttonNo = view.findViewById(R.id.no_button);
        builder.setCancelable(false);
        buttonYes.setOnClickListener(view1 -> reGame());
        buttonNo.setOnClickListener(view12 -> goInRoom(false));
        return builder.create();
    }
    public AlertDialog winDialog(Context context1){
        AlertDialog.Builder builder = new AlertDialog.Builder(context1);
        final View view = LayoutInflater.from(context1).inflate(R.layout.dialog_win, null);
        builder.setView(view);
        Button buttonYes = view.findViewById(R.id.win_button);
        builder.setCancelable(false);
        buttonYes.setOnClickListener(view1 -> goInRoom(true));
        return builder.create();
    }
    public void goInRoom(Boolean iswin){
        int col;
        startActivity(intent);
        if (iswin){
            editor.putBoolean("lit_room_test", true);
            col=sharedPreferences.getInt("colllit", 0);
            col = col+1;
            editor.putInt("colllit", col);
            editor.commit();
        }
        overridePendingTransition(R.anim.slide_right_animation, R.anim.slide_animation_left);
    }
    public void reGame(){
        startActivity(reintent);
        overridePendingTransition(1, 0);
    }
}