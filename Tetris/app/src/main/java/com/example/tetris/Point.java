package com.example.tetris;

public class Point {
    int x, y;

    Point(int X, int Y){
        x = X;
        y = Y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    static Point add(Point p1, Point p2) {
        return new Point(p1.x + p2.x, p1.y + p2.y);
    }
}
