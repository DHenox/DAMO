package com.example.mazegame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

public class Segment {
    point p1, p2;
    Segment(point p1, point p2){
        this.p1 = p1;
        this.p2 = p2;
    }

    void draw(Canvas canvas){
        Path path = new Path();
        path.moveTo((float)p1.x, (float)p1.y);
        path.lineTo((float)p2.x, (float)p2.y);
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(.05f);
        canvas.drawPath(path, paint);
    }
}
