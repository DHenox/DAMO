package com.example.mazegame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Ball {
    point c;
    double r;
    boolean thereIsDestination;
    point destination;
    double speed = 5;
    SegmentsManager sm;

    Ball(){
        r = 0.25;
        c = new point(0.5, 0.5);
        thereIsDestination = false;
    }

    void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);
        canvas.drawCircle((float) c.x, (float) c.y, (float) r, paint);
    }

    void select(point p) {
        if(point.dist(p, c) < r){
            thereIsDestination = true;
            destination = p;
        }
        else{
            thereIsDestination = false;
        }
    }

    void setDestination(point p){
        destination = p;
    }

    void deSelect(){
        thereIsDestination = false;
    }

    boolean isSelected() {
        return thereIsDestination;
    }

    void separate(point p){
        if(point.dist(p, c) >= r) return;
        point dir = point.unitary(point.sub(c, p));
        c = point.sum(p, point.prod(r, dir));
    }

    void separate(Segment segment){
        separate(segment.p1);
        separate(segment.p2);

        point p1 = segment.p1;
        point p2 = segment.p2;
        point p1c = point.sub(c, p1);
        point p1p2 = point.sub(p2, p1);
        double distprojection = point.dotProd(p1c, p1p2)/point.modulo(p1p2);
        if(distprojection <= 0) return;
        if(distprojection > point.modulo(p1p2)) return;
        point pointprojection = point.sum(p1, point.prod(distprojection, point.unitary(p1p2)));
        separate(pointprojection);
    }

    void separate(){
        for (int i = 0; i < sm.listSegments.size(); i++) {
            separate(sm.listSegments.get(i));
        }
    }

    void moveAux(double delta){
        if(!thereIsDestination) return;
        double dist = delta*speed;
        if(point.dist(c, destination) < dist){
            c = destination;
            separate();
            return;
        }
        point dir = point.unitary(point.sub(destination, c));
        c = point.sum(c, point.prod(dist, dir));
        separate();
    }

    void move(double delta){
        double dist = delta*speed;
        int steps = (int)Math.floor(dist/(r/4))+1;
        for (int i = 0; i < steps; i++) {
            moveAux(delta/steps);
        }
    }
}
