package com.example.tetris;

import android.app.Application;

import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;

public class DataManager extends Application {

    TetrisManager tetrisManager;

    void saveBoard(String s) throws IOException {
        tetrisManager.saveBoard(s);
    }

    String getBoard() throws FileNotFoundException{
        return tetrisManager.getBoard();
    }

    boolean hasBoardState() {
        return tetrisManager.hasSavedBoard();
    }

    void deleteBoard() throws IOException{
        tetrisManager.deleteBoard();
    }

    boolean managerSelected(){
        return tetrisManager != null;
    }

    void setTetrisManager(int manager) {
        switch (manager){
            case 0:
                Log.d("SELECTED MANAGER","Preferences");
                tetrisManager=new TetrisManagerPreferences(this);
                break;
            case 1:
                Log.d("SELECTED MANAGER","Files");
                tetrisManager=new TetrisManagerFiles(this);
                break;
            case 2:
                Log.d("SELECTED MANAGER","SQL");
                tetrisManager=new TetrisManagerSQL(this);
                break;
        }
    }

    @Override
    public void onCreate() {super.onCreate();}
}
