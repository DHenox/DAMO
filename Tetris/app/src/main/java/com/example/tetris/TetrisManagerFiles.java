package com.example.tetris;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class TetrisManagerFiles extends TetrisManager{

    String fileName = "gameState";

    TetrisManagerFiles(Context context){
        super(context);
    }

    @Override
    void saveBoard(String s) throws IOException {
        System.out.println("Saving board");
        OutputStream writer=myContext.openFileOutput(
                fileName,
                Context.MODE_PRIVATE
        );
        writer.write(s.getBytes(StandardCharsets.UTF_8));
        writer.close();
    }

    @Override
    String getBoard() throws FileNotFoundException {
        File file=new File(myContext.getFilesDir()+"/"+fileName);
        Scanner scanner=new Scanner(file);
        String input="";
        while (scanner.hasNext()) {
            if(input != "")
                input += "\n";
            input+=scanner.nextLine();
        }
        return input;
    }

    @Override
    boolean hasSavedBoard() {
        File file=new File(myContext.getFilesDir()+"/"+fileName);
        System.out.println("---------> " + String.valueOf(file.exists()));
        return file.exists();
    }

    @Override
    void deleteBoard() throws IOException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Files.deleteIfExists(
                    Paths.get(myContext.getFilesDir()+"/"+fileName));
        }
    }
}
