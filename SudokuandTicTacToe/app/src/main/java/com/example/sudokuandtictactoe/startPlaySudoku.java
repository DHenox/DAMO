package com.example.sudokuandtictactoe;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.FileNotFoundException;
import java.io.IOException;

public class startPlaySudoku extends AppCompatActivity {

    String[] board;

    private class Cell {
        int i,j;
        int value;
        Button bt;
        boolean constant;
        boolean conflict;
        public Cell(int I,int J) {
            i=I;
            j=J;
            conflict=false;
            value=board[i].charAt(j)-'0';
            constant=value!=0;
            bt=new Button(THIS);
            bt.setLayoutParams(
                    new LinearLayout.LayoutParams(buttonsize,buttonsize));
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    manageClick(i,j);
                }
            });
        }
        void draw() {
            if (value==0)
                bt.setText("");
            else
                bt.setText(String.valueOf(value));
            int color;
            if (constant) {
                if (conflict) {
                    color= Color.rgb(255, 51, 0);
                } else {
                    color=Color.BLACK;
                }
            } else {
                if (conflict) {
                    color=Color.RED;
                } else {
                    color=Color.BLUE;
                }
            }
            bt.setTextColor(color);
        }
    }

    Cell[][] table;

    void draw()
    {
        for (int i=0;i<9;i++)
            for (int j=0;j<9;j++)
                table[i][j].draw();
    }

    void checkConflicts(int i1,int j1,int i2,int j2)
    {
        int[] numoccurrences=new int[10];
        for (int i=i1;i<=i2;i++)
            for (int j=j1;j<=j2;j++)
                numoccurrences[table[i][j].value]++;
        for (int i=i1;i<=i2;i++)
            for (int j=j1;j<=j2;j++)
                if (numoccurrences[table[i][j].value]>1)
                    table[i][j].conflict=true;
    }

    void checkConflicts()
    {
        for (int i=0;i<9;i++)
            for (int j=0;j<9;j++)
                table[i][j].conflict=false;
        for (int i=0;i<9;i++)
            checkConflicts(i,0,i,8);
        for (int j=0;j<9;j++)
            checkConflicts(0,j,8,j);
        for (int i=0;i<9;i+=3)
            for (int j=0;j<9;j+=3)
                checkConflicts(i,j,i+2,j+2);
    }

    void manageClick(int i,int j)
    {
        Cell cell=table[i][j];
        if (cell.constant) return;
        cell.value=(cell.value+1)%10;
        checkConflicts();
        draw();
    }

    int id;
    int size,buttonsize;
    Context THIS;
    LinearLayout linlay;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        Intent intent=getIntent();
        id=intent.getIntExtra("id",0);
        int btId = intent.getIntExtra("btId",0);
        board=((DataManager)getApplication()).getBoard(id).t;
        THIS=this;
        DisplayMetrics metrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        size=metrics.widthPixels;
        buttonsize=size/9;
        linlay=new LinearLayout(this);
        linlay.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        linlay.setOrientation(LinearLayout.VERTICAL);
        linlay.setGravity(Gravity.CENTER_HORIZONTAL);
        table=new Cell[9][9];
        for (int i=0;i<9;i++) {
            LinearLayout row=new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);
            linlay.addView(row);
            for (int j=0;j<9;j++) {
                table[i][j]=new Cell(i,j);
                row.addView(table[i][j].bt);
            }
        }
        draw();

        Button btsave=new Button(this);
        btsave.setText("Save");
        btsave.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View view) {
                // Change sudoku board button to green when saved board
                playSudoku.choiceSudokuBoard[btId].setBackgroundColor(Color.parseColor("#9fe3bb"));

                String state="";
                for (int i=0;i<9;i++)
                    for (int j=0;j<9;j++)
                        state+=String.valueOf(table[i][j].value);

                try {
                    ((DataManager)getApplication()).saveBoardState(id,state);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        linlay.addView(btsave);
        Button btrestore=new Button(this);
        btrestore.setText("Restore");
        btrestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((DataManager)getApplication()).hasBoardState(id)) {
                    String state = "";
                    try {
                        state = ((DataManager) getApplication()).
                                getBoardState(id);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    int index = 0;
                    for (int i = 0; i < 9; i++)
                        for (int j = 0; j < 9; j++, index++)
                            table[i][j].value = state.charAt(index) - '0';
                    checkConflicts();
                    draw();
                }
                else
                    Log.i("Restore avoided", "There is no save");
            }
        });
        linlay.addView(btrestore);

        Button btrestart=new Button(this);
        btrestart.setText("Restart");
        btrestart.setBackgroundColor(Color.parseColor("#b36854"));
        btrestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Change sudoku board button to gray when restart board
                playSudoku.choiceSudokuBoard[btId].setBackgroundColor(Color.parseColor("#d6d7d7"));
                /*try {
                    ((DataManager) getApplication()).deleteBoardState(id);
                } catch (IOException e) {
                    e.printStackTrace();
                }*/

                for (int i = 0; i < 9; ++i) {
                    for (int j = 0; j < 9; ++j) {
                        //table[i][j].conflict = false;
                        int value = board[i].charAt(j) - '0';
                        table[i][j].value = value;
                        //table[i][j].constant = value != 0;
                    }
                }
                draw();
            }
        });
        linlay.addView(btrestart);
        setContentView(linlay);
    }
}
