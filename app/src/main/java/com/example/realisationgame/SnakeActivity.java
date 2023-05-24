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

public class SnakeActivity extends AppCompatActivity {

    private static final int STATUS_PAUSED = 1;
    private static final int STATUS_START = 2;
    private static final int STATUS_OVER = 3;
    private static final int STATUS_PLAYING = 4;

    SnakeGameView gameView;
    TextView gameStatusText;
    TextView gameScoreText;
    Button gameBtn;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Intent intent, reintent;

    private final AtomicInteger mGameStatus = new AtomicInteger(STATUS_START);

    private final Handler mHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake);

        gameView = findViewById(R.id.game_view);
        gameStatusText = findViewById(R.id.game_status);
        gameBtn = findViewById(R.id.game_control_btn);
        gameScoreText = findViewById(R.id.game_score);
        sharedPreferences = getSharedPreferences("my_shared", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        gameView.setGameScoreUpdatedListener(score -> {
            mHandler.post(() -> gameScoreText.setText("Счет: " + score));
        });
        intent = new Intent(this, MainActivity.class);
        reintent = new Intent(this, SnakeActivity.class);

        findViewById(R.id.up_btn).setOnClickListener(v -> {
            if (mGameStatus.get() == STATUS_PLAYING) {
                gameView.setDirection(SnakeGameView.Direction.UP);
            }
        });
        findViewById(R.id.down_btn).setOnClickListener(v -> {
            if (mGameStatus.get() == STATUS_PLAYING) {
                gameView.setDirection(SnakeGameView.Direction.DOWN);
            }
        });
        findViewById(R.id.left_btn).setOnClickListener(v -> {
            if (mGameStatus.get() == STATUS_PLAYING) {
                gameView.setDirection(SnakeGameView.Direction.LEFT);
            }
        });
        findViewById(R.id.right_btn).setOnClickListener(v -> {
            if (mGameStatus.get() == STATUS_PLAYING) {
                gameView.setDirection(SnakeGameView.Direction.RIGHT);
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
                if(gameView.score==20){
                    AlertDialog alertDialog = winDialog(this);
                    alertDialog.show();
                } else {
                AlertDialog alertDialog = loseDialog(this);
                alertDialog.show();}
                break;
            case STATUS_START:
                gameView.newGame();
                gameStatusText.setText("НАЧАТЬ ИГРУ");
                break;
            case STATUS_PAUSED:
                gameStatusText.setText("ПАУЗА");
                break;
            case STATUS_PLAYING:
                if (prevStatus == STATUS_OVER) {
                    gameView.newGame();
                }
                startGame();
                gameStatusText.setVisibility(View.INVISIBLE);
                gameBtn.setText("пауза");
                break;
        }
    }

    private void startGame() {
        new Thread(() -> {
            int count = 0;
            while (!gameView.isGameOver() && mGameStatus.get() != STATUS_PAUSED) {
                try {
                    Thread.sleep(10);
                    if (count % 25 == 0) {
                        gameView.next();
                        mHandler.post(gameView::invalidate);
                        count = 0;
                    }
                    count++;
                } catch (InterruptedException ignored) {
                }
            }
            if (gameView.isGameOver()) {
                mHandler.post(() -> setGameStatus(STATUS_OVER));
            }
        }).start();
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
    public AlertDialog loseDialog(Context context1){
        AlertDialog.Builder builder = new AlertDialog.Builder(context1);

        final View view = LayoutInflater.from(context1).inflate(R.layout.lost_dialog, null);
        builder.setView(view);
        TextView textView = view.findViewById(R.id.lost_text);
        textView.setText("Кусь!");
        Button buttonYes = view.findViewById(R.id.yes_button);
        Button buttonNo = view.findViewById(R.id.no_button);
        builder.setCancelable(false);
        buttonYes.setOnClickListener(view1 -> reGame());
        buttonNo.setOnClickListener(view12 -> goInRoom(false));
        return builder.create();
    }
    public void goInRoom(Boolean iswin){
        int col;
        startActivity(intent);
        if (iswin){
            editor.putBoolean("bio_room_test", true);
            col=sharedPreferences.getInt("collbio", 0);
            col = col+1;
            editor.putInt("collbio", col);
            editor.commit();
        }
        overridePendingTransition(R.anim.slide_right_animation, R.anim.slide_animation_left);
    }

    public void reGame(){
        startActivity(reintent);
        overridePendingTransition(1, 0);
    }
}
