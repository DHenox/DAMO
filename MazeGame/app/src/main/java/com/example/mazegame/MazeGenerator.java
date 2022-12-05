package com.example.mazegame;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MazeGenerator {
    private class Cell{
        int i, j;
        boolean top, right;
        int id;
        Cell(int i, int j){
            this.i = i;
            this.j = j;
            top = right = true;
            id = i+j*n;
        }
    }
    int n, m;
    Cell[][] table;
    SegmentsManager sm;

    private class Wall{
        Cell cell1, cell2;
        boolean chooseright;
        Wall(Cell cell1, Cell cell2, boolean chooseright){
            this.cell1 = cell1;
            this.cell2 = cell2;
            this.chooseright = chooseright;
        }
    }

    private void generateCells(){
        table = new Cell[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                table[i][j] = new Cell(i, j);
            }
        }
        ArrayList<Wall> listwalls = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if(i < n-1)
                    listwalls.add(new Wall(table[i][j], table[i+1][j], true));
                if(j < m-1)
                    listwalls.add(new Wall(table[i][j], table[i][j+1], false));
            }
        }
        Collections.shuffle(listwalls);
        for (int iwall = 0; iwall < listwalls.size(); iwall++) {
            Wall wall = listwalls.get(iwall);
            Cell cell1 = wall.cell1;
            Cell cell2 = wall.cell2;
            int id1 = cell1.id;
            int id2 = cell2.id;
            if(id1 == id2)
                continue;

            if(wall.chooseright)
                cell1.right = false;
            else
                cell1.top = false;

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if(table[i][j].id == id1)
                        table[i][j].id = id2;
                }
            }
        }
    }


    private void generateSegments(){
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                Cell cell = table[i][j];
                if(cell.right) {
                    sm.add(new Segment(
                            new point(i+1, j),
                            new point(i+1, j+1)
                    ));
                }
                if(cell.top) {
                    sm.add(new Segment(
                            new point(i, j+1),
                            new point(i+1, j+1)
                    ));
                }
                if(i == 0) {
                    sm.add(new Segment(
                            new point(0, j),
                            new point(0, j+1)
                    ));
                }
                if(j == 0) {
                    sm.add(new Segment(
                            new point(i, 0),
                            new point(i+1, 0)
                    ));
                }
            }
        }
    }

    public void generate(int n, int m, SegmentsManager sm){
        this.n = n;
        this.m = m;
        this.sm = sm;
        generateCells();
        generateSegments();
    }
}
