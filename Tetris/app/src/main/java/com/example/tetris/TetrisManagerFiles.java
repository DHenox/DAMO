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
    void saveGameState(String s) throws IOException {
        OutputStream writer=myContext.openFileOutput(
                fileName,
                Context.MODE_PRIVATE
        );
        writer.write(s.getBytes(StandardCharsets.UTF_8));
        writer.close();

        File file=new File(myContext.getFilesDir()+"/"+fileName);
    }

    @Override
    String getGameState() throws FileNotFoundException {
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
    boolean hasGameState() {
        File file=new File(myContext.getFilesDir()+"/"+fileName);
        return file.exists();
    }

    @Override
    void deleteGameState() throws IOException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Files.deleteIfExists(
                    Paths.get(myContext.getFilesDir()+"/"+fileName));
        }
    }
}
