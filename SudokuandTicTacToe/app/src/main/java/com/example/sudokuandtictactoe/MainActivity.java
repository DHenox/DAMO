package com.example.sudokuandtictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    Button createGameButton(String game){
        Button bt = new Button(this);
        bt.setText("Play " + game);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                switch (game){
                    case "TicTacToe":
                        intent=new Intent(
                                MainActivity.this,
                                playTicTacToe.class);
                        startActivity(intent);
                        break;
                    case "Sudoku":
                        intent=new Intent(
                                MainActivity.this,
                                playSudoku.class);
                        startActivity(intent);
                        break;
                }
            }
        });
        return bt;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        LinearLayout linlay = new LinearLayout(this);
        linlay.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        linlay.setOrientation(LinearLayout.VERTICAL);
        linlay.setGravity(Gravity.CENTER_HORIZONTAL);
        String[] games = {"TicTacToe", "Sudoku"};
        for (int i = 0; i < games.length; i++) {
            linlay.addView(createGameButton(games[i]));
        }

        setContentView(linlay);
    }
}