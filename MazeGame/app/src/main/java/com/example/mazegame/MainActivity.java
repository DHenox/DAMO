package com.example.mazegame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    LinearLayout linlay;
    ImageView imageView;
    Bitmap bitmap;
    int width;
    int height;
    int size;
    CoordinatesManager cm;
    Canvas canvas;
    int bgColor = Color.GRAY;
    SegmentsManager sm;
    Ball ball;
    Handler handler;
    Runnable runnable;
    long curMilliseconds;
    MazeGenerator mg;
    int n = 10;
    int m = 10;
    boolean fixedCamera;

    void draw(){
        canvas.drawColor(bgColor);

        Matrix matrix = new Matrix();
        point o = cm.world2screen(new point(0, 0));
        point ox = cm.vecworld2vecscreen(new point(1, 0));
        point oy = cm.vecworld2vecscreen(new point(0, 1));
        matrix.setValues(new float[] {
                (float)ox.x, (float)oy.x, (float)o.x,
                (float)ox.y, (float)oy.y, (float)o.y,
                0, 0, 1
        });

        canvas.save();
        canvas.setMatrix(matrix);
        sm.draw(canvas);
        ball.draw(canvas);
        canvas.restore();
        imageView.invalidate();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        linlay = new LinearLayout(this);
        linlay.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        linlay.setOrientation(LinearLayout.VERTICAL);
        linlay.setGravity(Gravity.CENTER);

        imageView  = new ImageView(this);
        linlay.addView(imageView);
        Point p = new Point();
        getWindowManager().getDefaultDisplay().getSize(p);
        width = p.x;
        height = p.y;
        size = (int)(width*0.9);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(size,size));
        bitmap = Bitmap.createBitmap(size,size, Bitmap.Config.ARGB_8888);
        imageView.setImageBitmap(bitmap);
        canvas = new Canvas();
        canvas.setBitmap(bitmap);

        sm = new SegmentsManager();
        //sm.add(new Segment(new point(0.5, 0), new point(0, 0.5)));
        mg = new MazeGenerator();
        mg.generate(n, m, sm);

        cm = new CoordinatesManager();
        ball = new Ball();
        ball.sm = sm;
        cm.ball = ball;
        cm.size = size;
        cm.setCamera(new point(n/2.0, m/2.0), new point(Math.max(n/2.0, m/2.0), 0));
        fixedCamera = false;

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                /*Log.e("--->Action",""+event.getAction());
                Log.e("--->X",""+event.getX());
                Log.e("--->Y",""+event.getY());*/
                if (event.getAction() == MotionEvent.ACTION_UP || (event.getPointerCount()!=1 && event.getPointerCount()!=2)){
                    cm.touch();
                }
                else if (event.getPointerCount() == 1) {
                    cm.touch(new point(event.getX(), event.getY()));
                }
                else if (event.getPointerCount() == 2){
                    cm.touch(new point(event.getX(0), event.getY(0)),       //  finger 1
                            new point(event.getX(1), event.getY(1)));       //  finger 2
                }
                draw();
                return true;
            }
        });
        draw();
        curMilliseconds = System.currentTimeMillis();

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                long nextCurMilliseconds = System.currentTimeMillis();
                double delta = (nextCurMilliseconds - curMilliseconds)/1000.0;
                curMilliseconds = nextCurMilliseconds;
                ball.move(delta);
                draw();
                if(fixedCamera && (ball.isSelected() || cm.numfingers == 0))
                    cm.setCenter(ball.c);
                handler.postDelayed(runnable, 10);
            }
        };
        handler.postDelayed(runnable, 10);

        Button camModeBtn = new Button(this);
        camModeBtn.setText("Lock camera");
        camModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!fixedCamera) {
                    camModeBtn.setText("Unlock camera");
                    cm.setCenter(ball.c);
                }
                else
                    camModeBtn.setText("Lock camera");
                fixedCamera = !fixedCamera;
            }
        });
        linlay.addView(camModeBtn);

        setContentView(linlay);
    }
}