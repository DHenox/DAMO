package com.example.drawfigures;

import android.graphics.Canvas;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.Random;

public class PolygonsManager {
    ArrayList<Polygon> listpolygons;
    int indexSelected = -1;
    Canvas canvas;

    PolygonsManager(){
        listpolygons = new ArrayList<>();
        canvas = new Canvas();
    }

    void add(Polygon polygon){
        if(listpolygons.size() == 0){
            listpolygons.add(polygon);
        }
        else{
            Random rnd = new Random();
            int randColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            listpolygons.add(polygon);
        }
    }

    void clearAll(){
        while (listpolygons.size() != 1)
            listpolygons.remove(listpolygons.size() - 1);
        indexSelected = -1;
    }

    void draw(Canvas canvas){
        for(int i = 0; i< listpolygons.size();++i){
            if(i == indexSelected)
                listpolygons.get(i).draw(canvas, true);
            else
                listpolygons.get(i).draw(canvas, false);
        }
    }

    void select(point p){
        for (int i = listpolygons.size()-1; i >= 0; i--) {
            if(listpolygons.get(i).inside(p)){
                indexSelected = i;
                return;
            }
        }
        indexSelected = -1;
    }

    boolean thereIsSelected(){
        return indexSelected != -1;
    }

    Polygon getSelected(){
        return listpolygons.get(indexSelected);
    }
}
