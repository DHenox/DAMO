package com.example.tetris;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.TextView;

public class Scores extends AppCompatActivity {
    TextView bestScoreTxt;
    TextView bestSocreTitleTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_score);

        Intent intent = getIntent();
        int bestScore = intent.getIntExtra("bestScore", -1);

        bestSocreTitleTxt = ((TextView) findViewById(R.id._bestSocreTitleTv));
        bestSocreTitleTxt.setPaintFlags(bestSocreTitleTxt.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        bestScoreTxt = ((TextView) findViewById(R.id._bestScoreTv));
        bestScoreTxt.setText(String.valueOf(bestScore) + "pts");
    }
}