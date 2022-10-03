package com.example.sudoku;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class SudokusManagerFiles extends SudokusManager {

    SudokusManagerFiles(Context context) {
        super(context);
    }

    @Override
    void saveBoardState(int id, String s) throws IOException {
        Log.i("---->save",s);
        OutputStream writer=mycontext.openFileOutput(
                id2text(id),
                Context.MODE_PRIVATE
        );
        writer.write(s.getBytes(StandardCharsets.UTF_8));
        writer.close();
    }

    @Override
    String getBoardState(int id) throws FileNotFoundException {
        File file=new File(mycontext.getFilesDir()+"/"+id2text(id));
        Scanner scanner=new Scanner(file);
        String input="";
        while (scanner.hasNext()) {
            input+=scanner.nextLine();
        }
        return input;
    }

    @Override
    boolean hasBoardState(int id) {
        File file=new File(mycontext.getFilesDir()+"/"+id2text(id));
        return file.exists();
    }

    @Override
    void clearBoardStates() throws IOException {
        for (int i=0;i<listboards.length;i++)
            deleteBoardState(i);
    }

    @Override
    void deleteBoardState(int id) throws IOException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Files.deleteIfExists(
                    Paths.get(mycontext.getFilesDir()+"/"+id2text(id)));
        }
    }
}
