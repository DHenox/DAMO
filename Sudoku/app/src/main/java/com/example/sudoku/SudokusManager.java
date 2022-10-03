package com.example.sudoku;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

abstract public class SudokusManager {

    Context mycontext;

    SudokusManager(Context context) {

        mycontext=context;

        BufferedReader reader= null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(
                            mycontext.getAssets().open("sudokus.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String line;
        String input="";
        try {
            while ((line=reader.readLine())!=null) {
                input+=line+"\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] lines=input.split("\n");
        listboards=new Board[lines.length/11];
        Log.i("------>input",input);
        for (int i=0;i<listboards.length;i++) {
            Log.i("--------->i",String.valueOf(i));
            int index=11*i;
            Board board=new Board();
            listboards[i]=board;
            board.id=i;
            board.name=lines[index];
            board.difficulty=Integer.parseInt(lines[index+1]);
            board.t=new String[9];
            for (int j=0;j<9;j++) {
                board.t[j]=lines[index+2+j];
            }
        }
        Log.i("--------done","done");
    }

    Board[] listboards;

    Board[] getListboards() {
        return listboards;
    }

    Board getBoard(int id)
    {
        return listboards[id];
    }

    String id2text(int id)
    {
        return "idsudoku"+String.valueOf(id);
    }

    abstract void saveBoardState(int id,String s) throws IOException;

    abstract String getBoardState(int id) throws FileNotFoundException;

    abstract boolean hasBoardState(int id);

    abstract void clearBoardStates() throws IOException;

    abstract void deleteBoardState(int id) throws IOException;
}
