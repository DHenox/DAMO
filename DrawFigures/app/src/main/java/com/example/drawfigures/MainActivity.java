package com.example.drawfigures;

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

import java.util.Random;

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
    PolygonsManager pm;

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
        pm.draw(canvas);
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

        pm = new PolygonsManager();
        cm = new CoordinatesManager();
        cm.setPm(pm);
        cm.size = size;
        cm.setCamera(new point(0, 0), new point(1, 0));

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

        pm.add(new Polygon(new point(0, 0), 0.008, 12, "cross"));
        draw();

        Button showImgBtn = new Button(this);
        showImgBtn.setText("Add new polygon");
        showImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random rnd = new Random();
                double randX = -0.5 + rnd.nextDouble()*(0.5-(-0.5));
                double randY = -0.5 + rnd.nextDouble()*(0.5-(-0.5));
                double randRadious = 0.1 + rnd.nextDouble()*(0.4-0.1);
                int randVertex = (int)(3 + rnd.nextDouble()*(10-3));

                pm.add(new Polygon(new point(randX,randY), randRadious, randVertex, "polygon"));
                draw();
            }
        });
        linlay.addView(showImgBtn);

        Button setCameraCetnerBtn = new Button(this);
        setCameraCetnerBtn.setText("Center Camera");
        setCameraCetnerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Handler handler = new Handler();
                Runnable r = new Runnable() {
                    point originalO = cm.O;
                    point originalOX = cm.OX;
                    double newOX, newOY, newOXX, newOXY;
                    double steps = 20;
                    double lambda = 0;
                    int i = 1;
                    @Override
                    public void run() {
                        if(i <= steps) {
                            lambda = i / steps;
                            newOX = (1 - lambda) * originalO.x + lambda * 0;
                            newOY = (1 - lambda) * originalO.y + lambda * 0;
                            newOXX = (1 - lambda) * originalOX.x + lambda * 1;
                            newOXY = (1 - lambda) * originalOX.y + lambda * 0;
                            cm.setCamera(new point(newOX, newOY), new point(newOXX, newOXY));
                            draw();
                            ++i;
                            handler.postDelayed(this, 1);
                        }
                    }
                };
                handler.postDelayed(r, 1);
            }
        });
        linlay.addView(setCameraCetnerBtn);

        Button removeFigures = new Button(this);
        removeFigures.setText("Clear all");
        removeFigures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pm.clearAll();
                draw();
            }
        });
        linlay.addView(removeFigures);

        setContentView(linlay);
    }
}