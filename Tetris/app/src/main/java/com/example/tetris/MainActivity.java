package com.example.tetris;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button playBtn;
    Button topScoreBtn;
    Button settingsBtn;
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
        setContentView(R.layout.activity_main);
        bestScore = 0;

        playBtn = ((Button) findViewById(R.id._playBtn));
        playBtn.setText("Play");
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Tetris.class);
                startActivityForResult(intent, GAME_REQUEST);
                setResult(Tetris.RESULT_OK, intent);
            }
        });

        topScoreBtn = ((Button) findViewById(R.id._bestScoreBtn));
        topScoreBtn.setText("Best Score");
        topScoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Scores.class);
                intent.putExtra("bestScore", bestScore);
                startActivity(intent);
            }
        });

        settingsBtn = ((Button) findViewById(R.id._settingsBtn));
        settingsBtn.setText("Settings");
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Settings.class);
                startActivity(intent);
            }
        });
    }
}