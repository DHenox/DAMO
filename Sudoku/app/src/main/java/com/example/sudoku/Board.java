package com.example.sudoku;

public class Board {
    int id;
    String name;
    int difficulty;
    String[] t;
    Board() {

    }
    Board(int id,String name,int difficulty,String[] t,boolean saved) {
        this.id=id;
        this.name=name;
        this.difficulty=difficulty;
        this.t=t;
    }
}
