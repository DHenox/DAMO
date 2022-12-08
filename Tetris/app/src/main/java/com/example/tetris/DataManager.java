package com.example.tetris;

import android.app.Application;

import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;

public class DataManager extends Application {

    TetrisManager tetrisManager;
    boolean hasManager;

    boolean saveActivated = false;

    void saveGameState(String s) throws IOException {
        tetrisManager.saveGameState(s);
    }

    String getGameState() throws FileNotFoundException{
        return tetrisManager.getGameState();
    }

    boolean hasGameState() {
        return tetrisManager.hasGameState();
    }

    void deleteGameState() throws IOException{
        tetrisManager.deleteGameState();
    }

    boolean managerSelected(){
        boolean preferencesManager = tetrisManager instanceof TetrisManagerPreferences;
        boolean filesManager = tetrisManager instanceof TetrisManagerFiles;
        boolean SQLManager = tetrisManager instanceof TetrisManagerSQL;

        return preferencesManager || filesManager || SQLManager;
    }

    void setUserWantsToSave(boolean saveGame) {
        saveActivated = saveGame;
    }

    public boolean userWantsToSave() {
        return saveActivated;
    }

    void setTetrisManager(int manager) {
        hasManager = true;
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
