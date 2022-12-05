package com.example.tetris;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class Scores extends AppCompatActivity {
    LinearLayout linlay;
    Button a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        int bestScore = intent.getIntExtra("bestScore", -1);

        linlay = new LinearLayout(this);
        linlay.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        linlay.setOrientation(LinearLayout.VERTICAL);
        linlay.setGravity(Gravity.CENTER);
        a = new Button(this);
        a.setText(String.valueOf(bestScore));
        linlay.addView(a);
        setContentView(linlay);

    }
}