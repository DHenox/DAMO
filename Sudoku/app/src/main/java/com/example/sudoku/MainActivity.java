package com.example.sudoku;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ListViewAutoScrollHelper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.BoringLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.io.IOException;
import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

    static Button[] choiceSudokuBoard;

    Button createButton(Board board, int i) {
        Button bt=new Button(this);
        bt.setText(board.name);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(
                        MainActivity.this,
                        PlaySudoku.class);
                intent.putExtra("id",board.id);
                intent.putExtra("btId", i);
                startActivity(intent);
            }
        });
        choiceSudokuBoard[i] = bt;
        return bt;
    }

    Button createClearButton(int id) {
        Button bt=new Button(this);
        bt.setText("Clear");
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((DataManager)getApplication()).hasBoardState(id)) {
                    choiceSudokuBoard[id].setBackgroundColor(Color.parseColor("#d6d7d7"));
                    try {
                        ((DataManager) getApplication()).deleteBoardState(id);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else
                    Log.i("Clear avoided", "It's already a clean board");
            }
        });
        return bt;
    }

    Button createClearAllButton() {
        Button bt=new Button(this);
        bt.setText("Clear all boards");
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int countNotSavedBoard = 0;
                for (int i = 0; i < ((DataManager)getApplication()).getListboards().length; i++) {
                    if(!((DataManager)getApplication()).hasBoardState(i)){
                        ++countNotSavedBoard;
                    }
                }
                if (countNotSavedBoard == ((DataManager)getApplication()).getListboards().length){
                    Log.i("ClearAll avoided", "There are no saved boards");
                    return;
                }
                for (int i = 0; i < ((DataManager)getApplication()).getListboards().length; i++) {
                    choiceSudokuBoard[i].setBackgroundColor(Color.parseColor("#d6d7d7"));
                }

                try {
                    ((DataManager)getApplication()).clearBoardStates();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return bt;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        for (int i = 0; i < ((DataManager)getApplication()).getListboards().length; i++) {
            if(((DataManager)getApplication()).hasBoardState(i)){
                choiceSudokuBoard[i].setBackgroundColor(Color.parseColor("#9fe3bb"));
                Log.i("HightLighting", "HightLighting saevd board  "+String.valueOf(i));
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Layout para la selección del sudoku manager (dropdown)
        LinearLayout selectSudokuManagerLay = new LinearLayout(this);
        selectSudokuManagerLay.setOrientation(LinearLayout.VERTICAL);

        Spinner dropdown = new Spinner(this);
        String[] items = new String[]{"Preferences", "Files", "SQL"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        selectSudokuManagerLay.addView(dropdown);
        Button okManager = new Button(this);
        okManager.setText("OK");
        selectSudokuManagerLay.addView(okManager);
        setContentView(selectSudokuManagerLay);

        Context context = this;

        okManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sudokuManager = dropdown.getSelectedItemPosition();
                ((DataManager)getApplication()).setSudokusManager(sudokuManager);

                LinearLayout linlay=new LinearLayout(context);
                linlay.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                ));
                linlay.setOrientation(LinearLayout.VERTICAL);
                linlay.setGravity(Gravity.CENTER_HORIZONTAL);

                linlay.addView(createClearAllButton());

                Board[] listboards=((DataManager)getApplication()).getListboards();
                choiceSudokuBoard = new Button[listboards.length];
                for (int i=0;i<listboards.length;i++){
                    LinearLayout row = new LinearLayout(context);
                    linlay.addView(row);
                    row.setOrientation(LinearLayout.HORIZONTAL);
                    row.setGravity(Gravity.CENTER_HORIZONTAL);

                    row.addView(createButton(listboards[i], i));
                    row.addView(createClearButton(i));
                }

                for (int i = 0; i < ((DataManager)getApplication()).getListboards().length; i++) {
                    if(((DataManager)getApplication()).hasBoardState(i)){
                        choiceSudokuBoard[i].setBackgroundColor(Color.parseColor("#9fe3bb"));
                        Log.i("HightLighting", "HightLighting saevd board  "+String.valueOf(i));
                    }
                }

                setContentView(linlay);
            }
        });

    }
}