package com.example.tetris;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.FileNotFoundException;
import java.io.IOException;

public class TetrisManagerPreferences extends TetrisManager{
    SharedPreferences preferences;

    String stateName = "gameState";

    TetrisManagerPreferences(Context context){
        super(context);
        preferences=context.getSharedPreferences("gamestate",
                context.MODE_PRIVATE);
    }

    @Override
    void saveBoard(String s) throws IOException {
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString(stateName, s);
        editor.commit();
    }

    @Override
    String getBoard() throws FileNotFoundException {
        return preferences.getString(stateName,"");
    }

    @Override
    boolean hasSavedBoard() {
        return preferences.contains(stateName);
    }

    @Override
    void deleteBoard() throws IOException {
        SharedPreferences.Editor editor=preferences.edit();
        editor.remove(stateName);
        editor.commit();
    }
}
