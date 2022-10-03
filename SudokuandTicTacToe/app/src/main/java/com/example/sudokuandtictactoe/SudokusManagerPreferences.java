package com.example.sudokuandtictactoe;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SudokusManagerPreferences extends SudokusManager {
    SharedPreferences preferences;

    SudokusManagerPreferences(Context context) {
        super(context);
        preferences=context.getSharedPreferences("sudokustates",
                context.MODE_PRIVATE);
    }

    @Override
    void saveBoardState(int id, String s) {
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString(id2text(id),s);
        editor.commit();
    }

    @Override
    String getBoardState(int id) {
        return preferences.getString(id2text(id),"");
    }

    @Override
    boolean hasBoardState(int id) {return preferences.contains(id2text(id));
    }

    @Override
    void clearBoardStates() {
        SharedPreferences.Editor editor=preferences.edit();
        editor.clear();
        editor.commit();

    }

    @Override
    void deleteBoardState(int id) {
        SharedPreferences.Editor editor=preferences.edit();
        editor.remove(id2text(id));
        editor.commit();
    }
}
