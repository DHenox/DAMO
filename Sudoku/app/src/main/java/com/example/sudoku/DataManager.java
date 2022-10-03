package com.example.sudoku;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.prefs.Preferences;

public class DataManager extends Application {

    SudokusManager sudokusManager;

    Board[] getListboards() {
        return sudokusManager.getListboards();
    }

    Board getBoard(int id)
    {
        return sudokusManager.getBoard(id);
    }

    String id2text(int id)
    {
        return "idsudoku"+String.valueOf(id);
    }

    void saveBoardState(int id,String s) throws IOException {
        sudokusManager.saveBoardState(id,s);
    }

    String getBoardState(int id) throws FileNotFoundException {
        return sudokusManager.getBoardState(id);
    }

    boolean hasBoardState(int id)
    {
        return sudokusManager.hasBoardState(id);
    }

    void clearBoardStates() throws IOException {
        sudokusManager.clearBoardStates();
    }

    void deleteBoardState(int id) throws IOException {
        sudokusManager.deleteBoardState(id);
    }

    void setSudokusManager(int manager) {
        switch (manager){
            case 0:
                Log.d("SELECTED MANAGER","Preferences");
                sudokusManager=new SudokusManagerPreferences(this);
                break;
            case 1:
                Log.d("SELECTED MANAGER","Files");
                sudokusManager=new SudokusManagerFiles(this);
                break;
            case 2:
                Log.d("SELECTED MANAGER","SQL");
                sudokusManager=new SudokusManagerSQL(this);
                break;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}