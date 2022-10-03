package com.example.sudokuandtictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class playTicTacToe extends AppCompatActivity {

    TextView notification;

    private class Cell {
        int i, j;
        int value;
        Button bt;
        Cell(int I, int J){
            i = I;
            j = J;
            bt = new Button(THIS);
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    manageClick(i, j);
                }
            });
            value = 0;
        }
        void draw(){
            if (value == 0) bt.setText("");
            else if (value == 1) bt.setText("X");
            else if (value == 2) bt.setText("O");
        }
    }

    boolean inside(int i){
        return 0<=i && i<3;
    }

    boolean inside(int i, int j){
        return inside(i) && inside(j);
    }

    boolean win(int i, int j, int di, int dj) {
        int value = table[i][j].value;
        int i2=i, j2=j, di2=di, dj2=dj;
        if (value == 0) return false;
        for (i += di, j += dj; inside(i, j); i += di, j += dj){
            if (table[i][j].value != value)
                return false;
        }

        table[i2][j2].bt.setBackgroundColor(Color.parseColor("#e5f2bb"));
        for(i2+=di2, j2+=dj2; inside(i2, j2); i2+=di2, j2+=dj2) {
            table[i2][j2].bt.setBackgroundColor(Color.parseColor("#e5f2bb"));
        }
        return true;
    }

    boolean win(){
        for (int i=0; i<3; ++i)
            if(win(i, 0, 0, 1))
                return true;
        for (int j=0; j<3; ++j)
            if(win(0, j, 1, 0))
                return true;
        if(win(0, 0, 1, 1))
            return true;
        if(win(0, 2, 1, -1))
            return true;
        return false;
    }

    void manageClick(int i, int j){
        if (state != State.PLAYING) return;
        if (table[i][j].value != 0) return;
        table[i][j].value = turn;
        if (turn == 1) turn = 2;
        else turn = 1;
        notification.setText("Player " + turn + " it's your turn");
        table[i][j].draw();
        if(win()) {
            if (turn == 1) turn = 2;
            else turn = 1;
            notification.setText("Player " + turn + " you won the game!");
            state = State.OVER;
        }
    }

    int turn = 1;
    enum State {PLAYING, OVER};
    State state;
    Cell[][] table;
    Context THIS;
    LinearLayout linlay;
    LinearLayout.LayoutParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        Log.d("INTO TICTACTOE", "INTO TICTACTOE");
        THIS = this;
        linlay = new LinearLayout(this);
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        linlay.setLayoutParams(params);
        linlay.setOrientation(LinearLayout.VERTICAL);
        linlay.setGravity(Gravity.CENTER_HORIZONTAL);

        table = new Cell[3][3];
        for(int i=0; i<3; ++i){
            LinearLayout row = new LinearLayout(this);
            linlay.addView(row);
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setGravity(Gravity.CENTER_HORIZONTAL);
            for(int j=0; j<3; ++j){
                table[i][j] = new Cell(i, j);
                row.addView(table[i][j].bt);
            }
        }
        LinearLayout restart = new LinearLayout(this);
        linlay.addView(restart);
        restart.setOrientation(LinearLayout.HORIZONTAL);
        restart.setGravity(Gravity.CENTER_HORIZONTAL);
        Button restBT = new Button(this);
        restBT.setText("RESTART");
        restBT.setBackgroundColor(Color.parseColor("#b36854"));
        restBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turn = 1;
                notification.setText("Player " + turn + " it's your turn");
                for(int i=0; i<3; ++i){
                    for(int j=0; j<3; ++j){
                        table[i][j].bt.setText("");
                        table[i][j].value = 0;
                        table[i][j].bt.setBackgroundColor(Color.parseColor("#d6d7d7"));
                    }
                }
                turn = 1;
                state = State.PLAYING;
            }
        });
        restart.addView(restBT);


        notification = new TextView(this);
        notification.setText("Player " + turn + " it's your turn");
        notification.setTextSize(20);
        LinearLayout notifLay = new LinearLayout(this);
        linlay.addView(notifLay);
        notifLay.setOrientation(LinearLayout.HORIZONTAL);
        notifLay.setGravity(Gravity.CENTER_HORIZONTAL);
        notifLay.addView(notification);

        state=State.PLAYING;
        turn=1;
        setContentView(linlay);
    }
}