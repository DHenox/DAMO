package com.example.tetris;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Tetris extends AppCompatActivity {
    DrawView dv;
    TetrisState ts;
    int rows, columns;
    int gameBgColor;

    Button pauseBtn;
    Button leftBtn;
    Button rightBtn;
    Button rotateBtn;
    Button downBtn;
    Button dropBtn;

    Button restartBtn;

    TextView scoreText;

    Runnable runnable;
    Handler handler;
    int cycles;
    int maxCycles;

    // TODO almacenar mejor score
    // TODO configurar preferences

    @SuppressLint({"ClickableViewAccessibility", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tetris);

        rows = 16;
        columns = 10;
        gameBgColor = Color.rgb(200, 200, 200);
        ts = new TetrisState(rows, columns);
        dv = new DrawView(this, ts);
        dv.setBackgroundColor(gameBgColor);
        setBoardTouchEvent(dv);
        setButtons();

        long delay = 1;
        maxCycles = 30;
        cycles = 0;
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if(!ts.gamePaused()) {
                    if (!ts.gameOver()) {
                        if (cycles == maxCycles) {
                            boolean canMoveDown = ts.moveActiveFigureDown();
                            if (!canMoveDown) {
                                ts.paintActiveFigure();
                                ts.addNewFigure();
                                ts.removeLines();
                                setNewScore();
                            }
                            cycles = 0;
                        }
                        ++cycles;
                        dv.invalidate();
                    } else {
                        Intent intent = getIntent();
                        intent.putExtra("score", ts.getScore());
                        System.out.println("GAME OVER");
                        ((FrameLayout) findViewById(R.id.board_lay)).removeView(dv);
                        if (restartBtn.getParent() == null) {
                            ((FrameLayout) findViewById(R.id.board_lay)).removeView(restartBtn);
                            ((FrameLayout) findViewById(R.id.board_lay)).addView(restartBtn);
                        }
                    }
                }
                handler.postDelayed(runnable, delay);
            }
        };
        handler.postDelayed(runnable, delay);
        ((FrameLayout)findViewById(R.id.board_lay)).addView(dv);
    }

    int lastEndColmn = 0;
    boolean validToMove = false;
    @SuppressLint("ClickableViewAccessibility")
    private void setBoardTouchEvent(DrawView dv) {
        dv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getPointerCount() == 1){
                    float x = motionEvent.getX();
                    float y = motionEvent.getY();

                    //  Al presionar la casilla de guardar una figura
                    boolean insideBoardStored = (x >= dv.minXStored && x <= dv.maxXStored && y >= (dv.minYStored) && y <= (dv.maxYStored));
                    if(insideBoardStored && motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                        ts.storeActiveFigure();
                        return true;
                    }

                    //  Al presionar el tablero de juego
                    int startColumn = 0;
                    boolean insideBoard = (x >= dv.minX && x <= dv.maxX && y >= (dv.yOffset+dv.minY) && y <= (dv.yOffset+dv.maxY));
                    if(insideBoard && motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        startColumn = Math.round(x-dv.minX)/dv.blockWidth;
                        validToMove = true;
                    }
                    else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                        validToMove = false;
                    }
                    if(validToMove) {
                        int endColumn = Math.round(x-dv.minX)/dv.blockWidth;
                        if(lastEndColmn < endColumn && endColumn != startColumn){
                            ts.moveActiveFigureRight();
                        }
                        else if(lastEndColmn > endColumn && endColumn != startColumn){
                            ts.moveActiveFigureLeft();
                        }
                        lastEndColmn = endColumn;
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void setNewScore() {
        scoreText.setText("Score: " + ts.getScore());
    }

    private void setButtons(){
        pauseBtn = ((Button)findViewById(R.id._pause));
        pauseBtn.setText("Pause");

        leftBtn = ((Button)findViewById(R.id._left));
        leftBtn.setText("L");

        rightBtn = ((Button)findViewById(R.id._right));
        rightBtn.setText("R");

        rotateBtn = ((Button)findViewById(R.id._rotate));
        rotateBtn.setText("⟳");

        downBtn = ((Button)findViewById(R.id._down));
        downBtn.setText("↓");

        dropBtn = ((Button)findViewById(R.id._drop));
        dropBtn.setText("DROP");

        restartBtn = new Button(this);
        restartBtn.setText("Restart game");

        scoreText = ((TextView)findViewById(R.id._score));
        setNewScore();

        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ts.pauseGame(!ts.gamePaused());
                if(ts.gamePaused()){
                    pauseBtn.setText("Resume");
                }
                else{
                    pauseBtn.setText("Pause");
                }
            }
        });

        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ts.moveActiveFigureLeft();
            }
        });

        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ts.moveActiveFigureRight();
            }
        });

        rotateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ts.rotateActiveFigure();
            }
        });

        downBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ts.moveActiveFigureDown();
            }
        });

        dropBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ts.dropDownActiveFigure();
            }
        });

        Context context = this;
        restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((FrameLayout)findViewById(R.id.board_lay)).removeView(restartBtn);
                ts = new TetrisState(rows, columns);
                dv = new DrawView(context, ts);
                dv.setBackgroundColor(gameBgColor);
                setBoardTouchEvent(dv);
                ((FrameLayout)findViewById(R.id.board_lay)).addView(dv);
                setNewScore();
            }
        });
    }
}