package com.example.drawfigures;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import java.util.Random;

public class Polygon {
    point v[];
    point c;
    double r;
    int polygonColor;

    Polygon(point center, double radious, int numvertex, String type){
        c = center;
        r = radious;
        v = new point[numvertex];
        if(type == "cross"){
            polygonColor = Color.BLACK;
            v[0] = point.sum(center, new point(-0.75, radious));
            v[1] = point.sum(center, new point(-radious, radious));
            v[2] = point.sum(center, new point(-radious, 0.75));
            v[3] = point.sum(center, new point(radious, 0.75));
            v[4] = point.sum(center, new point(radious, radious));
            v[5] = point.sum(center, new point(0.75, radious));
            v[6] = point.sum(center, new point(0.75, -radious));
            v[7] = point.sum(center, new point(radious, -radious));
            v[8] = point.sum(center, new point(radious, -0.75));
            v[9] = point.sum(center, new point(-radious, -0.75));
            v[10] = point.sum(center, new point(-radious, -radious));
            v[11] = point.sum(center, new point(-0.75, -radious));
        }
        else{
            Random rnd = new Random();
            polygonColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            for (int i=0; i<v.length; ++i){
                v[i]=point.sum(center, point.polar(radious,i*2*Math.PI/numvertex));
            }
        }
    }
    void draw(Canvas canvas, boolean selected){
        Path path = new Path();
        //point p = cm.world2screen(v[0]);
        point p = v[0];
        path.moveTo((float)p.x, (float)p.y);
        for (int i = 1; i<v.length; ++i) {
            //p = cm.world2screen(v[i]);
            p = v[i];
            path.lineTo((float) p.x, (float) p.y);
        }
        path.close();
        Paint paint = new Paint();
        paint.setColor(polygonColor);

        canvas.drawPath(path,paint);
        paint.setStyle(Paint.Style.FILL);
        if (selected){
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(0.015f);
        }
        canvas.drawPath(path,paint);
    }

    boolean inside(point p) {
        for (int i = 0; i < v.length; i++)
            if(point.crossPord( point.sub(v[(i+1)%v.length], v[i]), point.sub(p, v[i])) <= 0)
                return false;
        return  true;
    }

    public void add(point p) {
        for (int i = 0; i < v.length; i++) {
            v[i] = point.sum(v[i], p);
        }
    }

    void moveBetweeReferences(point o1, point ox1, point oy1, point o2, point ox2, point oy2){
        for (int i = 0; i <v.length ; i++) {
            v[i] = point.pointFromReference(point.pointToReference(v[i], o1, ox1, oy1), o2, ox2, oy2);
        }
    }
}