package com.example.sudokuandtictactoe;

public class Board {
    int id;
    String name;
    int difficulty;
    String[] t;

    Board() {

    }
    Board(int id,String name,int difficulty,String[] t) {
        this.id=id;
        this.name=name;
        this.difficulty=difficulty;
        this.t=t;
    }
}
