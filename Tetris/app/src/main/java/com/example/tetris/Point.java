package com.example.tetris;

public class Point {
    int i, j;

    Point(int I, int J){
        i = I;
        j = J;
    }

    static Point add(Point p1, Point p2) {
        return new Point(p1.i + p2.i, p1.j + p2.j);
    }
}
