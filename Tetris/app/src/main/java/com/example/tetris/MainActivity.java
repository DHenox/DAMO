package com.example.tetris;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    LinearLayout linlay;
    Button playBtn;
    Button topScoreBtn;
    Button preferencesBtn;
    int bestScore;
    static final int GAME_REQUEST = 0;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case (GAME_REQUEST): {
                if (resultCode == Tetris.RESULT_OK) {
                    int lastGameScore = data.getIntExtra("score", -1);
                    if(lastGameScore > bestScore){
                        bestScore = lastGameScore;
                    }
                }
                break;
            }
        }
    }

    // TODO almacenar las mejores scores
    // TODO configurar guardados de partidas
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        bestScore = 0;

        linlay = new LinearLayout(this);
        linlay.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        linlay.setOrientation(LinearLayout.VERTICAL);
        linlay.setGravity(Gravity.CENTER);

        playBtn = new Button(this);
        playBtn.setText("Play");
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Tetris.class);
                startActivityForResult(intent, GAME_REQUEST);
                setResult(Tetris.RESULT_OK, intent);
            }
        });
        linlay.addView(playBtn);

        topScoreBtn = new Button(this);
        topScoreBtn.setText("Best Score");
        topScoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Scores.class);
                intent.putExtra("bestScore", bestScore);
                startActivity(intent);
            }
        });
        linlay.addView(topScoreBtn);

        preferencesBtn = new Button(this);
        preferencesBtn.setText("Settings");
        preferencesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Settings.class);
                startActivity(intent);
            }
        });
        linlay.addView(preferencesBtn);

        setContentView(linlay);
    }
}