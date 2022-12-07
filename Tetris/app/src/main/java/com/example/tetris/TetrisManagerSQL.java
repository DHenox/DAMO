package com.example.tetris;

import android.content.Context;

import java.io.FileNotFoundException;
import java.io.IOException;

public class TetrisManagerSQL extends TetrisManager{
    TetrisManagerSQL(Context context){
        super(context);
    }

    @Override
    void saveBoard(String s) throws IOException {

    }

    @Override
    String getBoard() throws FileNotFoundException {
        return "";
    }

    @Override
    boolean hasSavedBoard() {
        return false;
    }

    @Override
    void deleteBoard() throws IOException {

    }
}
