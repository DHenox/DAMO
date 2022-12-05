package com.example.mazegame;

import android.graphics.Canvas;

import java.util.ArrayList;

public class SegmentsManager {
    ArrayList<Segment> listSegments;
    SegmentsManager(){
        listSegments = new ArrayList<>();
    }

    void draw(Canvas canvas){
        for (int i = 0; i < listSegments.size(); i++) {
            listSegments.get(i).draw(canvas);
        }
    }

    void add(Segment segment){
        listSegments.add(segment);
    }
}