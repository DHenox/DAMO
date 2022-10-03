package com.example.sudoku;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.FileNotFoundException;
import java.io.IOException;

public class SudokusManagerSQL extends SudokusManager{
    SQLiteDatabase db;

    SudokusManagerSQL(Context context) {
        super(context);
        db=context.openOrCreateDatabase("sudokus.sql",
                context.MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS sudokustate "+
                "(id INT NOT NULL PRIMARY KEY,state TEXT NOT NULL)");
    }

    @Override
    void saveBoardState(int id, String s) throws IOException {
        db.execSQL("REPLACE INTO sudokustate VALUES ("+String.valueOf(id)+",'"+s+"')");
        //db.execSQL("INSERT INTO sudokustate VALUES ("+String.valueOf(id)+",'"+s+"') "+
        //"ON CONFLICT("+String.valueOf(id)+") DO UPDATE SET state='"+s+"'");
    }

    @Override
    String getBoardState(int id) throws FileNotFoundException {
        Cursor cursor=db.rawQuery("SELECT state FROM sudokustate WHERE id="+
                String.valueOf(id),null);
        cursor.moveToFirst();
        return cursor.getString(0);
    }

    @Override
    boolean hasBoardState(int id) {
        Cursor cursor=db.rawQuery("SELECT state FROM sudokustate WHERE id="+
                String.valueOf(id),null);
        return cursor.getCount()>=1;
    }

    @Override
    void clearBoardStates() throws IOException {
        db.execSQL("DELETE FROM sudokustate");
    }

    @Override
    void deleteBoardState(int id) throws IOException {
        db.execSQL("DELETE FROM sudokustate WHERE id="+String.valueOf(id));
    }
}
